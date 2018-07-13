package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.MessageBean
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class MessageModel {
    fun getMessageList( type :Int , pageIndex:Int,pageSize:Int): Observable<ApiResult<ArrayList<MessageBean>>>? {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.getJPushList(type, pageIndex, pageSize)
    }
}