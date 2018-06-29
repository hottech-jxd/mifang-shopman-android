package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.ApiResult

import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class BindPhoneModel {
    fun sendCode(mobile:String ): Observable<ApiResult<Any>> {
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.sendCode(mobile)
    }
    fun updateMobile(mobile:String,code:String):Observable<ApiResult<Any>>{
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.updateMobile(mobile,code)
    }

    fun updatePayPassword(payPassword:String):Observable<ApiResult<Any>>{
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.updatePayPassword(payPassword)
    }
}