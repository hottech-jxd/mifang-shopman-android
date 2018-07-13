package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class ScoreModel {
    fun getIntegralList(searchType:Int , pageIndex :Int , pageSize:Int ): Observable<ApiResult<ScoreBean>> {
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.getIntegralList( searchType , pageIndex , pageSize)
    }
    fun getMiBeanList(pageIndex:Int , pageSize:Int):Observable<ApiResult<MiBean>>{
        val apiService = RetrofitManager.getApiService()
        return apiService!!.getMiBeanList(pageIndex, pageSize)
    }
}