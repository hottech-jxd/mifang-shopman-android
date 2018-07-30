package com.huotu.android.mifang.fragment


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.media.DeniedByServerException
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.text.TextUtils
import android.view.View
import android.widget.RelativeLayout
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.activity.*
import com.huotu.android.mifang.base.BaseApplication
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.MyContract
import com.huotu.android.mifang.mvp.presenter.MyPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.util.FrescoDraweeController
import com.huotu.android.mifang.util.FrescoDraweeListener
import com.huotu.android.mifang.util.ImageUtils
import com.huotu.android.mifang.widget.FrescoImageLoader
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_my.*
import java.io.File
import java.math.BigDecimal

/**
 * A simple [Fragment] subclass.
 * Use the [MyFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MyFragment : BaseFragment<MyContract.Presenter>()
        ,MyContract.View
        ,SwipeRefreshLayout.OnRefreshListener
        ,FrescoDraweeListener.ImageCallback
        ,OnBannerListener
        ,View.OnClickListener {

    private var myPresenter=MyPresenter(this)
    private var myBean :MyBean?=null
    private var qrCodeUrl:String?=null
    private var REQUEST_CODE_SHARE=2001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun initView() {

        my_setting.setOnClickListener(this)
        my_qrcode.setOnClickListener(this)
        my_repay.setOnClickListener(this)
        my_lay_message.setOnClickListener(this)
        my_lay_feedback.setOnClickListener(this)
        my_lay_model.setOnClickListener(this)
        my_lay_shopper.setOnClickListener(this)
        my_lay_aboutme.setOnClickListener(this)
        my_lay_wallet.setOnClickListener(this)
        my_lay_income.setOnClickListener(this)
        my_lay_order.setOnClickListener(this)
        my_lay_myterm.setOnClickListener(this)
        my_lay_waitaccounts.setOnClickListener(this)
        my_lay_balance.setOnClickListener(this)
        my_lay_mibean.setOnClickListener(this)
        my_banner.setOnBannerListener(this)
        my_message_deal.setOnClickListener(this)

        my_qrcode_back.setOnClickListener(this)
        my_qrcode_share.setOnClickListener(this)
        my_refreshview.setOnRefreshListener(this)

    }

    override fun onRefresh() {
        fetchData()
    }

    override fun fetchData() {
        myPresenter.myIndex()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_my
    }

    override fun OnBannerClick(position: Int) {
        if(myBean==null)return
        if(myBean!!.ADLists==null) return

        var url  =myBean!!.ADLists!![position].LinkURL
        newIntent<WebActivity>(Constants.INTENT_URL , url)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.my_lay_message->{
                newIntent<MessageActivity>()
            }
            R.id.my_lay_feedback->{
                newIntent<FeedbackActivity>()
            }
            R.id.my_setting->{
                newIntent<SettingActivity>()
            }
            R.id.my_lay_shopper->{
                newIntent<ShopperClassActivity>()
            }
            R.id.my_lay_aboutme->{
                var aboutUs = if(BaseApplication.instance!!.variable.initDataBean==null) "" else BaseApplication.instance!!.variable.initDataBean!!.aboutUs
                newIntent<WebActivity>( Constants.INTENT_URL , aboutUs )
            }
            R.id.my_lay_wallet->{
                newIntent<MyWalletActivity>()
            }
            R.id.my_lay_income->{
                newIntent<IncomeActivity>()
            }
            R.id.my_lay_order->{
                newIntent<OrderActivity>(Constants.INTENT_ORDER_SOURCE , -1 )
            }
            R.id.my_lay_myterm->{
                newIntent<MyTermActivity>()
            }
            R.id.my_qrcode->{
                //newIntent<WebActivity>(Constants.INTENT_URL , "http://www.baidu.com")
                openQRCodeDialog()
            }
            R.id.my_repay->{
                newIntent<BuyActivity>(Constants.INTENT_OPERATE_TYPE,1)
            }
            R.id.my_lay_waitaccounts->{
                newIntent<WaitAccountsActivity>(Constants.INTENT_OPERATE_TYPE, ScoreTypeEnum.WaitAccounts.id)
            }
            R.id.my_lay_balance->{
                newIntent<WaitAccountsActivity>(Constants.INTENT_OPERATE_TYPE, ScoreTypeEnum.Balance.id)
            }
            R.id.my_lay_mibean->{
                newIntent<WaitAccountsActivity>(Constants.INTENT_OPERATE_TYPE,ScoreTypeEnum.MiBean.id)
            }
            R.id.my_message_deal->{
                newIntent<PayLoanActivity>()
            }
            R.id.my_lay_model->{
                if(myBean==null)return
                newIntent<WebActivity>(Constants.INTENT_URL, myBean!!.MiFangModelURL)
            }
            R.id.my_qrcode_back->{
                my_qrcode_lay.visibility=View.GONE
            }
            R.id.my_qrcode_share->{

                shareQrCode(qrCodeUrl)
            }
        }
    }

    private fun shareQrCode(qrCodeUrl :String?){
        if(TextUtils.isEmpty(qrCodeUrl)){
            return
        }

        var qrCodeName= File(qrCodeUrl).name
        var qrCodePath = Constants.ImageDirPath + qrCodeName
        var qrCodeFile = File( qrCodePath )
        if( !qrCodeFile.exists() ) {
            downloadQrCode(qrCodeUrl!! , qrCodePath )
            return
        }

        var images = ArrayList<Uri>()
        //var file = File(qrCodePath)
        images.add( ImageUtils.getUriByFile(context , qrCodePath) )  // Uri.fromFile(qrCodeFile) )
        var intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.type = "image/*"
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, images)
        intent.putExtra(Intent.EXTRA_SUBJECT, "二维码名片" )
        intent.putExtra(Intent.EXTRA_TEXT, "二维码名片" )
        intent.putExtra(Intent.EXTRA_TITLE, "二维码名片")
        //intent.putExtra(Intent., quan.ShareTitle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivityForResult( Intent.createChooser(intent, "分享") , REQUEST_CODE_SHARE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //toast("分享了回调。。。。。")

    }

    private fun downloadQrCode(qrCodeUrl:String, qrCodePath :String){

        FileDownloader
                .getImpl()
                .create(qrCodeUrl)
                .setPath(qrCodePath)
                .setListener(object:FileDownloadListener(){
                    override fun warn(task: BaseDownloadTask?) {
                        my_progress.visibility=View.GONE
                    }

                    override fun completed(task: BaseDownloadTask?) {
                        my_progress.visibility=View.GONE
                        shareQrCode(qrCodeUrl)
                    }

                    override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                        my_progress.visibility=View.GONE
                    }

                    override fun error(task: BaseDownloadTask?, e: Throwable?) {
                        my_progress.visibility=View.GONE
                        e!!.printStackTrace()
                        toast("下载二维码失败,请重试")
                    }

                    override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                        my_progress.visibility=View.VISIBLE
                    }

                    override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                        my_progress.visibility=View.GONE
                    }
                }).start()

    }


    /**
     *
     */
    private fun openQRCodeDialog(){
        myPresenter.getQrcode()
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        my_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        my_progress.visibility=View.GONE
        my_refreshview.isRefreshing=false
    }

    override fun myIndexCallback(apiResult: ApiResult<MyBean>) {
        //my_progress.visibility=View.GONE
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        myBean = apiResult.data
        if(myBean == null)return

        my_name.text =myBean!!.WxNickName
        //GlideApp.with(context!!).load(myBean.WxHeadImg).into(my_avator)
        my_avator.setImageURI(myBean!!.WxHeadImg)

        my_account_type.text = myBean!!.LevelName
        my_ExpireTime.text="到期时间:"+ myBean!!.ExpireTime +"(剩"+ myBean!!.SurplusDays +"天)"

        if(myBean!!.SurplusDays<=30){
            my_repay.visibility=View.VISIBLE
        }else{
            my_repay.visibility=View.GONE
        }

        var tempIntegral = myBean!!.UserTempIntegral
        tempIntegral.setScale(2,BigDecimal.ROUND_HALF_UP)
        tempIntegral = tempIntegral.divide(BigDecimal(100))
        my_waitaccounts.text = tempIntegral.toString()
        var balance = myBean!!.UserIntegral
        balance.setScale(2, BigDecimal.ROUND_HALF_UP)
        balance = balance.divide(BigDecimal(100))
        my_balance.text = balance.toString()

        var mibean = myBean!!.UserMBean
        mibean.setScale(2,BigDecimal.ROUND_HALF_UP)
        mibean = mibean.divide(BigDecimal(100))
        my_mibean.text = mibean.toString()

        if( !TextUtils.isEmpty( myBean!!.TipStr)){
            my_lay_message.visibility=View.VISIBLE
            my_message_tip.text = myBean!!.TipStr
            my_message_deal.visibility = if( myBean!!.IsAgent ) View.VISIBLE else View.GONE
        }else{
            my_message_tip.text=""
            my_message_deal.visibility=View.GONE
            my_lay_message.visibility =View.GONE
        }

        setAdBanner(myBean!!.ADLists)
    }


    private fun setAdBanner( ads :ArrayList<MyBean.ADBean>?){
        if(ads==null||ads.size<1){
            my_banner.stopAutoPlay()
            my_banner.visibility=View.GONE
            return
        }else{
            my_banner.visibility=View.VISIBLE
        }

        //my_banner.setDelayTime(3500)
        var sw = DensityUtils.getScreenWidth(context!!)
        var sh = sw/5
        my_banner.setImageLoader(FrescoImageLoader(null, my_banner , sw, sh , R.mipmap.defaultpic ))
        my_banner.setImages(ads)
        if(ads.size>1) {
            my_banner.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        my_banner.stopAutoPlay()
    }

    override fun mySettingCallback(apiResult: ApiResult<SettingBean>) {

    }

    override fun getQrcodeCallback(apiResult: ApiResult<String>) {
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        my_qrcode_lay.visibility = View.VISIBLE //if (my_qrcode_lay.visibility==View.GONE) View.VISIBLE else View.GONE
        qrCodeUrl = apiResult.data
        var imageWith = DensityUtils.getScreenWidth(context!!) * 2 / 3
        FrescoDraweeController.loadImage( my_qrcode_image , imageWith , imageWith , qrCodeUrl, this)
    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {
        if(simpleDraweeView==null) return

        var layoutParams = simpleDraweeView.layoutParams
        if( layoutParams==null) {
            layoutParams = RelativeLayout.LayoutParams(width, height)
        }
        layoutParams.width = width
        layoutParams.height = height
    }

    override fun imageFailure(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {

    }

    override fun updatePayPasswordStatusCallback(apiResult: ApiResult<Any>) {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                MyFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
