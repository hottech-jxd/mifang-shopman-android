package com.huotu.android.mifang.bean

data class InviteOrderBean (
                    var payType	:Int /*支付类型，0-微信，1-支付宝*/,
                    var WxAppId	:String /*	微信AppId*/,
                    var WxAppMchId:String /*	微信MchId*/,
                    var PrepayId:String /*	预支付Id*/,
                    var UnionOrderId:String /*	联合单号*/,
                    var aliPayOrderString :String	/*支付宝支付加密信息*/)