package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.OrderBean

class GoodsAdapter( data :ArrayList<OrderBean.OrderItemBean>) :BaseQuickAdapter<OrderBean.OrderItemBean, BaseViewHolder>(R.layout.layout_goods_items, data) {

    override fun convert(helper: BaseViewHolder?, item: OrderBean.OrderItemBean?) {
        helper!!.setText(R.id.goods_item_desc , item!!.ProductSpec)
        helper!!.setText(R.id.goods_item_count , "x"+item!!.ProductNum)
        helper!!.setText(R.id.goods_item_unitPrice , "￥"+item!!.ProductPrice)
        helper!!.setText(R.id.goods_item_spec , item!!.ProductSpec)

        helper!!.getView<SimpleDraweeView>(R.id.goods_item_logo).setImageURI(item!!.ProductImgUrl)
    }
}