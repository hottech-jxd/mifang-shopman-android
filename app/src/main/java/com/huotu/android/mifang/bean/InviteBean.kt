package com.huotu.android.mifang.bean

data class InviteBean ( var UserNickName :String,
var UserHeadImgURL:String,
var UserLevelName:String,
var LevelId: Int,
var ProfitAmount:String/*收益金额*/,
var InvitationMemberNum:Int/*邀请人数*/,
var SurplusNum:Int/*剩余账号数*/,
var IsAgent:Boolean)