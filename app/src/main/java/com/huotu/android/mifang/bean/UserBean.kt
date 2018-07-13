package com.huotu.android.mifang.bean

data class UserBean (var userId :Long ,
                     var LoginName:String ,
                     var WxNickName:String?,
                     var nickName:String?,
                     var userHead:String?,
                     var token:String?,
                     var bindedMobile:Boolean,
                     var WxHeadImg : String? )