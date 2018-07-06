package com.huotu.android.mifang.bean

data class ProfitIndexBean (var UserIntegral	:Int	/*用户可用积分 */,
    var UserTempIntegral	:Int	/*待结算积分*/,
                       var     UserProfitByToday	:Int	/*今日收益*/,
                      var      UserOrderNumByToday	:Int	/*今日订单数*/,
                      var      UserProfitByYesterday:	Int	/*昨日收益*/,
                     var       UserOrderNumByYesterday	:Int	/*昨日订单数*/,
                     var       UserProfitByWeek	:Int	/*本周收益*/,
                     var       UserOrderNumByWeek	:Int	/*本周订单数*/,
                    var        UserProfitByLastWeek	:Int	/*上周收益*/,
                    var        UserOrderNumByLastWeek	:Int	/*上周订单数*/,
                    var        UserProfitByMonth	:Int	/*本月收益*/,
                     var       UserOrderNumByMonth	:Int	/*本月订单数*/,
                     var       UserProfitByLastMonth	:Int	/*上月收益*/,
                     var       UserOrderNumByLastMonth	:Int	/*上月订单数*/)