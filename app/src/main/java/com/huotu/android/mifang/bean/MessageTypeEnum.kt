package com.huotu.android.mifang.bean

enum class MessageTypeEnum(var id:Int , var desc:String) {
    All(0,"全部"),
    SystemMessage(1,"系统通知"),
    MallMessage(2,"商城通知"),
    ActivityMessage(3,"活动通知"),
    RegisterMessage(4,"注册通知"),
    PayMessage(5,"支付通知")
}