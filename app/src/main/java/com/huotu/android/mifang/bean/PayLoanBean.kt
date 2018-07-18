package com.huotu.android.mifang.bean


data class PayLoanBean( var ChangeDeposit:String,
    var ChangeTime:String,
    var ChangeDesc:String)


data class FrozenFlow(var money:String,//金额
  var time:String,//时间
    var memo:String )