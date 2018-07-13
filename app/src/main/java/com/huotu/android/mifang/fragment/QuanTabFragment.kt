package com.huotu.android.mifang.fragment


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
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnItemChildClickListener {

    private var category: Int? = null
    private var quanAdapter: QuanAdapter? = null
    private var data = ArrayList<Quan>()
    private var iPresenter=QuanPresenter(this)
    private var pageIndex = 0
    private var isShowProgress=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getInt(ARG_CATEGORY, 0)

        }
    }

    override fun initView() {

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
        if(quan.Type == 1){
            savaImage( quan )
        }else if(quan.Type==2){
            saveVideo( quan.dataId , quan.VideoUrls)
        }
    }

    private fun saveVideo( dataId :Long , videos:ArrayList<String?>?){

        if(videos==null || videos.size<1){
            toast("没有视频需要下载！")
            return
        }

        var dir = Constants.VideoDirPath + dataId+"/"

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

        for (i in 0 until videos.size) {


            var name = AppUtil.getFileName( videos[i] ) //  (i + 1).toString() + ".jpg"
            var path = dir + name
            var idId = IdId(i, videos.size - 1)

            tasks.add(FileDownloader.getImpl().create(videos[i]).setTag(i + 1).setPath(path).setTag(idId))
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

        startActivity(Intent.createChooser(intent, "分享"))
    }

    private fun shareImages(quan: Quan) {
        if(isDownPicture(quan)) savaImage(quan ,true)

        val cm = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 将文本内容放到系统剪贴板里。
        val clipData = ClipData.newPlainText( quan.ShareTitle , quan.ShareDescription )
        cm.primaryClip = clipData


        var intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.type = "image/*"
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, getLocalImages( quan.dataId ))
        intent.putExtra(Intent.EXTRA_SUBJECT, quan.ShareTitle )
        intent.putExtra(Intent.EXTRA_TEXT, quan.ShareDescription )
        intent.putExtra(Intent.EXTRA_TITLE, quan.ShareTitle)
        //intent.putExtra(Intent., quan.ShareTitle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(Intent.createChooser(intent, "分享"))
    }


    private fun share(quan: Quan) {
        if(quan.Type==0){
            shareText(quan)
        }else if(quan.Type==1) {
            shareImages(quan)
        }else if(quan.Type==2){
            //todo
            shareText(quan)
        }
    }

    private fun isDownPicture(quan: Quan):Boolean{
        val imageDirectoryPath = Constants.ImageDirPath  + quan.dataId+"/" //Environment.getExternalStorageDirectory().toString() + "/mifang/images/"+ dataId
        val dir = File(imageDirectoryPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val imageDirectory = File(imageDirectoryPath)

        val fileList = imageDirectory.list()
        return fileList.isEmpty()
    }

    /**
     * 设置需要分享的照片放入Uri类型的集合里
     */
    private fun getLocalImages( dataId :Long ): ArrayList<Uri> {
        val myList = ArrayList<Uri>()

        val imageDirectoryPath = Constants.ImageDirPath  + dataId+"/" //Environment.getExternalStorageDirectory().toString() + "/mifang/images/"+ dataId
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
