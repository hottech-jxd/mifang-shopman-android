package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.MyBean
import com.huotu.android.mifang.bean.MyWalletBean
import com.huotu.android.mifang.bean.SettingBean
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class WalletModel {
    fun myWallet(): Observable<ApiResult<MyWalletBean>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.myWallet()
    }

}