package com.huotu.android.mifang.bean

import java.math.BigDecimal

data class MyWalletBean (var UserIntegral:BigDecimal= BigDecimal.ZERO,
                         var UserTempIntegral:BigDecimal= BigDecimal.ZERO,
                         var UserMBean:BigDecimal= BigDecimal.ZERO,
                         var CouponNum:Int)