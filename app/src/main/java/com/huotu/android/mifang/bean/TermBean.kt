package com.huotu.android.mifang.bean

data class TermItemBean(var UserId:Long,
var NickName:String,
var UserImage :String,
var UserLevelName: String,
var FansAmount: String,
var OfferScore: String,
var RegisterTime :String,
var Recommender:String,
var Memo :String,
var Relation:Int, /*0直接1间接*/
var LastLoginTime :Long,
var UserType:Int,
var ExpireTime:Long)

data class TermBean( var TotalCount:Long, var CurrentCount:Long, var TeamItem:ArrayList<TermItemBean>?)