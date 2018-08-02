package com.huotu.android.mifang.fragment


import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.ShareCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.huotu.android.mifang.R
import com.huotu.android.mifang.activity.InviteOneActivity
import com.huotu.android.mifang.activity.InviteTwoActivity
import com.huotu.android.mifang.activity.OrderActivity
import com.huotu.android.mifang.adapter.FragmentAdapter
import com.huotu.android.mifang.adapter.PromotionAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.InviteContract
import com.huotu.android.mifang.mvp.presenter.InvitePresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.util.ImageUtils
import com.huotu.android.mifang.util.WechatShareUtil
import com.huotu.android.mifang.utils.AppUtil
import com.huotu.android.mifang.widget.ShareDialog
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloadQueueSet
import com.liulishuo.filedownloader.FileDownloader
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import kotlinx.android.synthetic.main.fragment_promotion.*
import java.io.File
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [PromotionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PromotionFragment : BaseFragment<InviteContract.Presenter>()
        ,ShareDialog.OnOperateListener
        ,SwipeRefreshLayout.OnRefreshListener
        ,InviteContract.View
        ,View.OnClickListener{
    var iPresenter = InvitePresenter(this)
    var shareDialog:ShareDialog?=null
    var inviteBean:InviteBean?=null
    var isShowProgress=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun initView() {

        promotion_operate_1.setOnClickListener(this)
        promotion_operate_2.setOnClickListener(this)
        promotion_operate_3.setOnClickListener(this)
        promotion_lay_shop_order.setOnClickListener(this)
        promotion_invite.setOnClickListener(this)
        promotion_refreshview.setOnRefreshListener(this)

    }

    override fun inviteCallback(apiResult: ApiResult<InviteBean>) {
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code !=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            toast("数据格式不对")
            return
        }
        inviteBean =apiResult.data

        promotion_avator.setImageURI( apiResult.data!!.UserHeadImgURL )
        promotion_username.text = apiResult.data!!.UserNickName
        promotion_user_type.text = apiResult.data!!.UserLevelName
        promotion_invite_count.text = apiResult.data!!.InvitationMemberNum.toString()
        promotion_sy_count.text = apiResult.data!!.SurplusNum.toString()
        promotion_profit.text = apiResult.data!!.ProfitAmount

        promotion_operate_2.visibility = if(apiResult.data!!.IsAgent) View.GONE else View.VISIBLE
        promotion_operate_3.visibility = if(apiResult.data!!.IsAgent) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.promotion_operate_1 -> {

                if(inviteBean==null)return

                var bundle=Bundle()
                bundle.putInt(Constants.INTENT_OPERATE_TYPE,1)
                bundle.putString(Constants.INTENT_URL,inviteBean!!.BuyBuddyNumURL )
                newIntent<InviteOneActivity>(bundle)
            }
            R.id.promotion_operate_2 -> {
                if(inviteBean==null)return

                var bundle=Bundle()
                bundle.putInt(Constants.INTENT_OPERATE_TYPE,2)
                bundle.putString(Constants.INTENT_URL, inviteBean!!.ApplyAgentURL)
                newIntent<InviteOneActivity>(bundle)
            }
            R.id.promotion_operate_3->{
                if(inviteBean==null)return

                var bundle=Bundle()
                bundle.putInt(Constants.INTENT_OPERATE_TYPE,3)
                bundle.putString(Constants.INTENT_URL, inviteBean!!.UpgradeAgentURL )
                bundle.putString(Constants.INTENT_URL1, inviteBean!!.AgentPoster )
                bundle.putString(Constants.INTENT_URL2, inviteBean!!.ProgramAgentURL)
                newIntent<InviteOneActivity>(bundle)
            }
            R.id.promotion_lay_shop_order -> {
                newIntent<OrderActivity>(Constants.INTENT_ORDER_SOURCE, 100 )
            }
            R.id.promotion_invite->{
                //openShareDialog()

                downloadImages(inviteBean!!.BuddyPoster , inviteBean!!.ProgramBuddyURL , -1)

            }
        }
    }

    private fun openShareDialog(){
        if( shareDialog==null){
            var list = ArrayList<KeyValue>()
            list.add(KeyValue(R.mipmap.ssdk_oks_classic_wechat, "微信好友"))
            list.add(KeyValue(R.mipmap.ssdk_oks_classic_wechatmoments,"微信朋友圈"))
            list.add(KeyValue(R.mipmap.ssdk_oks_classic_wechatfavorite,"微信收藏"))
            shareDialog=ShareDialog(context!! , this, list)
        }

        shareDialog!!.show()
    }

    override fun operate(keyValue: KeyValue) {

        var shareType:Int=SendMessageToWX.Req.WXSceneSession

        when(keyValue.code ){
            R.mipmap.ssdk_oks_classic_wechat-> {
                shareType = SendMessageToWX.Req.WXSceneSession
            }
            R.mipmap.ssdk_oks_classic_wechatmoments->{
                shareType=SendMessageToWX.Req.WXSceneTimeline
            }
            R.mipmap.ssdk_oks_classic_wechatfavorite->{
                shareType=SendMessageToWX.Req.WXSceneFavorite
            }
        }
        downloadImages(inviteBean!!.BuddyPoster , inviteBean!!.ProgramBuddyURL , shareType )
    }


    private fun share( bitmapPath:String  ){

        //WechatShareUtil().shareWechatOfOneImage(thumbBitmap, bitmap ,shareType )
        //var images = ArrayList<Uri>()
        //images.add(Uri.parse( bitmapPath ) )
        //images.add(ImageUtils.getUriByFile(context, bitmapPath))

        //var image = Uri.parse( bitmapPath )
        var intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.type = "image/*"

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM , getLocalImages(bitmapPath) )
        //intent.putExtra(Intent.EXTRA_STREAM , image)
        //intent.putExtra(Intent.EXTRA_SUBJECT, "邀请好友开店" )
        //intent.putExtra(Intent.EXTRA_TEXT, "邀请好友开店" )
        //intent.putExtra(Intent.EXTRA_TITLE, "邀请好友开店")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        var shareIntent = Intent.createChooser(intent, "分享")
        startActivity( shareIntent )




