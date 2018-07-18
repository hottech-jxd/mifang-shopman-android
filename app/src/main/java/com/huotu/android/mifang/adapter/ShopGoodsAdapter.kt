package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.GoodsInfoBean

class ShopGoodsAdapter(data:ArrayList<GoodsInfoBean>)
    :BaseQuickAdapter<GoodsInfoBean,BaseViewHolder>( R.layout.layout_shop_goods_item , data) {

    override fun convert(helper: BaseViewHolder?, item: GoodsInfoBean?) {
        helper!!.getView<SimpleDraweeView>(R.id.good_item_1_logo).setImageURI( item!!.PicUrl )
        helper!!.setText(R.id.good_item_1_title, item!!.Name)
        helper!!.setText(R.id.good_item_1_price, "￥"+item!!.Price)
        helper!!.setText(R.id.good_item_1_final_price, "￥"+item!!.UserPrice)
    }
}

