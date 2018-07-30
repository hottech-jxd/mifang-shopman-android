package com.huotu.android.mifang.widget

import android.content.Context
import android.net.Uri
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
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


class FrescoImageLoader(var banner : Banner, var width :Int , var defaultHeight:Int)
    :ImageLoader(), FrescoDraweeListener.ImageCallback{

    constructor(collapsingToolbarLayout : CollapsingToolbarLayout? , banner :Banner, width:Int, defaultHeight: Int, defaultPicture:Int):this(banner , width, defaultHeight){
        this.defaultPicture = defaultPicture
        this.collapsingToolbarLayout=collapsingToolbarLayout
    }

    private var collapsingToolbarLayout:CollapsingToolbarLayout?=null
    private var defaultPicture:Int=R.mipmap.avator

    override fun createImageView(context: Context?): ImageView {
        var simpleDraweeView = SimpleDraweeView(context)

        val builder = GenericDraweeHierarchyBuilder(context!!.resources)
        val hierarchy = builder
                .setPlaceholderImage(defaultPicture)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setFailureImage(defaultPicture)
                .setFailureImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build()
        simpleDraweeView.hierarchy= hierarchy
        return simpleDraweeView
    }

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {

        FrescoDraweeController.loadImage(imageView as SimpleDraweeView , width , defaultHeight , path.toString() , this)
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

        if(collapsingToolbarLayout!=null){
            var layout3  = collapsingToolbarLayout!!.layoutParams
            layout3.height=height
            collapsingToolbarLayout!!.layoutParams=layout3
        }
    }

    override fun imageFailure(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {
        var layout2  = banner.layoutParams

        layout2.height=height
        banner.layoutParams=layout2
    }
}