package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class BuyModel {
    fun getRenewGoods(): Observable<ApiResult<GoodsBean>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.getRenewGoods()
    }

    fun submitInviteOrder(quantity:Int , payType:Int ):Observable<ApiResult<InviteOrderBean>>{
        val apiService =RetrofitManager.getApiService()
        return apiService!!.submotInviteOrder(quantity , payType)
    }

    fun getAgentUpgradeGoods():Observable<ApiResult<AgentUpgradeGoodsBean>>{
        val apiService =RetrofitManager.getApiService()
        return apiService!!.getAgentUpgradeGoods()
    }

    fun getAddressList():Observable<ApiResult<ArrayList<AddressBean>>>{
        val apiService =RetrofitManager.getApiService()
        return apiService!!.getAddressList()
    }

    fun submitAgentUpgradeOrder(shipName:String
                                ,shipMobile:String
                                , shipAddress:String
                                ,shipArea:String
                                , shipAreaCode:String
                                ,payType:Int ):Observable<ApiResult<InviteOrderBean>>{
        val apiService =RetrofitManager.getApiService()
        return apiService!!.submitAgentUpgradeOrder(shipName , shipMobile ,shipAddress, shipArea,shipAreaCode , payType)
    }
}