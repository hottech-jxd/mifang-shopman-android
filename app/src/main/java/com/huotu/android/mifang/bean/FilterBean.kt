package com.huotu.android.mifang.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

class FilterEntry(var filterBean: FilterBean,
                  var type:Int =1 ,
                  var selected :Boolean=false) :MultiItemEntity{


    override fun getItemType(): Int {
        return type
    }
}


data class FilterBean(var fid:Int,var name:String , var pid:Int)