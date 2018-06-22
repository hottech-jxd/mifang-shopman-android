package com.huotu.android.mifang.http


import com.huotu.android.mifang.bean.Constants

data class HeaderParameter(var appVersion: String? = null,
                           var hwid: String? = null,
                           var mobileType: String? = null,
                           var osType :Int = Constants.OS_TYPE,
                           var osVersion: String? = "",
                           var userId: Long = 0,
                           var userToken :String= "",
                           var customerId:Long = Constants.CUSTOMERID)