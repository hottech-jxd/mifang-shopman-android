package com.huotu.android.mifang.bean

import java.math.BigDecimal

data class ProfitIndexBean (var UserIntegral	: BigDecimal= BigDecimal.ZERO    /*用户可用积分 */,
                            var UserTempIntegral	:BigDecimal= BigDecimal.ZERO	/*待结算积分*/,
                            var     UserProfitByToday	:BigDecimal	/*今日收益*/,
                            var      UserOrderNumByToday	:Int	/*今日订单数*/,
                            var      UserProfitByYesterday:	BigDecimal	/*昨日收益*/,
                            var       UserOrderNumByYesterday	:Int	/*昨日订单数*/,
                            var       UserProfitByWeek	:BigDecimal	/*本周收益*/,
                            var       UserOrderNumByWeek	:Int	/*本周订单数*/,
                            var        UserProfitByLastWeek	:BigDecimal	/*上周收益*/,
                            var        UserOrderNumByLastWeek	:Int	/*上周订单数*/,
                            var        UserProfitByMonth	:BigDecimal	/*本月收益*/,
                            var       UserOrderNumByMonth	:Int	/*本月订单数*/,
                            var       UserProfitByLastMonth	:BigDecimal	/*上月收益*/,
                            var       UserOrderNumByLastMonth	:Int	/*上月订单数*/)