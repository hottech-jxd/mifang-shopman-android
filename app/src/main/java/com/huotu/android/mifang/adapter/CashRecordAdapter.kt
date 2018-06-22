package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.CashRecord

class CashRecordAdapter(data :ArrayList<CashRecord>):BaseQuickAdapter<CashRecord,BaseViewHolder>( R.layout.layout_cash_record , data) {

    override fun convert(helper: BaseViewHolder?, item: CashRecord?) {

    }
}