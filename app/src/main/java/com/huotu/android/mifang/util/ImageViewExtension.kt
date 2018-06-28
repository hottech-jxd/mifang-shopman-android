package com.huotu.android.mifang.util

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.GlideApp


fun ImageView.get(url:String){
    GlideApp.with(this.context)
            .load(url)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(this)
}

fun ImageView.getScale(url:String){

    var iv = this

    GlideApp.with(this.context)
            .asBitmap()
            .load(url)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(object :SimpleTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    var wid = resource.width
                    var hei = resource.height
                    var sw = iv.width
                    var sh = sw* hei / wid
                    var layout = iv.layoutParams
                    layout.width = sw
                    layout.height = sh
                    iv.layoutParams = layout

                    iv.setImageBitmap(resource)
                }
            })
}