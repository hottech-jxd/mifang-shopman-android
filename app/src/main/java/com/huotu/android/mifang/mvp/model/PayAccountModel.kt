package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class PayAccountModel {
    fun getAccountList(): Observable<ApiResult<ArrayList<PayAccount>>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.getAccountList()
    }
    fun setDefaultAccount(accountId:Long): Observable<ApiResult<Any>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.setDefaultAccount(accountId)
    }
    fun editPayAccount(payAccount: PayAccount):Observable<ApiResult<Any>>{
        val apiService = RetrofitManager.getApiService()
        return apiService!!.editAccount(payAccount.RealName , payAccount.Account , payAccount.AccountType ,  payAccount.AccountId )
    }
    fun deleteAccount(accountId:Long):Observable<ApiResult<Any>>{
        val apiService=RetrofitManager.getApiService()
        return apiService!!.deleteAccount(accountId)
    }
}