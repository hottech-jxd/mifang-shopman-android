package com.huotu.android.mifang.bean

data class UserBean (var userId :Long ,
                     var LoginName:String?,
                     var WxNickName:String?,
                     var nickName:String?,
                     var userHead:String?,
                     var token:String?,
                     var bindedMobile:Boolean,
                     var mobile:String?,
                     var userRoleType:Int,/*100 普通会员 101营养师 102代理商*/
                     var userRoleName:String?,
                     var WxHeadImg : String? )