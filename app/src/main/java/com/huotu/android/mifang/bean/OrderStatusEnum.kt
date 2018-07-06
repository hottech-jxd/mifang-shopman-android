package com.huotu.android.mifang.bean

enum class OrderStatusEnum(var id:Int, var desc:String) {
    All(-1,"全部"),
    Delivery(0,"待发货"),
    Receive(1,"待收货"),
    Back(4,"已退款"),
    Finish(5,"已完成")
}