package com.huotu.android.mifang.adapter


import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.util.ExpandableTextView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.KVEntry
import com.huotu.android.mifang.bean.Quan
import com.huotu.android.mifang.util.get
import com.huotu.android.mifang.widget.PicturePreviewDialog
import com.huotu.android.mifang.widget.RecyclerViewDivider2
import com.huotu.android.mifang.widget.VideoDialog

class QuanAdapter(data:ArrayList<Quan>)
    :BaseQuickAdapter< Quan ,BaseViewHolder>( R.layout.layout_quan_item_one , data)
        ,BaseQuickAdapter.OnItemClickListener{

//    val TAG = "QuanAdapter"

//    var imageView :ImageView?=null

//    private var gsyVideoOptionBuilder= GSYVideoOptionBuilder()

    override fun convert(helper: BaseViewHolder?, item: Quan?) {

        helper!!.addOnClickListener(R.id.quan_item_one_save_image)
        helper!!.addOnClickListener(R.id.quan_item_one_share)

        if(item!!.Type == 0){
            setUIType0(helper!!, item!!)
        }else if(item!!.Type==1){
            setUIType1(helper!!,item!!)
        }else if(item!!.Type==2){
            setUIType2(helper!!,item!!)
        }


//        Glide.with(mContext.applicationContext)
//                .setDefaultRequestOptions(
//                        RequestOptions()
//                                //.frame(1000000)
//                                .centerCrop()).load("http://image.tkcm888.com/adSet_2018-05-31_a13475823f524d5f8b3b9480673e339915277602221601122.png")
//                .into( helper!!.getView<ImageView>(R.id.quan_item_one_save_logo) )

//        setNineImage( helper!!.getView(R.id.quan_item_one_images) , item!!.ImageUrls )

//        helper!!.getView<ExpandableTextView>(R.id.quan_item_one_content).text= item!!.Content


//        imageView = ImageView(mContext)
//        imageView!!.scaleType = ImageView.ScaleType.CENTER_CROP
//        imageView!!.setImageResource(R.mipmap.ic_launcher)
//        if (imageView!!.parent != null) {
//            val viewGroup = imageView!!.parent as ViewGroup
//            viewGroup.removeView(imageView)
//        }
//
//        var url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
//        var title ="测试视频"
//        var position = helper!!.layoutPosition
//        var gsyVideoPlayer = helper.getView<SampleCoverVideo>(R.id.quan_item_one_video)

//        gsyVideoOptionBuilder
//                .setIsTouchWiget(false)
//                .setThumbImageView(imageView)
//                .setUrl(url)
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
//                        if (!gsyVideoPlayer.isIfCurrentIsFullscreen()) {
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
//                        gsyVideoPlayer.getCurrentPlayer().getTitleTextView().setText(objects[0] as String)
//                    }
//                }).build(gsyVideoPlayer)


        //gsyVideoPlayer.loadCoverImage( "http://image.tkcm888.com/adSet_2018-05-31_a13475823f524d5f8b3b9480673e339915277602221601122.png" , R.mipmap.ic_launcher)


        //增加title
//        gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE)
//
//        //设置返回键
//        gsyVideoPlayer.getBackButton().setVisibility(View.GONE)
//
//        //设置全屏按键功能
//        gsyVideoPlayer.getFullscreenButton().setOnClickListener(View.OnClickListener { resolveFullBtn(gsyVideoPlayer) })


    }

    /**
     * 全屏幕按键处理
     */
//    private fun resolveFullBtn(standardGSYVideoPlayer: StandardGSYVideoPlayer) {
//        standardGSYVideoPlayer.startWindowFullscreen( mContext , false , true)
//    }

    private fun setNineImage( recyclerView: RecyclerView , urls :ArrayList<String>?){
        if(urls==null||urls.size<1 )return

        var count = urls.size
        var scaleType = 1
        var itemWidth = 0

        var layoutParam = recyclerView.layoutParams as LinearLayout.LayoutParams
        var margin = DensityUtils.dip2px(mContext , 5f)
        layoutParam.rightMargin = margin
        recyclerView.layoutParams = layoutParam
        var screenW =  DensityUtils.getScreenWidth(mContext)
        var leftW =  DensityUtils.dip2px(mContext, 70f )
        var rightW = screenW-leftW

        if(count==1){
            recyclerView.layoutManager=GridLayoutManager( mContext , 2 )
            //width = DensityUtils.getScreenWidth(mContext)/count
            itemWidth = rightW/2
            scaleType=2
        }
        else if(count == 2){
            recyclerView.layoutManager=GridLayoutManager( mContext , 2 )
            var layoutParam = recyclerView.layoutParams as LinearLayout.LayoutParams
            layoutParam.rightMargin = DensityUtils.getScreenWidth(mContext) / 4
            recyclerView.layoutParams = layoutParam
            scaleType=1
            itemWidth = (rightW - layoutParam.rightMargin) / 2
        }
        else if(count==3){
            recyclerView.layoutManager=GridLayoutManager( mContext , count )
            scaleType = 1
            itemWidth = (rightW - 3* margin) / count
        }else if( count == 4){
            recyclerView.layoutManager=GridLayoutManager( mContext , 2 )
            var layoutParam = recyclerView.layoutParams as LinearLayout.LayoutParams
            layoutParam.rightMargin = DensityUtils.getScreenWidth(mContext) / 4
            recyclerView.layoutParams = layoutParam
            scaleType = 1
            itemWidth = (rightW - layoutParam.rightMargin) / 2
        }else{
            recyclerView.layoutManager = GridLayoutManager( mContext , 3)
            scaleType = 1
            itemWidth = (rightW - 3* margin) /3
        }
        recyclerView.addItemDecoration(RecyclerViewDivider2(mContext , ContextCompat.getColor(mContext , R.color.white) , 5f))

        var imageAdapter = ImageAdaper( urls , scaleType , itemWidth)
        recyclerView.adapter=imageAdapter

        imageAdapter.onItemClickListener=this
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

        if( adapter is ImageAdaper) {
            var picturePreviewDialog = PicturePreviewDialog(mContext)
            picturePreviewDialog.show(adapter!!.data as ArrayList<String?>)
        }else if(adapter is VideoAdapter){
            var videoDialog = VideoDialog(mContext)
            videoDialog.show( adapter.data[position].name )
        }
    }

    /***
     * 类型是 普通文本 样式
     */
    private fun setUIType0(helper: BaseViewHolder, quan: Quan){

        helper.setText(R.id.quan_item_one_title , quan.Title )

        helper.getView<ExpandableTextView>(R.id.quan_item_one_content).text= quan.Content

//        Glide.with(mContext)
//                .load( quan.logo )
//                .into( helper.getView(R.id.quan_item_one_logo))
//
//        GlideApp.with(mContext)
//                .asBitmap()
//                .placeholder(R.mipmap.ic_launcher)
//                .load(quan.logo)
//                .into(helper.getView(R.id.quan_item_one_logo))

        helper.getView<ImageView>(R.id.quan_item_one_logo).get(quan.logo)


        helper.setText(R.id.quan_item_one_time , quan.Time)
        helper.setText(R.id.quan_item_one_count , "已被转发次"+quan.TurnAmount.toString()+"次")
        helper.setText(R.id.quan_item_one_money , quan.Profit)

        helper.setGone(R.id.quan_item_one_images , false)
        helper.setGone(R.id.quan_item_one_videos , false)

        helper.setGone(R.id.quan_item_one_save_image , false)
    }

    /***
     * 类型是 图片+文本 样式
     */
    private fun setUIType1(helper: BaseViewHolder , quan: Quan){
        helper.setText(R.id.quan_item_one_title , quan.Title )
        helper.getView<ExpandableTextView>(R.id.quan_item_one_content).text= quan.Content
        //Glide.with(mContext).load( quan.logo ).into( helper.getView(R.id.quan_item_one_logo))
        helper.getView<ImageView>(R.id.quan_item_one_logo).get(quan.logo)

        helper.setText(R.id.quan_item_one_time , quan.Time)
        helper.setText(R.id.quan_item_one_count , "已被转发次"+quan.TurnAmount.toString()+"次")
        helper.setText(R.id.quan_item_one_money , quan.Profit)


        helper.setGone(R.id.quan_item_one_images , true)
        helper.setGone(R.id.quan_item_one_videos , false)
        helper.setGone(R.id.quan_item_one_save_image, true)
        helper.setText(R.id.quan_item_one_save_text,"下载图片")

        setNineImage( helper.getView(R.id.quan_item_one_images) , quan.SmallImageUrls )
    }

    private fun setVideos( recyclerView: RecyclerView ,quan: Quan){
        if(quan.VideoUrls==null || quan.VideoUrls!!.size<1) return
        if(quan.VideoPictureUrls==null|| quan.VideoPictureUrls!!.size<1) return

        var count = quan.VideoPictureUrls!!.size
        var scaleType = 1
        var screenW =  DensityUtils.getScreenWidth(mContext)
        var leftW =  DensityUtils.dip2px(mContext, 70f )
        var rightW = screenW-leftW

        var itemWidth = (screenW - leftW)/2

        var layoutParam = recyclerView.layoutParams as LinearLayout.LayoutParams
        layoutParam.rightMargin = DensityUtils.dip2px(mContext , 5f)
        recyclerView.layoutParams = layoutParam

        if(count==1){
            recyclerView.layoutManager=GridLayoutManager( mContext , 2 )
            scaleType=2
        }
        else if(count == 2){
            recyclerView.layoutManager=GridLayoutManager( mContext , 2 )
            var layoutParam = recyclerView.layoutParams as LinearLayout.LayoutParams
            layoutParam.rightMargin = DensityUtils.getScreenWidth(mContext) / 4
            recyclerView.layoutParams = layoutParam
            scaleType=1
        }
        else if(count==3){
            recyclerView.layoutManager=GridLayoutManager( mContext , count )
            scaleType = 1
        }else if( count == 4){
            recyclerView.layoutManager=GridLayoutManager( mContext , 2 )
            var layoutParam = recyclerView.layoutParams as LinearLayout.LayoutParams
            layoutParam.rightMargin = DensityUtils.getScreenWidth(mContext) / 4
            recyclerView.layoutParams = layoutParam
            scaleType = 1
        }else{
            recyclerView.layoutManager = GridLayoutManager( mContext , 3)
            scaleType = 1
        }
        recyclerView.addItemDecoration(RecyclerViewDivider2(mContext , ContextCompat.getColor(mContext , R.color.white) , 5f))

        var kvs = ArrayList<KVEntry>()

        for(i in 0 until quan.VideoPictureUrls!!.size){
            var kv = KVEntry(quan.VideoPictureUrls!![i] , quan.VideoUrls!![i])
            kvs.add(kv)
        }

        var videoAdapter = VideoAdapter( kvs , itemWidth)
        recyclerView.adapter=videoAdapter
        videoAdapter.onItemClickListener=this
    }

    /***
     * 类型是 视频 样式
     */
    private fun setUIType2(helper: BaseViewHolder , quan: Quan){
        helper.setText(R.id.quan_item_one_title , quan.Title )
        helper.getView<ExpandableTextView>(R.id.quan_item_one_content).text= quan.Content
        //Glide.with(mContext).load( quan.logo ).into( helper.getView(R.id.quan_item_one_logo))
        helper.getView<ImageView>(R.id.quan_item_one_logo).get(quan.logo)

        helper.setText(R.id.quan_item_one_time , quan.Time)
        helper.setText(R.id.quan_item_one_count , "已被转发次"+quan.TurnAmount.toString()+"次")
        helper.setText(R.id.quan_item_one_money , quan.Profit)
        helper.setGone(R.id.quan_item_one_images , false)
        helper.setGone(R.id.quan_item_one_videos , true)
        helper.setGone(R.id.quan_item_one_save_image, true )
        helper.setText(R.id.quan_item_one_save_text , "下载视频")

        if(quan.VideoUrls==null|| quan.VideoUrls!!.size<1)return


        setVideos( helper.getView(R.id.quan_item_one_videos) , quan )

//        var videoPicUrl = quan.VideoPictureUrls!![0]
//        var videoUrl = quan.VideoUrls[0]

//        var videoAdapter=VideoAdapter(quan.VideoUrls , quan.VideoPictureUrls)
//        var videosRecyclerView = helper.getView<RecyclerView>(R.id.quan_item_one_videos)
//        videosRecyclerView.layoutManager = GridLayoutManager( mContext , 2)
//        videosRecyclerView.adapter=  videoAdapter
//        videosRecyclerView.addItemDecoration(RecyclerViewDivider2(mContext , ContextCompat.getColor(mContext , R.color.white) , 5f))

//
//        imageView = ImageView(mContext)
//        imageView!!.scaleType = ImageView.ScaleType.CENTER_CROP
//        if (imageView!!.parent != null) {
//            val viewGroup = imageView!!.parent as ViewGroup
//            viewGroup.removeView(imageView)
//        }
//
//        var title =quan.Title
//        var position = helper!!.layoutPosition
//        var gsyVideoPlayer = helper.getView<SampleCoverVideo>(R.id.quan_item_one_video)
//
//        var layoutPara = gsyVideoPlayer.layoutParams
//        layoutPara.width = DensityUtils.getScreenWidth(mContext)/2
//        gsyVideoPlayer.layoutParams = layoutPara
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
//        //gsyVideoPlayer.loadCoverImage( videoPicUrl , R.mipmap.ic_launcher)
//
//        GlideApp.with(mContext)
//                .asBitmap()
//                .load(videoPicUrl)
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .into(object :SimpleTarget<Bitmap>(){
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                        if(imageView==null) return
//                        var sw = resource.width
//                        var sh = resource.height
//                        var iw = imageView!!.width
//                        var ih = imageView!!.height
//                        var fw =sw
//                        var fh = sh
//                        if(sw<=sh){
//                            iw = DensityUtils.getScreenWidth(mContext)/3
//                            fw = iw
//                            //fh = fw * sh / sw //fw/fh = sw / sh
//                        }else{
//                            iw = DensityUtils.getScreenWidth(mContext)/2
//                            fw = iw
//                            //fw =  sw * fh / sh
//                        }
//                        fh = fw * sh / sw
//
//                        var layoutParam = imageView!!.layoutParams
//                        layoutParam.width = fw
//                        layoutParam.height = fh
//                        imageView!!.layoutParams = layoutParam
//
//                        imageView!!.setImageBitmap(resource)
//                    }
//                })
//
//
//
//
//        //增加title
//        gsyVideoPlayer.titleTextView.visibility= View.GONE
//
//        //设置返回键
//        gsyVideoPlayer.backButton.visibility =View.GONE
//        //设置全屏按键功能
//        gsyVideoPlayer.fullscreenButton.setOnClickListener(View.OnClickListener { resolveFullBtn(gsyVideoPlayer) })


    }
}