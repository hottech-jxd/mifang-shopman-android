package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable
import okhttp3.RequestBody

class SettingModel {
    fun shopperSeting( type:Int , content:String): Observable<ApiResult<Any>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.shopperSetting(type,content )
    }
    fun uploadLogo( map:Map<String,RequestBody >): Observable<ApiResult<Map<String,String>>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.uploadImage( map )
    }
    fun getStoreInfo(): Observable<ApiResult<ShopperInfo>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.getStoreInfo()
    }
}