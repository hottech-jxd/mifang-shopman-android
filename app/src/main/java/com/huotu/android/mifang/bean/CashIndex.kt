package com.huotu.android.mifang.bean

import java.math.BigDecimal

data class CashIndex (var AccountId :Long,
                      var UserRealName:String,
                      var AccountInfo:String,
                      var UserIntegral:BigDecimal= BigDecimal.ZERO,
                      var HandlingRate:Double,
                      var Handling:Long,
                      var BaseMoney:BigDecimal= BigDecimal.ZERO,
                      var MonthCount:Int,
                      var IsSettingPayWord:Boolean/*是否设置了支付密码*/)