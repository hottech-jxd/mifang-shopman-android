package com.huotu.android.mifang.bean


data class PayModel (
        var wxAppId:String?,
        var wxAppMchId:String?,
    var customId: String? = null,
    var tradeNo: String? = null,
    var paymentType: String? = null,
    /***
     * 微信支付金额
     */
    var amount: Int = 0,
    var detail: String? = null,
    var notifyurl: String? = null,
    var attach: String? = null,
    /***
     * 支付宝支付金额
     */
    var aliAmount: String? = null,
        var wxPrePayId :String?=null
)
