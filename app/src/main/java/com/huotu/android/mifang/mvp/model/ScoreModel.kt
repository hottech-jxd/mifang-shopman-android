package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.CashRecord
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.ScoreBean
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class ScoreModel {
    fun getIntegralList(searchType:Int , pageIndex :Int , pageSize:Int ): Observable<ApiResult<ScoreBean>> {
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.getIntegralList( searchType , pageIndex , pageSize)
    }
}