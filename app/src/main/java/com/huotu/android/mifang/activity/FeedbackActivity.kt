package com.huotu.android.mifang.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.FeedbackAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.FeedbackBean
import com.huotu.android.mifang.mvp.IPresenter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_feedback.*
import android.R.attr.data
import android.os.Environment
import com.luck.picture.lib.entity.LocalMedia
import android.os.Environment.getExternalStorageDirectory
import com.luck.picture.lib.tools.PictureFileUtils
import kotlinx.android.synthetic.main.layout_header.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File
import java.util.jar.Manifest


@RuntimePermissions
class FeedbackActivity : BaseActivity<IPresenter>(),View.OnClickListener
        ,BaseQuickAdapter.OnItemClickListener
        ,BaseQuickAdapter.OnItemChildClickListener {
    var feedbackAdapter:FeedbackAdapter?=null
    var data=ArrayList<FeedbackBean>()
    var MAX_IMAGE_COUNT = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        header_title.text="反馈与建议"
        header_left_image.setOnClickListener(this)
        data.add(FeedbackBean("",true))
        feedbackAdapter= FeedbackAdapter(data)
        feedback_images.layoutManager=GridLayoutManager(this,MAX_IMAGE_COUNT)
        feedback_images.adapter = feedbackAdapter
        feedbackAdapter!!.onItemChildClickListener =this
        feedbackAdapter!!.onItemClickListener=this

    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if(view!!.id==R.id.feedback_image_item_delete){
            data.removeAt(position)
            if( !data[0].isNew ) {
                data.add(0, FeedbackBean("", true))
            }
            feedbackAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        var feedbackBean=data[position]
        if(feedbackBean.isNew){
            //selectImage()
            selectImageWithPermissionCheck()
        }else{

        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        PictureFileUtils.deleteCacheDirFile(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( resultCode == Activity.RESULT_OK){
            when(requestCode ){
                PictureConfig.CHOOSE_REQUEST->{
                    // 图片、视频、音频选择结果回调
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    for( i in 0 until selectList.size) {
                        var bean = FeedbackBean(selectList[i].compressPath,false)
                        this.data.add(bean)
                        //adapter.setList(selectList)
                    }

                    if(this.data.size>MAX_IMAGE_COUNT){
                        this.data.removeAt(0)
                    }

                    feedbackAdapter!!.notifyDataSetChanged()

                }
            }
        }
    }

    /**
     * 自定义压缩存储地址
     *
     * @return
     */
    private fun getPath(): String {

        var path = ""
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            path = Environment.getExternalStorageDirectory().toString() + "/mifang/images/"
        }
        val file = File(path)
        return if (file.mkdirs()) {
            path
        } else path
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        onRequestPermissionsResult(requestCode , grantResults)
    }


    @NeedsPermission(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun selectImage(){
        var count = data.size-1
        if( count >= MAX_IMAGE_COUNT ) return
        var maxCount = MAX_IMAGE_COUNT- count
        var minCount = 1
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .compress(true)
                .compressSavePath(getPath())
                .isCamera(true)
                .maxSelectNum(maxCount)
                .minSelectNum(minCount)
                .selectionMode(PictureConfig.MULTIPLE)
                .minimumCompressSize(150) //150k以下不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST)
    }
}
