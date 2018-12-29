package com.huotu.android.mifang.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.util.DensityUtils

class PromotionAdapter(var data:ArrayList<String>)
    :PagerAdapter(){


//    :BaseQuickAdapter< String ,BaseViewHolder>( R.layout.layout_promotion_banner_item,data) {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        var v = LayoutInflater.from(container.context).inflate(R.layout.layout_promotion_banner_item,null)
        container.addView(v)

        var iv = v.findViewById<ImageView>(R.id.promotion_banner_item_logo)
        Glide.with( iv.context as Activity )
                .asBitmap()
                .load(data[position])
                .into(object:SimpleTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        var wid = DensityUtils.getScreenWidth(iv.context) - container.paddingLeft - container.paddingRight
                        var hei = wid * resource.height / resource.width
                        var layout = iv.layoutParams
                        layout.width = wid.toInt()
                        layout.height = hei.toInt()
                        iv.layoutParams = layout

                        var roundBitmap = RoundedBitmapDrawableFactory.create(iv.context.resources , resource)
                        roundBitmap.cornerRadius = DensityUtils.dip2px(iv.context, 15f).toFloat()
                        iv.setImageDrawable(roundBitmap)
                    }
                })


        return v

    }

    override fun getItemPosition(`object`: Any): Int {
        //return super.getItemPosition(`object`)
        return POSITION_NONE
    }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {


        container.removeView(`object` as View)

    }

//    override fun convert(helper: BaseViewHolder?, item: String?) {
//
//        var iv = helper!!.getView<ImageView>(R.id.promotion_banner_item_logo)
//
//        Glide.with(iv.context as Activity)
//                .asBitmap()
//                .load("http://image.tkcm888.com/adSet_2018-06-01_f406f8550f0f4b21b41fca881bbcb11415278577614883710.png")
//                .into(object : SimpleTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                        var wid = DensityUtils.getScreenWidth(mContext) * 0.8
//                        var hei = wid * resource.height / resource.width
//                        var layout = iv.layoutParams
//                        layout.width = wid.toInt()
//                        layout.height = hei.toInt()
//                        iv.layoutParams = layout
//                        iv.setImageBitmap(resource)
//                    }
//                })
//
//    }
}