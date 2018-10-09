package com.huotu.android.mifang.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.OrderBean
import com.huotu.android.mifang.util.DateUtils
import java.math.BigDecimal
import java.util.*

class OrderAdapter(data:ArrayList<OrderBean>)
    :BaseQuickAdapter<OrderBean,BaseViewHolder>(R.layout.layout_order_item , data ) {

    override fun convert(helper: BaseViewHolder?, item: OrderBean?) {

        helper!!.setText(R.id.order_item_orderNo , "订单号:"+ item!!.OrderID)
        helper!!.setText(R.id.order_item_status, item!!.OrderShipStatus)


        var createTime = DateUtils.formatDate(item!!.CreatTime)

        helper!!.setText(R.id.order_item_time , createTime)
        helper!!.setText(R.id.order_item_label2, "共"+ item!!.OrderGoodsNum +"件商品 合计")

        var price = item!!.OrderFinalAmount.setScale(2,BigDecimal.ROUND_HALF_UP)
        helper!!.setText(R.id.order_item_total , "￥"+ price.toPlainString() )

        var score = item!!.Integral.setScale(2,BigDecimal.ROUND_HALF_UP).divide(BigDecimal(100))
        helper!!.setText(R.id.order_item_score , score.stripTrailingZeros().toPlainString() )

        helper!!.setText(R.id.order_item_buy_name, "购买用户:"+ item!!.wxNickName)

        var goodsAdapter=GoodsAdapter(item!!.OrderItemInfo)
        var recyclerView = helper!!.getView<RecyclerView>(R.id.order_item_recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(mContext)
        recyclerView.adapter = goodsAdapter
    }


}