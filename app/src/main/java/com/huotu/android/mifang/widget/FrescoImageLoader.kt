package com.huotu.android.mifang.widget

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.util.FrescoDraweeController
import com.huotu.android.mifang.util.FrescoDraweeListener
import com.youth.banner.Banner
import com.youth.banner.loader.ImageLoader
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.huotu.android.mifang.R


class FrescoImageLoader(var banner : Banner, var width :Int ) :ImageLoader(), FrescoDraweeListener.ImageCallback{

    override fun createImageView(context: Context?): ImageView {
        var simpleDraweeView = SimpleDraweeView(context)

        val builder = GenericDraweeHierarchyBuilder(context!!.resources)
        val hierarchy = builder
                .setPlaceholderImage(R.mipmap.avator)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setFailureImage(R.mipmap.avator)
                .setFailureImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build()
        simpleDraweeView.hierarchy= hierarchy
        //simpleDraweeView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        return simpleDraweeView
        //return super.createImageView(context)
    }

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        //var uri = Uri.parse(path.toString())
        //imageView!!.setImageURI(uri)


        var layoutPara = imageView!!.layoutParams
        if(layoutPara==null){
            layoutPara= ViewGroup.LayoutParams(width,width*2/3)
        }
        layoutPara.width = width
        layoutPara.height = width*2/3
        imageView!!.layoutParams=layoutPara


        FrescoDraweeController.loadImage(imageView as SimpleDraweeView , width , width*2/3 , path.toString() , this)
    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {
        if(simpleDraweeView==null) return
        var layoutParams = simpleDraweeView.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        simpleDraweeView.layoutParams =layoutParams

        var layout2  = banner.layoutParams
        layout2.height=height
        banner.layoutParams=layout2
    }
}