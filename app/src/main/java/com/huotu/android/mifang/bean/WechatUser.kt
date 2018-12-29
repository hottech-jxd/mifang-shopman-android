package com.huotu.android.mifang.bean

data class WechatUser(
        var openid:String,
    var nickname :String,
        var sex:Int,
 var province:String,
 var city :String,
var country:String,
var headimgurl:String,
var privilege:ArrayList<String>,
var unionid:String ,  var errcode:Int , var errmsg:String
)