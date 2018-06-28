package com.huotu.android.mifang.bean

data class Quan (
        var dataId:Long/*数据ID*/,
        var Title:String,
        var name : String/*素材号名称*/,
        var logo :String/*素材号logo*/,
        var Content:String ,
        var Type:Int /*0:普通 1:图片 2:视频*/,
        var ImageUrls :ArrayList<String>?,
        var SmallImageUrls:ArrayList<String>?/*缩略图*/,
        var VideoUrls:ArrayList<String?>?/*视频地址*/,
        var VideoPictureUrls:ArrayList<String?>?/*视频图片*/,
        var ShareTitle:String,
        var ShareImage:String,
        var ShareDescription:String,
        var GoodsId:Long,
        var TurnAmount:Long /*	转发次数*/,
        var Time :String ,
        var Profit:String /*可赚利润*/
)
