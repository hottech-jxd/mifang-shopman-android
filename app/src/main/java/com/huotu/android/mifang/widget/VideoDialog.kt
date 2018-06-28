package com.huotu.android.mifang.widget

import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.huotu.android.mifang.R
import com.huotu.android.mifang.widget.video.SampleCoverVideo
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder

class VideoDialog(context:Context) : BaseDialog(context){

    private var gsyVideoOptionBuilder= GSYVideoOptionBuilder()
    var gsyVideoPlayer:SampleCoverVideo?=null

    init {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.layout_video_dialog, null)

        this.addContentView(view)


    }

    fun show(videoUrl :String?){
        show()

        gsyVideoPlayer = this.dialog!!.findViewById<SampleCoverVideo>(R.id.video_dialog_video)


        gsyVideoOptionBuilder
                .setIsTouchWiget(false)
                .setUrl(videoUrl)
                .setSetUpLazy(true)//lazy可以防止滑动卡顿
                .setVideoTitle("")
                .setCacheWithPlay(true)
                .setRotateViewAuto(true)
                .setLockLand(true)
                //.setPlayTag(TAG)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
//                .setPlayPosition(position)
//                .setVideoAllCallBack(object : GSYSampleCallBack() {
//                    override fun onPrepared(url: String?, vararg objects: Any) {
//                        super.onPrepared(url, *objects)
//                        if (!gsyVideoPlayer.isIfCurrentIsFullscreen ) {
//                            //静音
//                            GSYVideoManager.instance().isNeedMute = false
//                        }
//
//                    }
//
//                    override fun onQuitFullscreen(url: String?, vararg objects: Any) {
//                        super.onQuitFullscreen(url, *objects)
//                        //全屏不静音
//                        GSYVideoManager.instance().isNeedMute = false
//                    }
//
//                    override fun onEnterFullscreen(url: String?, vararg objects: Any) {
//                        super.onEnterFullscreen(url, *objects)
//                        GSYVideoManager.instance().isNeedMute = false
//                        gsyVideoPlayer.currentPlayer.titleTextView.text =objects[0].toString()
//                    }
//                })
             .build(gsyVideoPlayer)
//
//
//        //gsyVideoPlayer.loadCoverImage( videoPicUrl , R.mipmap.ic_launcher)
//
//
//
//
//
//
//        //增加title
        gsyVideoPlayer!!.titleTextView.visibility= View.GONE
//
//        //设置返回键
        gsyVideoPlayer!!.backButton.visibility =View.GONE
//        //设置全屏按键功能
//        gsyVideoPlayer.fullscreenButton.setOnClickListener(View.OnClickListener { resolveFullBtn(gsyVideoPlayer) })

        gsyVideoPlayer!!.startPlayLogic()

    }



    override fun show() {

        val window = this.dialog!!.window
        if (window != null) {
            window!!.setWindowAnimations(R.style.anim_dialog)
            window!!.setGravity(Gravity.CENTER)
        }
        super.show()
    }

    override fun onDismiss(dialog: DialogInterface) {

        gsyVideoPlayer!!.setVideoAllCallBack(null)
        GSYVideoManager.releaseAllVideos()

        super.onDismiss(dialog)
    }
}