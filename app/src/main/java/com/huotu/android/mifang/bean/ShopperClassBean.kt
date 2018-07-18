package com.huotu.android.mifang.bean

data class ShopperClassBean(var dataId:Long ,var logo:String? , var title:String? ,var content:String?, var outerLink:String? )

data class ShopperClassCategoryBean(var typeId:Int , var title:String)

data class ShopperAccountInfo( var goodsDeposit:String,//货款
        var account:String,//余额
            var bean:String//觅豆
 )

data class ShopperInfo(  var logo:String,//店铺头像
     var name:String,//店铺名称
  var shareTitle:String,//分享标题
 var shareContent:String//分享内容
 )