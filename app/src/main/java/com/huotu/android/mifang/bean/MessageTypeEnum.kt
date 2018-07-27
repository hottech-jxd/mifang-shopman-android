package com.huotu.android.mifang.bean

enum class MessageTypeEnum(var id:Int , var desc:String) {
    All(-1,"全部"),
    SystemMessage(0,"系统通知"),
    RegisterMessage(1,"注册通知"),
    PayMessage(2,"支付通知")
}