package com.huotu.android.mifang.bean

import java.math.BigDecimal

data class OrderBean(var OrderID:String,
                var wxNickName:String,
                var LevelName:String,
                var DepthName:String,
                var Integral:BigDecimal ,
                var OrderShipStatus:String,
                var OrderFinalAmount:BigDecimal,
                var OrderGoodsNum:String,
                var CreatTime:Long,
                var OrderItemInfo:ArrayList<OrderItemBean>
              ) {

    data class OrderItemBean(var ProductName:String ,
                             var ProductSpec:String ,
                             var ProductPrice:BigDecimal ,
                             var ProductNum:String ,
                             var ProductImgUrl:String,
                             var OrderID:String)
}