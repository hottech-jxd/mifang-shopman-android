package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.TermBean

class TermAdpter(data :ArrayList<TermBean>) :BaseQuickAdapter<TermBean,BaseViewHolder>( R.layout.layout_term_item , data) {

    override fun convert(helper: BaseViewHolder?, item: TermBean?) {

    }
}