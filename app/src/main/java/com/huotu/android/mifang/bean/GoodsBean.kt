package com.huotu.android.mifang.bean

import java.math.BigDecimal

data class StoreIndex(var PageCount :Int , var PageIndex:Int , var PageSize:Int , var Total:Long , var Rows :ArrayList<GoodsInfoBean>?)

data class GoodsBean(var GoodsImgURL:String	,
                        var GoodsName:String	,
                    var  GoodsPrice:BigDecimal)

data class GoodsInfoBean(

        var GoodsId: Long,
        var Name: String,
        var CatId: Long,
     var PicUrl:String,
    var Store: Int,
    var Price:String,
    var MktPrice:String,
    var UserPrice:String,
    var EarnIntegral:String,
   var EarnMiBean:String,
    var IsFav:Int,
    var Sales:String)


data class GoodsDetailBean(var pictures:String?/*商品图片 多个,分割*/
                           , var title:String?/**/
                           , var price:String? /*商品标题*/
                           , var agentPrcie:String? /*代理价（代理商）*/
                           , var commission:String? /*佣金(营养师)*/
                           , var intro:ArrayList<String?>? /*详细介绍 图片列表*/
                           , var memo:String?//说明
                           , var shareUrl:String?    )



data class DepositBean(var MyDeposit	:String ,//	我的货款
                        var OweDeposit	:String,//	已欠货款
                       var GoodsItems:ArrayList<DepositItem>?){
    data class DepositItem(var checked:Boolean=false , var GoodsId:Long , var ProductId:Long , var DepositPrice:BigDecimal= BigDecimal.ZERO //货款商品价格
     )
}