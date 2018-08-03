package com.huotu.android.mifang.bean

data class InviteBean ( var UserNickName :String,
var UserHeadImgURL:String,
var UserLevelName:String,
var LevelId: Int,
var ProfitAmount:String/*收益金额*/,
var InvitationMemberNum:Int/*邀请人数*/,
var SurplusNum:Int/*剩余账号数*/,
var IsAgent:Boolean,
            var    BuddyPoster:String?,	   //营养师海报
            var    AgentPoster:	String?,	   //代理商海报
            var    ProgramBuddyURL:String?, //邀请好友成为小伙伴小程序商品地址
            var    H5BuddyURL:	String?,    //邀请好友成为小伙伴H5商品地址
            var    ProgramAgentURL:String?,	//邀请好友成为代理商小程序商品地址
            var    H5AgentURL	:String?,	//邀请好友成为代理商H5商品地址
            var    BuyBuddyNumURL:String?,	//购买开店账号地址
            var    UpgradeAgentURL:String?,	//邀请好友升级代理商地址
            var    ApplyAgentURL:String?	    //申请成为代理商地址
 )