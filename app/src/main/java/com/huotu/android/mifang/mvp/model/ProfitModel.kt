package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class ProfitModel {
    fun getProfitIndex(): Observable<ApiResult<ProfitIndexBean>> {
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.getProfitIndex()
    }
    fun getProfitItems( countType:Int, searchYear:Int,searchMonth:Int): Observable<ApiResult<ArrayList<ProfitItemBean>>> {
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.getProfitItems(countType , searchYear , searchMonth)
    }
}