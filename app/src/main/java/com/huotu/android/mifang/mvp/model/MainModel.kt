package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.AdBean
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class MainModel {
    fun getAd(): Observable<ApiResult<AdBean>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.popAd()
    }
}