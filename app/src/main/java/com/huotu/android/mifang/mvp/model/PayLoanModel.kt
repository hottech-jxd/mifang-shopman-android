package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class PayLoanModel {
    fun getDepositList( pageIndex :Int , pageSize:Int ): Observable<ApiResult<ArrayList<PayLoanBean>>> {
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.getDepositList( pageIndex , pageSize)
    }
    fun getFrozenFlow( pageIndex :Int , pageSize:Int ): Observable<ApiResult<ArrayList<FrozenFlow>>> {
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.getFrozenFlow( pageIndex , pageSize)
    }
    fun getDepositIndex(): Observable<ApiResult<DepositBean>> {
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.getDepositGoods()
    }
    fun submitGoodsDepositOrder(payType :Int , goodsId:Long , productId:Long): Observable<ApiResult<DepositOrderBean>> {
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.submitGoodsDepositOrder(goodsId, productId, payType)
    }
}