package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.IncomeDetailEntity

class IncomeDetailAdapter(data :ArrayList<IncomeDetailEntity>):BaseMultiItemQuickAdapter<IncomeDetailEntity , BaseViewHolder>( data) {

    init {
        addItemType(1, R.layout.layout_income_detail_item)
        addItemType(2,R.layout.layout_income_detail_item)
        addItemType(3, R.layout.layout_income_detail_item)
    }

    override fun convert(helper: BaseViewHolder?, item: IncomeDetailEntity?) {

        helper!!.setText(R.id.income_detail_item_header_1, item!!.incomeDetailBean.name)
        helper!!.setText(R.id.income_detail_item_header_2, item!!.incomeDetailBean.name2)
        helper!!.setText(R.id.income_detail_item_header_3, item!!.incomeDetailBean.name3)

    }
}