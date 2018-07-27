package com.huotu.android.mifang.bean

import java.math.BigDecimal

/**
 * 开店订单
 */
data class InviteOrderBean (
                    var payType	:Int /*支付类型，0-微信，1-支付宝，2-小程序*/,
                    var WxAppId	:String /*	微信AppId*/,
                    var WxAppMchId:String /*	微信MchId*/,
                    var PrepayId:String /*	预支付Id*/,
                    var UnionOrderId:String /*	联合单号*/,
                    var aliPayOrderString :String	/*支付宝支付加密信息*/)

/**
 * 货款订单数据类
 */
data class DepositOrderBean(
         var payType	:Int /*支付类型，0-微信，1-支付宝*/,
         var WxAppId	:String /*	微信AppId*/,
         var WxAppMchId:String /*	微信MchId*/,
         var PrepayId:String /*	预支付Id*/,
         var UnionOrderId:String /*	联合单号*/,
         var aliPayOrderString :String,	/*支付宝支付加密信息*/
        var SurplusAmount :BigDecimal= BigDecimal.ZERO /*剩余需要付的金额，当等于0时，表示已经完全抵用*/ )