package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.GoodsBean
import com.huotu.android.mifang.bean.InviteOrderBean
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
}