package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.CashRecord

class CashRecordAdapter(data :ArrayList<CashRecord>):BaseQuickAdapter<CashRecord,BaseViewHolder>( R.layout.layout_cash_record , data) {

    override fun convert(helper: BaseViewHolder?, item: CashRecord?) {
        helper!!.setText(R.id.cash_record_money , item!!.ApplyMoney+"元")
        helper!!.setText(R.id.cash_record_status , item!!.ApplyStatus)
        helper!!.setText(R.id.cash_record_name , item!!.RealName)
        helper!!.setText(R.id.cash_record_pay_name, item!!.AccountInfo)
        helper!!.setText(R.id.cash_record_apply_time , "申请日期："+ item!!.ApplyTime)
    }
}