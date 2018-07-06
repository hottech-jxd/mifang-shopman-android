package com.huotu.android.mifang.mvp.model

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.UserBean
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class LoginModel{

//    fun register( mobile:String , inviteCode:String , verifyCode :String ): Observable<ApiResult<UserBean>>{
//        val apiService = RetrofitManager.getApiService()
//        return apiService!!.register(mobile, inviteCode, verifyCode)
//    }

//    fun login(phone:String , code :String ) : Observable<ApiResult<UserBean>>{
//        val apiService = RetrofitManager.getApiService()
//        return apiService!!.login(phone, code )
//    }

    fun sendVerifyCode(phone:String):Observable<ApiResult<Any>>{
        val apiService = RetrofitManager.getApiService()
        return apiService!!.sendVerifyCode(phone )
    }
}
