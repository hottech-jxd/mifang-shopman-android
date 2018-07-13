package com.huotu.android.mifang.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.ShopperClassBean

class ShopperClassAdapter(data:ArrayList<ShopperClassBean>):BaseQuickAdapter<ShopperClassBean,BaseViewHolder>(R.layout.layout_shopperclass_item, data ) {

    override fun convert(helper: BaseViewHolder?, item: ShopperClassBean?) {

        helper!!.setText(R.id.shopperclass_item_desc , item!!.content)
        helper!!.setText(R.id.shopperclass_item_title , item!!.title)
        helper!!.getView<SimpleDraweeView>(R.id.shopperclass_item_logo).setImageURI(item!!.logo)

//        Glide.with(mContext)
//                .load(item!!.url)
//                .into( helper!!.getView(R.id.shopperclass_item_logo) )

    }
}