package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.MyBean
import com.huotu.android.mifang.bean.SettingBean
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class MyModel {
    fun myIndex(): Observable<ApiResult<MyBean>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.myIndex()
    }
    fun mySetting():Observable<ApiResult<SettingBean>>{
        val apiService = RetrofitManager.getApiService()
        return apiService!!.setting()
    }
    fun getQrcode():Observable<ApiResult<String>>{
        val apiService=RetrofitManager.getApiService()
        return apiService!!.getQrcode()
    }
    fun updatePayPasswordStatus(status:Int): Observable<ApiResult<Any>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.updatePayPasswordStatus(status)
    }
}