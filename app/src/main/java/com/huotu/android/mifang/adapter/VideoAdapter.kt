package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.GlideApp
import com.huotu.android.mifang.bean.KVEntry
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.util.FrescoDraweeController
import com.huotu.android.mifang.util.FrescoDraweeListener
import com.huotu.android.mifang.widget.video.SampleCoverVideo
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class VideoAdapter( data :ArrayList<KVEntry> , var itemWidth :Int )
    : BaseQuickAdapter<KVEntry, BaseViewHolder>( R.layout.layout_quan_video_item , data)
    , FrescoDraweeListener.ImageCallback{

//    var imageView:ImageView?=null
//    private var gsyVideoOptionBuilder= GSYVideoOptionBuilder()

    override fun convert(helper: BaseViewHolder?, item: KVEntry ) {

        var iv = helper!!.getView<SimpleDraweeView>(R.id.quan_video_item_picture )



        //在加载图片之前，先给一个默认的宽高值
        imageCallback(itemWidth , itemWidth , iv )


        FrescoDraweeController.loadImage(iv , itemWidth , itemWidth , item.code , this )







//        Glide.with(mContext).asBitmap().load( item.code ).into(object:SimpleTarget<Bitmap>(){
//            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                if( scaleType == 1) {
//
//                    var wid = iv.width
//                    var hei = wid
//                    var layout = iv.layoutParams
//                    layout.width = wid.toInt()
//                    layout.height = hei.toInt()
//                    iv.layoutParams = layout
//                }else if(scaleType==2){
//                    var wid = resource.width
//                    var hei = resource.height
//                    var sw = iv.width
//                    var sh = sw* hei / wid
//                    var layout = iv.layoutParams
//                    layout.width = sw
//                    layout.height = sh
//                    iv.layoutParams = layout
////                }

//                iv.setImageBitmap(resource)
//            }
//        })




//        var videoPicUrl = pics[helper!!.adapterPosition]
//
//        imageView = ImageView(mContext)
//        imageView!!.scaleType = ImageView.ScaleType.CENTER_CROP
//
//        if (imageView!!.parent != null) {
//            val viewGroup = imageView!!.parent as ViewGroup
//            viewGroup.removeView(imageView)
//        }
//        var videoUrl = item
//
//        var title =""
//        var position = helper!!.layoutPosition
//        var gsyVideoPlayer = helper.getView<SampleCoverVideo>(R.id.quan_video_item_video)
//
//        gsyVideoOptionBuilder
//                .setIsTouchWiget(false)
//                .setThumbImageView(imageView)
//                .setUrl(videoUrl)
//                .setSetUpLazy(true)//lazy可以防止滑动卡顿
//                .setVideoTitle(title)
//                .setCacheWithPlay(true)
//                .setRotateViewAuto(true)
//                .setLockLand(true)
//                .setPlayTag(TAG)
//                .setShowFullAnimation(true)
//                .setNeedLockFull(true)
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
//                }).build(gsyVideoPlayer)
//
//
//        GlideApp.with(mContext)
//                .asBitmap()
//                .load(videoPicUrl)
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .into(object : SimpleTarget<Bitmap>(){
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                        if(imageView==null) return
//                        var wid = resource.width
//                        var hei = resource.height
//                        var sw = imageView!!.width
//                        var sh = sw* hei / wid
//                        var layout = imageView!!.layoutParams
//                        layout.width = sw
//                        layout.height = sh
//                        imageView!!.layoutParams = layout
//
//                        imageView!!.setImageBitmap(resource)
//                    }
//                })
//
//
//        //增加title
//        gsyVideoPlayer.titleTextView.visibility= View.GONE
//
//        //设置返回键
//        gsyVideoPlayer.backButton.visibility = View.GONE
//        //设置全屏按键功能
//        gsyVideoPlayer.fullscreenButton.setOnClickListener(View.OnClickListener { resolveFullBtn(gsyVideoPlayer) })
//

    }

    /**
     * 全屏幕按键处理
     */
    private fun resolveFullBtn(standardGSYVideoPlayer: StandardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen( mContext , false , true)
    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {
        simpleDraweeView!!.layoutParams.width=width
        simpleDraweeView!!.layoutParams.height=height
    }

    override fun imageFailure(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {

    }
}