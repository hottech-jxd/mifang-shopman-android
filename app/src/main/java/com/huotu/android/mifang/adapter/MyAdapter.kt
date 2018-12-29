package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.MyMenu

class MyAdapter(var data:ArrayList<MyMenu>) : BaseQuickAdapter< MyMenu ,BaseViewHolder>(R.layout.layout_my_menu_item,  data) {

    override fun convert(helper: BaseViewHolder?, item: MyMenu?) {


    }
}