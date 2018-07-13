package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.GoodsBean

class ShopGoodsAdapter(data:ArrayList<GoodsBean>)
    :BaseQuickAdapter<GoodsBean,BaseViewHolder>( R.layout.layout_shop_goods_item , data) {

    override fun convert(helper: BaseViewHolder?, item: GoodsBean?) {

    }
}

