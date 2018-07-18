package com.huotu.android.mifang.bean

import java.math.BigDecimal


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


data class GoodsDetailBean(var pictures:String/*商品图片 多个,分割*/
                           , var title:String/**/
                           , var price:String /*商品标题*/
                           , var agentPrcie:String /*代理价（代理商）*/
                           , var commission:String /*佣金(营养师)*/
                           , var intro:ArrayList<String> /*详细介绍 图片列表*/
                           , var memo:String//说明
                           , var shareUrl:String    )