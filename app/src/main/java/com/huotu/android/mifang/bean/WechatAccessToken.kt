package com.huotu.android.mifang.bean

data class WechatAccessToken (
        var access_token :String,
    var expires_in:Long,
    var refresh_token :String,
var   openid:String,
var scope:String ,
                              var errcode:Int , var errmsg:String)