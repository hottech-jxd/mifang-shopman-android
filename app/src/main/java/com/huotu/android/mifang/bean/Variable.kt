package com.huotu.android.mifang.bean

data class Variable (
    /**
     * 用户信息
     */
    var userBean: UserBean? = null,
    var initDataBean:InitDataBean?=null,
    var wechatUser:WechatUser?=null,
    /*省市区数据集合*/
    var proviceData:ArrayList<ProvinceCityArea>?=null,
    var cityData:ArrayList<ProvinceCityArea>?=null
)