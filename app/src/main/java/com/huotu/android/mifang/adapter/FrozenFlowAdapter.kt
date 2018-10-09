package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.FrozenFlow
import java.math.BigDecimal

class FrozenFlowAdapter(data:ArrayList<FrozenFlow>)
    :BaseQuickAdapter<FrozenFlow,BaseViewHolder>( R.layout.layout_payloan_item , data) {

    override fun convert(helper: BaseViewHolder?, item: FrozenFlow?) {

        var money = item!!.money.setScale(2,BigDecimal.ROUND_HALF_UP).divide(BigDecimal(100))
        helper!!.setText(R.id.payloan_item_value, money.stripTrailingZeros().toPlainString()+"元")
        helper!!.setText(R.id.payloan_item_desc , item!!.memo)
        helper!!.setText(R.id.payloan_item_desc2 , "")
        helper!!.setGone(R.id.payloan_item_desc2, false)
        helper!!.setText(R.id.payloan_item_time, "申请时间:"+item!!.time)

    }
}
