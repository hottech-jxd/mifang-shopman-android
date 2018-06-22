package com.huotu.android.mifang.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.OrderBean

class OrderAdpter(data:ArrayList<OrderBean>)
    :BaseQuickAdapter<OrderBean,BaseViewHolder>(R.layout.layout_order_item , data ) {

    override fun convert(helper: BaseViewHolder?, item: OrderBean?) {


        Glide.with(mContext)
                .load(item!!.url)
                .into( helper!!.getView(R.id.order_item_logo))
    }
}