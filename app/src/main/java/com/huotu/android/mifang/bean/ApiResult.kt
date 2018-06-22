package com.huotu.android.mifang.bean

data class ApiResult<T> (
    var code:Int=0,
    var msg:String="",
    var data:T?=null
    )