package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class OrderModel {
    fun getProfitOrderList(
            SearchTime:Int=-1 /*int	查询类型（默认-1全部,0-日,1-周,2-月）*/,
            ShipStatus:Int=-1	/*发货状态(默认-1全部,0-待发货,1-待收货,4-已退货,5-确认收货)*/,
            SearchYear:Int=-1	/*查询年，默认-1*/,
            SearchMonth:Int=-1 /*	查询月，默认-1 */,
            SearchDay:Int=-1	/*查询天,默认-1*/,
            WeekNum:Int=-1 /*	查询周,默认-1*/,
            OrderSourceType:Int=-1 /*订单来源类型,默认-1，主要用于查看邀请的营养师订单，传入100 —新增条件*/,
            PageIndex:Int=1	/*页码，默认1*/,
            PageSize:Int = Constants.PAGE_SIZE
    ): Observable<ApiResult<ArrayList<OrderBean>>> {
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.getProfitOrderList(SearchTime ,ShipStatus, SearchYear , SearchMonth,SearchDay,WeekNum, OrderSourceType , PageIndex,PageSize )
    }

}