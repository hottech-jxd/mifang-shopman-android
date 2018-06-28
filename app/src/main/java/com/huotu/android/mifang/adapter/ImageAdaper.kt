package com.huotu.android.mifang.adapter


import android.graphics.Bitmap
import android.support.v7.widget.AppCompatImageView
import android.util.Log
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.GlideApp
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.util.FrescoDraweeController
import com.huotu.android.mifang.util.FrescoDraweeListener


class ImageAdaper(data :ArrayList<String> , var scaleType :Int = 1 /*1：代表宽高等比，2：代表根据宽度缩放*/,var itemWidth:Int)
    :BaseQuickAdapter<String,BaseViewHolder>( R.layout.layout_quan_image_item , data)
    ,FrescoDraweeListener.ImageCallback{

    override fun convert(helper: BaseViewHolder?, item: String?) {

//        var iv = helper!!.getView<AppCompatImageView>(R.id.quan_image_item_icon )

//        GlideApp.with(mContext).asBitmap().load( item )
//                .error(R.mipmap.ic_launcher)
//                .into(object:SimpleTarget<Bitmap>(){
//            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                if(scaleType == 1) {
//                    var wid = itemWidth//iv.width
//                    var hei = wid
//                    var layout = iv.layoutParams
//                    layout.width = wid
//                    layout.height = hei
//                    iv.layoutParams = layout
//                }else if(scaleType==2){
//                    var wid = resource.width
//                    var hei = resource.height
//                    var sw = itemWidth// iv.width
//                    var sh = sw* hei / wid
//                    var layout = iv.layoutParams
//                    layout.width = sw
//                    layout.height = sh
//                    iv.layoutParams = layout
//                }
//
//                iv.setImageBitmap(resource)
//            }
//        })

        var iv=  helper!!.getView<SimpleDraweeView>(R.id.quan_image_item_icon)

        //Log.d("ssssssssssssssssssssss", "itemwidth="+ itemWidth)

        //var w = DensityUtils.getScreenWidth(mContext) - DensityUtils.dip2px(mContext, 70f )

        if(scaleType==1){
            iv.layoutParams.width = itemWidth
            iv.layoutParams.height = itemWidth//iv.layoutParams.width
            iv.setImageURI(item)
        }else {
            FrescoDraweeController.loadImage(iv, itemWidth, 0, item, this)
        }
    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {

        simpleDraweeView!!.layoutParams.width=width
        simpleDraweeView!!.layoutParams.height=height
    }
}