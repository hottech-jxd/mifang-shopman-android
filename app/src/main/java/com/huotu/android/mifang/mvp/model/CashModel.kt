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
}