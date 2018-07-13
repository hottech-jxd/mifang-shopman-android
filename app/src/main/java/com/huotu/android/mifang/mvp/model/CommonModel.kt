package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.AppVersionBean
import com.huotu.android.mifang.bean.PaymentItem
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class CommonModel {

    fun checkAppVersion( appType:String,version:String): Observable<ApiResult<AppVersionBean>> {
        val apiService= RetrofitManager.getApiService()
        return apiService!!.checkAppVersion(appType , version )
    }
    fun getPaymentItems():Observable<ApiResult<ArrayList<PaymentItem>>>{
        val apiService = RetrofitManager.getApiService()
        return apiService!!.getPaymentItem()
    }
    fun feedback(subType:Int, memo:String,mobile:String):Observable<ApiResult<Any>>{
        val apiService = RetrofitManager.getApiService()
        return apiService!!.feedback(subType, memo , mobile)
    }

}