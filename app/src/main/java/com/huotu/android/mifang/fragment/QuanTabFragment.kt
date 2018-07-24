package com.huotu.android.mifang.fragment


import android.app.Activity.RESULT_OK
import android.content.*
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter

import android.provider.MediaStore.Images
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import com.huotu.android.mifang.AppInit
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.QuanAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.QuanContract
import com.huotu.android.mifang.mvp.presenter.QuanPresenter
import com.huotu.android.mifang.utils.AppUtil
import com.huotu.android.mifang.widget.RecyclerViewDivider
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloadQueueSet
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.util.FileDownloadHelper
import com.shuyu.gsyvideoplayer.GSYVideoManager
import kotlinx.android.synthetic.main.fragment_quan_tab.*
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList


private const val ARG_CATEGORY = "category"


/**
 * A simple [Fragment] subclass.
 * Use the [QuanTabFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class QuanTabFragment : BaseFragment<QuanContract.Presenter>()
        , QuanContract.View
        , SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnItemChildClickListener {

    private var category: Int? = null
    private var quanAdapter: QuanAdapter? = null
    private var data = ArrayList<Quan>()
    private var iPresenter=QuanPresenter(this)
    private var pageIndex = 0
    private var isShowProgress=true
    private var REQUEST_CODE_SHARE=3001
    private var currentShareDataId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getInt(ARG_CATEGORY, 0)

        }
    }

    override fun initView() {

        quan_tab_refreshview.setOnRefreshListener(this)

        quanAdapter = QuanAdapter(data)

        var emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty, null)
        quanAdapter!!.emptyView = emptyView
        quanAdapter!!.isUseEmpty(false)

        quan_tab_recyclerview.layoutManager = LinearLayoutManager(context)
        quan_tab_recyclerview.addItemDecoration(RecyclerViewDivider(context!!, ContextCompat.getColor(context!!, R.color.line_color), 10f))

        quan_tab_recyclerview.adapter = quanAdapter


        quanAdapter!!.onItemChildClickListener = this
        quanAdapter!!.setOnLoadMoreListener( this , quan_tab_recyclerview)

    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when (view!!.id) {
            R.id.quan_item_one_share -> {
                share( adapter!!.data[position] as Quan)
            }
            R.id.quan_item_one_save_image -> {
                save(adapter!!.data[position] as Quan)
            }
        }
    }

    private fun save( quan:Quan){
        if(quan.Type==0){
            val cm = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 将文本内容放到系统剪贴板里。
            val clipData = ClipData.newPlainText( quan.ShareTitle , quan.ShareDescription )
            cm.primaryClip = clipData
            toast("复制成功~~")
        }else if(quan.Type == 1){
            savaImage( quan )
        }else if(quan.Type==2){
            saveVideo( quan  )
        }
    }

    private fun saveVideo(quan: Quan , needShare:Boolean=false){

        if(quan.VideoUrls ==null || quan.VideoUrls!!.size<1){
            toast("没有视频需要下载！")
            return
        }

        var dir = Constants.VideoDirPath + quan.dataId+"/"

        isShowProgress=true
        var downLoadQueueSet = FileDownloadQueueSet(object : FileDownloadListener() {
            override fun warn(task: BaseDownloadTask?) {
                hideProgress()
                toast("warn")
            }

            override fun completed(task: BaseDownloadTask?) {
                hideProgress()
                if ((task!!.tag as IdId).id == (task!!.tag as IdId).total) {
                    toast("视频已经保存在"+ dir)
                    if(needShare){
                        share(quan)
                    }
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

        var f = File(dir)
        f.delete()
        if (!f.exists()) {
            f.parentFile.mkdir()
        }

        for (i in 0 until quan.VideoUrls!!.size) {

            var name = AppUtil.getFileName( quan.VideoUrls!![i] )
            var path = dir + name
            var idId = IdId(i, quan.VideoUrls!!.size - 1)

            tasks.add(FileDownloader.getImpl().create(quan.VideoUrls!![i]).setTag(i + 1).setPath(path).setTag(idId))
        }

        downLoadQueueSet.disableCallbackProgressTimes()
        downLoadQueueSet.setAutoRetryTimes(1)
        downLoadQueueSet.downloadSequentially(tasks)//串行下载
        downLoadQueueSet.start()
        showProgress("")

    }

    /**
     * 将图片存到本地
     */
    private fun savaImage(quan: Quan ,  needShare:Boolean=false) {

        if(quan.SmallImageUrls ==null || quan.SmallImageUrls!!.size<1){
            toast("没有图像需要下载！")
            return
        }
        var dir = Constants.ImageDirPath + quan.dataId+"/"
        isShowProgress=true
        var downLoadQueueSet = FileDownloadQueueSet(object : FileDownloadListener() {
            override fun warn(task: BaseDownloadTask?) {
                hideProgress()
                toast("warn")
            }

            override fun completed(task: BaseDownloadTask?) {
                hideProgress()
                if ((task!!.tag as IdId).id == (task!!.tag as IdId).total) {
                    toast("图片已经保存在"+dir)

                    if(needShare){
                        share(quan)
                    }
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

         //Environment.getExternalStorageDirectory().toString() + "/coupons/images/"+dataId+"/"
        var f = File(dir)
        f.delete()
        if (!f.exists()) {
            f.parentFile.mkdir()
        }

        for (i in 0 until quan.SmallImageUrls!!.size) {

            var name = AppUtil.getFileName( quan.SmallImageUrls!![i] ) //(i + 1).toString() + ".jpg"
            var path = dir + name
            var idId = IdId(i, quan.SmallImageUrls!!.size - 1)

            tasks.add(FileDownloader.getImpl().create(quan.SmallImageUrls!![i]).setTag(i + 1).setPath(path).setTag(idId))
        }
        downLoadQueueSet.disableCallbackProgressTimes()
        downLoadQueueSet.setAutoRetryTimes(1)
        downLoadQueueSet.downloadSequentially(tasks)//串行下载
        downLoadQueueSet.start()
        showProgress("")

    }

    private fun shareText(quan: Quan){
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plan"
        //intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, getLocalImages( quan.dataId ))
        intent.putExtra(Intent.EXTRA_SUBJECT, quan.ShareTitle )
        intent.putExtra(Intent.EXTRA_TEXT, quan.ShareDescription )
        intent.putExtra(Intent.EXTRA_TITLE, quan.ShareTitle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        currentShareDataId= quan.dataId
        startActivityForResult(Intent.createChooser(intent, "分享") , REQUEST_CODE_SHARE)
    }

    private fun shareImages(quan: Quan) {
        var dirPath = Constants.ImageDirPath + quan.dataId+"/"
        if(isDownPicture(dirPath)) savaImage(quan ,true)

        val cm = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 将文本内容放到系统剪贴板里。
        val clipData = ClipData.newPlainText( quan.ShareTitle , quan.ShareDescription )
        cm.primaryClip = clipData


        var intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.type = "image/*"
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, getLocalImages( Constants.ImageDirPath  + quan.dataId+"/"  ))
        intent.putExtra(Intent.EXTRA_SUBJECT, quan.ShareTitle )
        intent.putExtra(Intent.EXTRA_TEXT, quan.ShareDescription )
        intent.putExtra(Intent.EXTRA_TITLE, quan.ShareTitle)
        //intent.putExtra(Intent., quan.ShareTitle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        var shareIntent = Intent.createChooser(intent, "分享")
        //shareIntent.putExtra(Constants.INTENT_GOODSID , quan.dataId )
        currentShareDataId=quan.dataId
        startActivityForResult( shareIntent , REQUEST_CODE_SHARE )
    }

    private fun shareVideo(quan: Quan){
        var fileName = File( quan.VideoUrls!![0]).name
        var filePath = Constants.VideoDirPath  + quan.dataId+"/"+fileName
        var file = File(filePath)
        if(!file.exists()) saveVideo( quan ,true)

        val cm = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 将文本内容放到系统剪贴板里。
        val clipData = ClipData.newPlainText( quan.ShareTitle , quan.ShareDescription )
        cm.primaryClip = clipData


        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "video/*"
        //intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, getLocalvideo( quan.dataId ))
        intent.putExtra(Intent.EXTRA_STREAM , Uri.fromFile(file))
        intent.putExtra(Intent.EXTRA_SUBJECT, quan.ShareTitle )
        intent.putExtra(Intent.EXTRA_TEXT, quan.ShareDescription )
        intent.putExtra(Intent.EXTRA_TITLE, quan.ShareTitle)
        //intent.putExtra(Intent., quan.ShareTitle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        var shareIntent = Intent.createChooser(intent, "分享")
        //shareIntent.putExtra(Constants.INTENT_GOODSID , quan.dataId )
        currentShareDataId=quan.dataId
        startActivityForResult( shareIntent , REQUEST_CODE_SHARE )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if( requestCode==REQUEST_CODE_SHARE  ){
            //因为无法判断是否分享成功了，所以就都认为成功
            //toast("分享回调了.....")
            //var dataId = data!!.getLongExtra(Constants.INTENT_GOODSID,0L)
            if(currentShareDataId>0) {
                iPresenter.shareSuccess(currentShareDataId)
            }
        }
    }

    private fun share(quan: Quan) {
        if(quan.Type==0){
            shareText(quan)
        }else if(quan.Type==1) {
            shareImages(quan)
        }else if(quan.Type==2){
            shareVideo(quan)
        }
    }

    private fun isDownPicture( dirPath : String ):Boolean{
        //val imageDirectoryPath = Constants.ImageDirPath  + quan.dataId+"/"
        val dir = File(dirPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val imageDirectory = File(dirPath)

        val fileList = imageDirectory.list()
        return fileList.isEmpty()
    }

    /**
     * 设置需要分享的照片放入Uri类型的集合里
     */
    private fun getLocalImages( dirPath:String   ): ArrayList<Uri> {
        val myList = ArrayList<Uri>()

        val imageDirectoryPath = dirPath //Constants.ImageDirPath  + dataId+"/"
        val dir = File(imageDirectoryPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        val imageDirectory = File(imageDirectoryPath)

        val fileList = imageDirectory.list()

        if (fileList.isNotEmpty()) {

            var count = if (fileList.size > 9) 9 else fileList.size

            for (i in 0 until count) {

                try {

                    val values = ContentValues(7)

                    values.put(Images.Media.TITLE, fileList[i])

                    values.put(Images.Media.DISPLAY_NAME, fileList[i])

                    values.put(Images.Media.DATE_TAKEN, Date().time)

                    values.put(Images.Media.MIME_TYPE, "image/jpeg")

                    values.put(Images.ImageColumns.BUCKET_ID, imageDirectoryPath.hashCode())

                    values.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, fileList[i])

                    values.put("_data", imageDirectoryPath + fileList[i])

                    //val contentResolver = context!!.contentResolver

                    val uri = Uri.fromFile(File(imageDirectoryPath + fileList[i])) //contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values)

                    myList.add(uri)

                } catch (e: Exception) {

                    e.printStackTrace()

                }

            }

        }


        return myList
    }


    override fun fetchData() {

        quanAdapter!!.isUseEmpty(false)
        iPresenter.materialList(category!! , pageIndex+1)
    }


    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_quan_tab
    }


    override fun showProgress(msg: String) {
        super.showProgress(msg)

        if(isShowProgress){
            quan_tab_progress.visibility=View.VISIBLE
        }else{
            quan_tab_progress.visibility=View.GONE
        }

    }

    override fun hideProgress() {
        super.hideProgress()
        quan_tab_progress.visibility=View.GONE
        isShowProgress=false
        quan_tab_refreshview.isRefreshing= false
        quanAdapter!!.isUseEmpty(true)
    }

    override fun onRefresh() {
        pageIndex=0
        isShowProgress=false
        fetchData()
    }

    override fun onLoadMoreRequested() {
        //iPresenter.materialList(category , pageIndex+1 )
        fetchData()
    }

    override fun materialCategprysCallback(apiResult: ApiResult<List<MaterialCategory>>) {

    }

    override fun materialListCallback(apiResult: ApiResult<List<Quan>>) {
        isShowProgress = false
        quanAdapter!!.isUseEmpty(true)
        //borrow_refreshview.setRefreshing(false)


        if (processCommonErrorCode(apiResult )) return
        if (apiResult.code  != ApiResultCodeEnum.SUCCESS.code ) {
            toast(apiResult.msg )
            return
        }
        if (apiResult.data == null) return

        if (  apiResult!!.data!!.size < Constants.PAGE_SIZE) {
            //没有数据了
            if (pageIndex == 0) {
                quanAdapter!!.loadMoreEnd(true)
            } else {
                quanAdapter!!.loadMoreEnd()
            }

            pageIndex++

        } else {
            quanAdapter!!.loadMoreComplete()
            pageIndex++
        }


        if (pageIndex == 1) {
            data.clear()
            data.addAll(apiResult.data!!)
            quanAdapter!!.setNewData(apiResult.data)
            quanAdapter!!.disableLoadMoreIfNotFullPage(quan_tab_recyclerview)
        } else {
            data.addAll(apiResult.data!!)
            quanAdapter!!.addData(apiResult.data!!)
        }
    }


    override fun shareSuccessCallback(apiResult: ApiResult<Any>) {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuanTabFragment.
         */
        @JvmStatic
        fun newInstance(category: Int) =
                QuanTabFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_CATEGORY, category)

                    }
                }
    }
}
