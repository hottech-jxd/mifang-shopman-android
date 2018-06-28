package com.huotu.android.mifang.bean

data class SettingBean (
    var RealName:String,
    var UserSex:String,
    var UserBirthday:String,
    var UserCardNo:String,
    var UserMobile:String,
    var UserWxNo:String,
    var UserCityName:String,
    var PayPassworded:Int /*支付密码是否已设置 0未重置 1已重置*/,
    var PayPasswordStatus:Int /*是否开启支付保护 0未开启 1已开启*/
)