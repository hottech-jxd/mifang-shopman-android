package com.huotu.android.mifang.bean

data class MyBean (
        var UserMBean :Int,
        var WxNickName:String,
        var WxHeadImg:String,
        var LevelName:String,
        var UserName:String,
        var UserBalance:Double,/**用户可用余额*/
        var UserIntegral:Int,
        var UserTempIntegral:Int,
        var UserGold:Double,
        var UnpaidCount: Int,
        var AftermarketCount:Int,
        var UnSendCount:Int,
        var UnDeliverCount:Int,
        var ExpireTime:String,
        var SurplusDays:Int,
        var TipStr:String, /*TipStr*/
        var IsAgent:Boolean,/*是否是代理商*/
        var BackGroundModel:BackGroundModelBean?,
        var ADList:ArrayList<ADBean>?
){
    data class BackGroundModelBean( var Id:Long,
                var CustomerId:Long,
            var TagColor:String,
            var TagFontColor:String,
            var FontColor:String,
            var UserCenterImg:String,
            var Template:String)

    data class ADBean(var ImgURL:String ,var LinkURL:String){
        override fun toString(): String {
            return ImgURL
        }
    }
}