//        var shareIntent=ShareCompat.IntentBuilder.from(activity)
//                .setType("image/*")
//                .set


    }


    /**
     * 设置需要分享的照片放入Uri类型的集合里
     */
    private fun getLocalImages( filePath:String   ): ArrayList<Uri> {
        val myList = ArrayList<Uri>()

//        val imageDirectoryPath = dirPath //Constants.ImageDirPath  + dataId+"/"
//        val dir = File(imageDirectoryPath)
//        if (!dir.exists()) {
//            dir.mkdirs()
//        }

//        val imageDirectory = File(imageDirectoryPath)

//        val fileList = imageDirectory.list()

//        if (fileList.isNotEmpty()) {

//            var count = if (fileList.size > 9) 9 else fileList.size

//            for (i in 0 until count) {

                try {

                    var file = File(filePath)

                    val values = ContentValues(7)

                    values.put(MediaStore.Images.Media.TITLE, file.name)

                    values.put(MediaStore.Images.Media.DISPLAY_NAME, file.name)

                    values.put(MediaStore.Images.Media.DATE_TAKEN, Date().time)

                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

                    values.put(MediaStore.Images.ImageColumns.BUCKET_ID, file.hashCode())

                    values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, file.name)

                    values.put("_data", filePath)

                    //val contentResolver = context!!.contentResolver

                    val uri = ImageUtils.getUriByFile(context , filePath) //Uri.fromFile(file) //contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values)

                    myList.add(uri)

                } catch (e: Exception) {

                    e.printStackTrace()

                }

//            }

