package com.huotu.android.mifang.http

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.UserBean
import com.huotu.android.mifang.bean.WechatAccessToken
import com.huotu.android.mifang.bean.WechatUser
import io.reactivex.Observable
import retrofit2.http.*

interface WechatService {

    /**
     * 通过code获取access_token的接口。
     */
    @GET("/sns/oauth2/access_token")
    fun getAccessToken(@Query("appid") appid: String
                       , @Query("secret") secret:String
                       , @Query("code") code: String
                       , @Query("grant_type") grant_type:String="authorization_code"): Observable<WechatAccessToken>


    /**
     * 刷新或续期access_token使用
     */
    @GET("/sns/oauth2/refresh_token")
    fun getRefreshToken(@Query("appid") appid: String,
                        @Query("refresh_token")refresh_token:String,
                        @Query("grant_type") grant_type:String="refresh_token" ):Observable<WechatAccessToken>

    /**
     * 获取用户个人信息（UnionID机制）
     */
    @GET("/sns/userinfo")
    fun getUserInfo(@Query("access_token")access_token:String,
                    @Query("openid") openid:String):Observable<WechatUser>
}