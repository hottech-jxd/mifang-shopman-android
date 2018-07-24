package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface PayLoanContract {
    interface Presenter: IPresenter {
        fun getDepositIndex()
        fun getDepositList(  pageIndex:Int , pageSize:Int)
        fun getFrozenFlow( pageIndex: Int, pageSize: Int)
        fun getPaymentItems()
        fun submitGoodsDepositOrder(payType :Int , goodsId:Long , productId:Long)
    }

    interface View: IView<Presenter> {
        fun getDepositIndexCallback(apiResult: ApiResult<DepositBean>)
        fun getDepositListCallback(apiResult: ApiResult<ArrayList<PayLoanBean>>)
        fun getFrozenFlowCallback(apiResult: ApiResult<ArrayList<FrozenFlow>>)
        fun getPaymentItemsCallback(apiResult: ApiResult<ArrayList<PaymentItem>>)
        fun submitGoodsDepositOrderCallback(apiResult: ApiResult<DepositOrderBean>)
    }
}