//        }


        return myList
    }



    private fun mergePicture( bgPicturePath:String , qrCodePath:String , shareType:Int)  {

        var bgFileName = File(bgPicturePath).name
        var bgFile = File( Constants.ImageDirPath+ bgFileName)
        var qrCodeFileName = File(qrCodePath).name
        var qrCodeFile = File( Constants.ImageDirPath + qrCodeFileName)

        if( bgFile.exists() && qrCodeFile.exists() ){
            var bgBitmap = ImageUtils.getBitmap(bgFile)
            var qrCodeBitmap = ImageUtils.getBitmap(qrCodeFile)
            if(bgBitmap==null ){
                toast("打开图片失败，请重新!")
                File(bgPicturePath).delete()
                return
            }
            if(qrCodeBitmap==null){
                toast("打开图片失败，请重试!")
                File(qrCodePath).delete()
                return
            }

            var sw = bgBitmap.width
            var sh = bgBitmap.height
            var w = qrCodeBitmap.width
            var h = qrCodeBitmap.height
            var x:Int
            var y :Int
            var qrw = sw/3
            var qrh = sh/3
            if( qrw >= w ){
                x = (sw- w )/2
                y = (sh- h )/2 + h
            }else{
                x = (sw- qrw)/2
                y = (sh - qrh)/2 + qrh
            }
            var mergeBitmap = ImageUtils.addImageWatermark(bgBitmap , qrCodeBitmap , x ,y ,255 )
            bgBitmap.recycle()
            qrCodeBitmap.recycle()

            var mergePicturePath = Constants.ImageDirPath+Constants.SHARE_SHOP_CODE_PICUTE_NAME
            ImageUtils.save(mergeBitmap , mergePicturePath , Bitmap.CompressFormat.PNG , true )

            if(!mergeBitmap.isRecycled){
                mergeBitmap.recycle()
            }

            share( mergePicturePath )

            return
        }else{
            toast("分享图片下载失败,请重试!")
            return
        }
    }

    private fun downloadImages(urlbg:String , urlqrcode:String , shareType: Int ) {

        val type = shareType

        if (TextUtils.isEmpty(urlbg) || TextUtils.isEmpty(urlqrcode)) {
            toast("图片地址空!")
            return
        }
        var fileNameBg = File(urlbg).name
        var filePathBg = Constants.ImageDirPath + fileNameBg
        var fileBg = File(filePathBg)
        var fileNameQrcode = File(urlqrcode).name
        var filePathQrcode = Constants.ImageDirPath + fileNameQrcode
        var fileQrcode = File(filePathQrcode)
        if (fileBg.exists() && fileQrcode.exists()) {
            mergePicture(filePathBg, filePathQrcode , type )
            return
        }






        var dir = Constants.ImageDirPath
        var f = File(dir)
        if (!f.exists()) {
            f.parentFile.mkdir()
        }

        var downLoadQueueSet = FileDownloadQueueSet(object : FileDownloadListener() {
            override fun warn(task: BaseDownloadTask?) {
                hideProgress()
                toast("warn")
            }

            override fun completed(task: BaseDownloadTask?) {
                hideProgress()
                if ((task!!.tag as IdId).id == (task!!.tag as IdId).total) {
                    //toast("图片已经保存在"+dir)

                    mergePicture(filePathBg , filePathQrcode , shareType )

                }
            }

            override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                hideProgress()
            }

            override fun error(task: BaseDownloadTask?, e: Throwable?) {
                hideProgress()
                toast("error")
                e!!.printStackTrace()
            }

            override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                showProgress("")
            }

            override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                hideProgress()
            }
        })

        var tasks = ArrayList<BaseDownloadTask>()


        if(!fileBg.exists() && fileQrcode.exists()){
            var idId = IdId(1, 1)
            tasks.add(FileDownloader.getImpl().create(urlbg).setTag(1).setPath(filePathBg).setTag(idId))
        }else if( fileBg.exists() && !fileQrcode.exists()){
            var idId = IdId(1, 1)
            tasks.add(FileDownloader.getImpl().create(urlqrcode).setTag(1).setPath(filePathQrcode).setTag(idId))
        }else if( !fileBg.exists() && !fileQrcode.exists()){
            var idId = IdId(1, 2)
            tasks.add(FileDownloader.getImpl().create(urlbg).setTag(1).setPath(filePathBg).setTag(idId))
            idId = IdId(2, 2)
            tasks.add(FileDownloader.getImpl().create(urlqrcode).setTag(2).setPath(filePathQrcode).setTag(idId))
        }

        downLoadQueueSet.disableCallbackProgressTimes()
        downLoadQueueSet.setAutoRetryTimes(1)
        downLoadQueueSet.downloadSequentially(tasks)//串行下载
        downLoadQueueSet.start()
        showProgress("")
    }

    override fun onRefresh() {
        isShowProgress=false
        iPresenter.invite()
    }

    override fun fetchData() {
        iPresenter.invite()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_promotion
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        if(isShowProgress) {
            promotion_progress.visibility = View.VISIBLE
        }else{
            promotion_progress.visibility=View.GONE
        }
    }

    override fun hideProgress() {
        super.hideProgress()
        promotion_progress.visibility=View.GONE
        promotion_refreshview.isRefreshing=false
        isShowProgress=false
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PromotionFragment.
         */
        @JvmStatic
        fun newInstance() =
                PromotionFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
