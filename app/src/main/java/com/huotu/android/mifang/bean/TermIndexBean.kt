package com.huotu.android.mifang.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

data class TermIndexBean ( var BelongOneNumByToday :Int,
            var BelongTwoNumByToday:Int,
            var BelongOneNumByMonth:Int,
            var BelongTwoNumByMonth:Int ,
            var TeamInfo :ArrayList<TeamInfoBean>?)

data class TeamInfoBean( var LevelName:String,
                var BelongOneMemberNum:Int,
            var BelongTwoMemberNum:Int,
            var LevelId:Int)

data class TermIndex0Entity(var title:String , var flag:Int):MultiItemEntity{
    override fun getItemType(): Int {
        return 0
    }
}

data class TermIndex1Entity(var content1 :String,var content2:String,var content3:String ):MultiItemEntity{

    override fun getItemType(): Int {
        return 1
    }
}

data class TermIndex2Entity(  var termInfo: TeamInfoBean ):MultiItemEntity{

    override fun getItemType(): Int {
        return 2
    }
}