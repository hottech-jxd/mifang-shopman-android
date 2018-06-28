package com.huotu.android.mifang.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.chrisbanes.photoview.PhotoView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.util.DensityUtils

class PicturePreviewAdapter(var data:ArrayList<String?> , var onClickListener : View.OnClickListener? )
    : PagerAdapter(),View.OnClickListener {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var v = LayoutInflater.from(container.context).inflate(R.layout.layout_picture_preview_item ,null)



        var iv = v.findViewById<PhotoView>(R.id.picture_preview_item_image)
        iv.setOnClickListener(this)
//        Glide.with( iv.context as Activity)
//                .load(data[position])
//                .into( iv )

        Glide.with(iv.context as Activity)
                .asBitmap()
                .load(data[position])
                .into(object:SimpleTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        if(iv==null)return
                        var sw = resource.width
                        var sh = resource.height
                        //var iw = DensityUtils.getScreenWidth(iv.context)
                        var ih = DensityUtils.getScreenHeight(iv.context)
                        var layout = iv.layoutParams
                        //layout.width=iw
                        layout.height = ih
                        iv.layoutParams= layout

                        iv.setImageBitmap(resource)
                    }
                })


//        var test = v.findViewById<ImageView>(R.id.testttttt)
//        Glide.with( iv.context as Activity)
//                .load(data[position])
//                .into( test )

        container.addView(v)

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun onClick(v: View?) {
        if( onClickListener!=null){
            onClickListener!!.onClick(v!!)
        }
    }
}