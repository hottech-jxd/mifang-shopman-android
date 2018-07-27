package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.util.FrescoDraweeController
import com.huotu.android.mifang.util.FrescoDraweeListener


class ImageAdaper(data :ArrayList<String> , var scaleType :Int = 1 /*1：代表宽高等比，2：代表根据宽度缩放*/,var itemWidth:Int)
    :BaseQuickAdapter<String,BaseViewHolder>( R.layout.layout_quan_image_item , data)
    ,FrescoDraweeListener.ImageCallback{

    override fun convert(helper: BaseViewHolder?, item: String?) {

        var iv=  helper!!.getView<SimpleDraweeView>(R.id.quan_image_item_icon)

        //Log.d("ssssssssssssssssssssss", "itemwidth="+ itemWidth)

        //var w = DensityUtils.getScreenWidth(mContext) - DensityUtils.dip2px(mContext, 70f )

        if(scaleType==1){
            iv.layoutParams.width = itemWidth
            iv.layoutParams.height = itemWidth
            iv.aspectRatio = 1f
            iv.setImageURI(item)
        }else {
            FrescoDraweeController.loadImage(iv, itemWidth, itemWidth , item, this)
        }
    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {

        simpleDraweeView!!.layoutParams.width=width
        simpleDraweeView!!.layoutParams.height=height
    }

    override fun imageFailure(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {

    }
}