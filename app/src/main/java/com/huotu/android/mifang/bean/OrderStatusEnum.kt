package com.huotu.android.mifang.bean

enum class OrderStatusEnum(var id:Int, var desc:String) {
    All(0,"全部"),
    Delivery(1,"待发货"),
    Receive(2,"待收货"),
    Finish(3,"已完成"),
    Back(4,"已退款")
}