package com.huotu.android.mifang.adapter


import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.util.ExpandableTextView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.KVEntry
import com.huotu.android.mifang.bean.Quan
import com.huotu.android.mifang.widget.PicturePreviewDialog
import com.huotu.android.mifang.widget.RecyclerViewDivider2
import com.huotu.android.mifang.widget.VideoDialog
import android.util.TypedValue
import com.huotu.android.mifang.widget.RecyclerViewDividerEmpty
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder


class QuanAdapter(data:ArrayList<Quan>)
    :BaseQuickAdapter< Quan ,BaseViewHolder>( R.layout.layout_quan_item_one , data)
        ,BaseQuickAdapter.OnItemClickListener{

    var marginDp=2f

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

    }


    private fun setNineImage( recyclerView: RecyclerView , urls :ArrayList<String>?){
        if(urls==null||urls.size<1 )return

        var count = urls.size
        var scaleType = 1
        var itemWidth = 0

        var layoutParam = recyclerView.layoutParams as LinearLayout.LayoutParams
        var margin = DensityUtils.dip2px(mContext , marginDp)
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
            itemWidth = (rightW- 3* margin)/3
            layoutParam.rightMargin = (rightW - itemWidth*2 - margin) //DensityUtils.getScreenWidth(mContext) / 4
            recyclerView.layoutParams = layoutParam
            scaleType=1
            //itemWidth = (rightW - layoutParam.rightMargin-2*margin) / 2
        }
        else if(count==3){
            recyclerView.layoutManager=GridLayoutManager( mContext , count )
            scaleType = 1
            itemWidth = (rightW - 3* margin) / count
        }else if( count == 4){
            recyclerView.layoutManager=GridLayoutManager( mContext , 2 )
            var layoutParam = recyclerView.layoutParams as LinearLayout.LayoutParams
            itemWidth = (rightW- 3* margin)/3
            layoutParam.rightMargin = (rightW - itemWidth*2 - margin)//DensityUtils.getScreenWidth(mContext) / 4
            recyclerView.layoutParams = layoutParam
            scaleType = 1
            //itemWidth = (rightW - layoutParam.rightMargin-2*margin) / 2
        }else{
            recyclerView.layoutManager = GridLayoutManager( mContext , 3)
            scaleType = 1
            itemWidth = (rightW - 3* margin) /3
            layoutParam.rightMargin=0
            recyclerView.layoutParams= layoutParam
        }

        recyclerView.addItemDecoration(RecyclerViewDivider2(mContext , ContextCompat.getColor(mContext , R.color.white) , marginDp ))

        var imageAdapter = ImageAdaper( urls , scaleType , itemWidth)
        recyclerView.adapter=imageAdapter

        imageAdapter.onItemClickListener=this
    }


