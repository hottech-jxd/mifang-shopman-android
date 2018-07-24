package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class GoodsModel {
    fun getGoodsInfo( goodsId:Long): Observable<ApiResult<GoodsDetailBean>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.getGoodsInfo(goodsId )
    }
    fun getStoreIndex( pageIndex:Int, pageSize:Int): Observable<ApiResult<StoreIndex>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.getStoreIndex(pageIndex, pageSize )
    }
    fun getShopperAccountInfo( ): Observable<ApiResult<ShopperAccountInfo>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.getShopperAccountInfo()
    }
    fun agentUpgrade( goodsId:Long): Observable<ApiResult<GoodsDetailBean>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.agentUpgrade(goodsId )
    }


}