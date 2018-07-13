package com.huotu.android.mifang.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.MiBean
import com.huotu.android.mifang.bean.PayLoanBean
import com.huotu.android.mifang.bean.ScoreBean

class PayloanAdapter(data:ArrayList<PayLoanBean>)
    :BaseQuickAdapter<PayLoanBean,BaseViewHolder>( R.layout.layout_payloan_item , data) {

    override fun convert(helper: BaseViewHolder?, item: PayLoanBean?) {

        helper!!.setText(R.id.payloan_item_value, item!!.ChangeDeposit+"元")
        helper!!.setText(R.id.payloan_item_desc , item!!.ChangeDesc)
        helper!!.setText(R.id.payloan_item_desc2 , "")
        helper!!.setText(R.id.payloan_item_time, "申请时间:"+item!!.ChangeTime)

    }
}
