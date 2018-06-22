package com.huotu.android.mifang.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

data class IncomeDetailBean (var name:String,var name2:String,var name3:String)

data class IncomeDetailEntity(var incomeDetailBean: IncomeDetailBean, var type:Int ):MultiItemEntity {

    override fun getItemType(): Int {
        return type
    }
}