package com.huotu.android.mifang.adapter


import android.support.v7.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R

class ImageAdaper(var width : Int , data :ArrayList<String>)
    :BaseQuickAdapter<String,BaseViewHolder>( R.layout.layout_quan_image_item , data){

    override fun convert(helper: BaseViewHolder?, item: String?) {

        var iv = helper!!.getView<AppCompatImageView>(R.id.quan_image_item_icon )
        Glide.with(mContext).load( item ).into(iv)

    }
}