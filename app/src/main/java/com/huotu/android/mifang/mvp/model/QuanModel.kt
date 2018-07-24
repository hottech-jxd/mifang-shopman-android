package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.MaterialCategory
import com.huotu.android.mifang.bean.Quan
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class QuanModel {
    fun materialCategprys(): Observable<ApiResult<List<MaterialCategory>>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.materialCategprys()
    }
    fun materialList(typeId:Int, pageIndex:Int=1):Observable<ApiResult<List<Quan>>>{
        val apiService = RetrofitManager.getApiService()
        return apiService!!.materialList(typeId , pageIndex )
    }
    fun shareSuccess(dataId:Long):Observable<ApiResult<Any>>{
        val apiService = RetrofitManager.getApiService()
        return apiService!!.shareSuccess(dataId )
    }
}