package com.huotu.android.mifang.bean

data class ShopperClassBean(var dataId:Long ,var logo:String? , var title:String? ,var content:String?, var outerLink:String? )

data class ShopperClassCategoryBean(var typeId:Int , var title:String)

data class ShopperAccountInfo(
        //var goodsDeposit:String,//货款
        //var account:String,//余额
        //var bean:String,//觅豆
        var EnabledPayPassword:String,
        var UsefulBalance: String,
        var UsefulCptGold: String,
        var UsefulIntegral: String ,
        var UsefulIntegralAmount: String,
        var UsefulMiBean: String,
        var UserRegTime: String,
        var UserTempIntegral:String,
        var UserType:String,
        var GoodsDeposit:String
 )

data class ShopperInfo(
        var logo:String,//店铺头像
     var Name:String,//店铺名称
  var ShareTitle:String,//分享标题
 var ShareContent:String,//分享内容
var StoreId:Int,
 var UserId:Long
)