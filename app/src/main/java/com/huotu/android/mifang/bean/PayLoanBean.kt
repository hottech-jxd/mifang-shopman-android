package com.huotu.android.mifang.bean

import java.math.BigDecimal


data class PayLoanBean( var ChangeDeposit:String,
    var ChangeTime:String,
    var ChangeDesc:String)


data class FrozenFlow(var money:BigDecimal,//金额
  var time:String,//时间
    var memo:String )