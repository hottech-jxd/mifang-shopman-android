package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.OrderBean
import java.math.BigDecimal

class GoodsAdapter( data :ArrayList<OrderBean.OrderItemBean>) :BaseQuickAdapter<OrderBean.OrderItemBean, BaseViewHolder>(R.layout.layout_goods_items, data) {

    override fun convert(helper: BaseViewHolder?, item: OrderBean.OrderItemBean?) {
        helper!!.setText(R.id.goods_item_desc , item!!.ProductName)
        helper!!.setText(R.id.goods_item_count , "x"+item!!.ProductNum)

        var price=  item!!.ProductPrice.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()

        helper!!.setText(R.id.goods_item_unitPrice , "￥${price}" )
        helper!!.setText(R.id.goods_item_spec , item!!.ProductSpec)

        helper!!.getView<SimpleDraweeView>(R.id.goods_item_logo).setImageURI(item!!.ProductImgUrl)
    }
}