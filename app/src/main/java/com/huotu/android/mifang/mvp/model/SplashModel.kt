package com.huotu.android.mifang.mvp.model


import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class SplashModel {

    fun initData():Observable<ApiResult<InitDataBean>> {
        val apiService = RetrofitManager.getApiService()
        return apiService!!.init()
    }

    fun getWechatAccessToken( appid :String, appsecret:String , code:String):Observable<WechatAccessToken>{
        val apiService = RetrofitManager.getWechatService()
        return apiService!!.getAccessToken(appid , appsecret , code )
    }

    fun getWechatUser( openid :String, accessToken: String ):Observable<WechatUser>{
        val apiService = RetrofitManager.getWechatService()
        return apiService!!.getUserInfo( accessToken ,openid )
    }

    fun loginByUnionId( openid :String,  unionid :String,  nickname :String,  userhead :String):Observable<ApiResult<UserBean>>{
        val apiService = RetrofitManager.getApiService()
        return apiService!!.loginByUnionId(openid,unionid,nickname,userhead)
    }

}