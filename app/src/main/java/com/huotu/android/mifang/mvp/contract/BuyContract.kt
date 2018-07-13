package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface BuyContract {
    interface Presenter: IPresenter {
        fun getPaymentItems()
        fun getRenewGoods()
        fun getBuyInfo()
        fun submitOrder(quantity:Int, payType:Int)
    }

    interface View: IView<Presenter> {
        fun getPaymentItemsCallback(apiResult: ApiResult<ArrayList<PaymentItem>>)
        fun getRenewGoodsCallback(apiResult: ApiResult<GoodsBean>)
        fun submitOrderCallback(apiResult: ApiResult<InviteOrderBean>)

    }
}