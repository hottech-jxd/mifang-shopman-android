package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.OrderBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView


interface OrderContract {
    interface Presenter: IPresenter {
        fun getProfitOrderList(
                SearchTime:Int=-1 /*int	查询类型（默认-1全部,0-日,1-周,2-月）*/,
                ShipStatus:Int=-1	/*发货状态(默认-1全部,0-待发货,1-待收货,4-已退货,5-确认收货)*/,
                SearchYear:Int=-1	/*查询年，默认-1*/,
                SearchMonth:Int=-1 /*	查询月，默认-1 */,
                SearchDay:Int=-1	/*查询天,默认-1*/,
                WeekNum:Int=-1 /*	查询周,默认-1*/,
                PageIndex:Int=1	/*页码，默认1*/,
                PageSize:Int = Constants.PAGE_SIZE
        )

    }

    interface View: IView<Presenter> {
        fun getProfitOrderListCallback(apiResult: ApiResult<ArrayList<OrderBean>>)

    }
}