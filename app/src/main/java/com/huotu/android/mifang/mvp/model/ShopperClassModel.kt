package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class ShopperClassModel {
    fun getShopperClassCategorys(): Observable<ApiResult<ArrayList<ShopperClassCategoryBean>>> {
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.getShopClassCategorys()
    }
    fun getShopperClassList(typeId:Int, pageIndex:Int):Observable<ApiResult<ArrayList<ShopperClassBean>>>{
        val apiService = RetrofitManager.getApiService()
        return  apiService!!.getShopClassList(typeId , pageIndex)
    }
}