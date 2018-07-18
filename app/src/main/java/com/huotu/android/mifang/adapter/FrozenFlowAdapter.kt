package com.huotu.android.mifang.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.FrozenFlow
import com.huotu.android.mifang.bean.MiBean
import com.huotu.android.mifang.bean.PayLoanBean
import com.huotu.android.mifang.bean.ScoreBean

class FrozenFlowAdapter(data:ArrayList<FrozenFlow>)
    :BaseQuickAdapter<FrozenFlow,BaseViewHolder>( R.layout.layout_payloan_item , data) {

    override fun convert(helper: BaseViewHolder?, item: FrozenFlow?) {

        helper!!.setText(R.id.payloan_item_value, item!!.money+"元")
        helper!!.setText(R.id.payloan_item_desc , item!!.memo)
        helper!!.setText(R.id.payloan_item_desc2 , "")
        helper!!.setGone(R.id.payloan_item_desc2, false)
        helper!!.setText(R.id.payloan_item_time, "申请时间:"+item!!.time)

    }
}
