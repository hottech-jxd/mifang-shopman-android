package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView
import retrofit2.http.Field

interface BuyContract {
    interface Presenter: IPresenter {
        fun getPaymentItems()
        fun getRenewGoods()
        fun getBuyInfo()
        fun submitOrder(quantity:Int, payType:Int)
        fun getAgentUpgradeGoods()
        fun getAddressList()
        fun submitAgentUpgradeOrder(shipName:String
                                    ,shipMobile:String
                                    , shipAddress:String
                                    ,shipArea:String
                                    , shipAreaCode:String
                                    ,payType:Int)
    }

    interface View: IView<Presenter> {
        fun getPaymentItemsCallback(apiResult: ApiResult<ArrayList<PaymentItem>>)
        fun getRenewGoodsCallback(apiResult: ApiResult<GoodsBean>)
        fun submitOrderCallback(apiResult: ApiResult<InviteOrderBean>)
        fun getAgentUpgradeGoodsCallback(apiResult: ApiResult<AgentUpgradeGoodsBean>)
        fun getAddressListCallback(apiResult: ApiResult<ArrayList<AddressBean>>)
        fun submitAgentUpgradeOrderCallback(apiResult: ApiResult<InviteOrderBean>)
    }
}