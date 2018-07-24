package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView


interface SplashContract {
    interface Presenter: IPresenter {

        fun initData()

        /**
         * 读取省市区数据
         */
        //fun readCityData()
        fun sendWechatLogin()

        fun getWechatAccessToken(appid: String,appSecret:String , code :String)

        fun getWechatUserInfo( openid:String,accessToken:String)

        fun loginByUnionId(openid: String, unionId:String,nickName:String,userHead:String)
    }
    interface View: IView<Presenter> {

         fun initDataCallback(result: ApiResult<InitDataBean>)

        //fun sendWechatLoginCallbak()

         //fun readCityDataCallback(list: ArrayList<Province>)
        fun getWechatAccessTokenCallback(result: WechatAccessToken)

        fun getWechatUserInfo(result: WechatUser)

        fun loginByUnionIdCallback(apiResult: ApiResult<UserBean>)

    }
}