package com.huotu.android.mifang.bean

import java.math.BigDecimal

/**
 * Created by jinxiangdong on 2017/12/20.
 */

class PayOrderBean {
    var orderNo: String? = null
    var payType: Int = 0
    var tradeType: Int = 0
    /**
     * 剩余需要支付的金额，是否已支付，根据该字段来判断
     */
    var surplusAmount: BigDecimal? = null
    var bizParameters: BizParameter? = null


    inner class BizParameter {
        /**
         * 支付完成后去的页面
         */
        var returnUrl: String? = null
        /**
         * 使用H5支付跳转去的网址
         */
        var wapPayUrl: String? = null
        /**
         * app发起支付请求用到的签名好的包裹
         */
        var bizPackage: String? = null
    }
}
