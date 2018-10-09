package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseApplication
import com.huotu.android.mifang.bean.GoodsInfoBean
import java.math.BigDecimal

class ShopGoodsAdapter(data:ArrayList<GoodsInfoBean>)
    :BaseQuickAdapter<GoodsInfoBean,BaseViewHolder>( R.layout.layout_shop_goods_item , data) {

    override fun convert(helper: BaseViewHolder?, item: GoodsInfoBean?) {
        helper!!.getView<SimpleDraweeView>(R.id.good_item_1_logo).setImageURI( item!!.PicUrl )
        helper!!.setText(R.id.good_item_1_title, item!!.Name)
        helper!!.setText(R.id.good_item_1_price, "￥"+item!!.Price.stripTrailingZeros().toPlainString())

        var scoreString :String?

        if(BaseApplication.instance!!.variable.userBean==null || BaseApplication.instance!!.variable.userBean!!.userRoleType == 101){
            var score = BigDecimal( item!!.EarnIntegral)
            score.setScale(2,BigDecimal.ROUND_HALF_UP)
            score = score.divide(BigDecimal(100))
            scoreString = score.stripTrailingZeros().toPlainString()
        }else{
            scoreString = item!!.Price.multiply( item!!.MfAgent_Discount ).divide(BigDecimal(10)).stripTrailingZeros().toPlainString()
        }

        helper!!.setText(R.id.good_item_1_final_price, "￥${scoreString}" )


        var label= if( BaseApplication.instance!!.variable.userBean==null || BaseApplication.instance!!.variable.userBean!!.userRoleType == 101) "佣金" else "代理价"
        helper!!.setText(R.id.good_item_1_final_label, label)
    }
}

