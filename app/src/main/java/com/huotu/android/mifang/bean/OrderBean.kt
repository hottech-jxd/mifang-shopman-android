package com.huotu.android.mifang.bean

data class OrderBean(var OrderID:String,
                var wxNickName:String,
                var LevelName:String,
                var DepthName:String,
                var Integral:String ,
                var OrderShipStatus:String,
                var OrderFinalAmount:String,
                var OrderGoodsNum:String,
                var CreatTime:Long,
                var OrderItemInfo:ArrayList<OrderItemBean>
              ) {

    data class OrderItemBean(var ProductName:String , var ProductSpec:String , var ProductPrice:String , var ProductNum:String ,var ProductImgUrl:String,var OrderID:String)
}