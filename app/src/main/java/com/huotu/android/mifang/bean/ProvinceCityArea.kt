package com.huotu.android.mifang.bean

data class ProvinceCityArea(var value:String ,var text:String, var children:ArrayList<ProvinceCityArea>?){

    override fun toString(): String {
        //return super.toString()
        return text
    }
}