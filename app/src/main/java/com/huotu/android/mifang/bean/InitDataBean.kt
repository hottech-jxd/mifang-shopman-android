package com.huotu.android.mifang.bean

/**
 * Created by jinxiangdong on 2018/2/5.
 */
data class InitDataBean (
        var userId:Long,
        var nickName:String?,
        var userHead:String?,
        var token:String?,//32位随机字符
        var bindedMobile:Boolean,//是否绑定手机号
        var aboutUs:String,//关于我们
        var iosCheck:Boolean, //是否ios审核
        var mobile:String?,
        var userRoleType:Int,
        var userRoleName:String?
)