//    private fun setNineImage( recyclerView: RecyclerView , urls :ArrayList<String>?){
//        if(urls==null||urls.size<1 )return
//
//        var count = urls.size
//        var scaleType = 1
//        var columnCount = 3
//        var screenW =  DensityUtils.getScreenWidth(mContext)
//        var margin = DensityUtils.dip2px(mContext , 5f)
//
//        var mmm = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                5f, mContext.resources.displayMetrics).toInt()
//
//        var leftWidth = DensityUtils.dip2px(mContext , 70f)
//        var itemWidth = (screenW - leftWidth - (columnCount-1) * margin)/columnCount
//
//        var layoutParam = recyclerView.layoutParams as LinearLayout.LayoutParams
//
//
//        if(count==1){
//            recyclerView.layoutManager=GridLayoutManager( mContext , 1 )
//
//            itemWidth = (screenW - leftWidth -  margin)/2
//            itemWidth += itemWidth/4
//            //layoutParam.width = itemWidth
//            layoutParam.rightMargin = (screenW-leftWidth)- itemWidth
//            recyclerView.layoutParams = layoutParam
//            scaleType=2
//            recyclerView.addItemDecoration(RecyclerViewDividerEmpty(mContext))
//        }
//        else if(count == 2){
//            recyclerView.layoutManager=GridLayoutManager( mContext , 2 )
//
//            itemWidth = (screenW - leftWidth - 3 * margin)/3
//            //layoutParam.width = itemWidth * 2 + margin*2 //screenW - leftWidth - 2 * margin
//            layoutParam.rightMargin = itemWidth
//            recyclerView.layoutParams = layoutParam
//            scaleType=1
//            recyclerView.addItemDecoration(RecyclerViewDivider2(mContext , ContextCompat.getColor(mContext , R.color.white) , 5f))
//
//        }
//        else if(count==3){
//            recyclerView.layoutManager=GridLayoutManager( mContext , count )
//            itemWidth = (screenW  -leftWidth - 3* margin)/3-10
//            scaleType = 1
//            //layoutParam.width = screenW -leftWidth - margin*3
//            layoutParam.rightMargin = 0
//            recyclerView.layoutParams = layoutParam
//            recyclerView.addItemDecoration(RecyclerViewDivider2(mContext, ContextCompat.getColor(mContext, R.color.white), 5f))
//
//        }else if( count == 4){
//            recyclerView.layoutManager=GridLayoutManager( mContext , 2 )
//            itemWidth = (screenW - leftWidth - 3 * margin)/3
//            //layoutParam.width =  itemWidth * 2 + 2*margin //screenW-leftWidth- 2 * margin
//            layoutParam.rightMargin = itemWidth
//            recyclerView.layoutParams = layoutParam
//            scaleType = 1
//            recyclerView.addItemDecoration(RecyclerViewDivider2(mContext, ContextCompat.getColor(mContext, R.color.white), 5f))
//
//        }else{
//            recyclerView.layoutManager = GridLayoutManager( mContext , 3)
//            //layoutParam.width =  screenW-leftWidth- 3*margin
//            layoutParam.rightMargin = 0
//            recyclerView.layoutParams = layoutParam
//            scaleType = 1
//            itemWidth = (screenW-leftWidth- 3 * margin)/3
//            recyclerView.addItemDecoration(RecyclerViewDivider2(mContext, ContextCompat.getColor(mContext, R.color.white), 5f))
//
//        }
//
//        //recyclerView.addItemDecoration(RecyclerViewDivider2(mContext , ContextCompat.getColor(mContext , R.color.white) , 5f))
//
//        var imageAdapter = ImageAdaper( urls , scaleType , itemWidth)
//        recyclerView.adapter=imageAdapter
//
//        imageAdapter.onItemClickListener=this
//    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

        if( adapter is ImageAdaper) {
            var picturePreviewDialog = PicturePreviewDialog(mContext)
            picturePreviewDialog.show(adapter!!.data as ArrayList<String?> , position)
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

        helper.getView<SimpleDraweeView>(R.id.quan_item_one_logo).setImageURI(quan.logo)

        helper.setText(R.id.quan_item_one_time , quan.Time)
        helper.setText(R.id.quan_item_one_count , "已被转发次"+quan.TurnAmount.toString()+"次")
        helper.setText(R.id.quan_item_one_money , quan.Profit+"元")

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
        helper.getView<SimpleDraweeView>(R.id.quan_item_one_logo).setImageURI(quan.logo)

        helper.setText(R.id.quan_item_one_time , quan.Time)
        helper.setText(R.id.quan_item_one_count , "已被转发次"+quan.TurnAmount.toString()+"次")
        helper.setText(R.id.quan_item_one_money , quan.Profit+"元")


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
        layoutParam.rightMargin = DensityUtils.dip2px(mContext , marginDp )
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
        recyclerView.addItemDecoration(RecyclerViewDivider2(mContext , ContextCompat.getColor(mContext , R.color.white) , marginDp ))

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
        helper.getView<SimpleDraweeView>(R.id.quan_item_one_logo).setImageURI(quan.logo)

        helper.setText(R.id.quan_item_one_time , quan.Time)
        helper.setText(R.id.quan_item_one_count , "已被转发次"+quan.TurnAmount.toString()+"次")
        helper.setText(R.id.quan_item_one_money , quan.Profit+"元")
        helper.setGone(R.id.quan_item_one_images , false)
        helper.setGone(R.id.quan_item_one_videos , true)
        helper.setGone(R.id.quan_item_one_save_image, true )
        helper.setText(R.id.quan_item_one_save_text , "下载视频")

        if(quan.VideoUrls==null|| quan.VideoUrls!!.size<1)return

        setVideos( helper.getView(R.id.quan_item_one_videos) , quan )
    }
}