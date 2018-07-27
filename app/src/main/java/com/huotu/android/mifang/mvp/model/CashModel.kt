package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.CashIndex
import com.huotu.android.mifang.bean.CashRecord
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class CashModel {
    fun cashList( pageIndex :Int , pageSize:Int ): Observable<ApiResult<ArrayList<CashRecord>>> {
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.applyList(pageIndex , pageSize)
    }
    fun cashIndex():Observable<ApiResult<CashIndex>>{
        var apiService = RetrofitManager.getApiService()
        return apiService!!.applyIndex()
    }

    fun submitApply(accountId:Long , applyMoney:Long ):Observable<ApiResult<Any>>{
        var apiService = RetrofitManager.getApiService()
        return apiService!!.submitApply(accountId , applyMoney )
    }

    fun judgePassword(password:String):Observable<ApiResult<Any>>{
        var apiService = RetrofitManager.getApiService()
        return apiService!!.judgePassword( password )
    }
}