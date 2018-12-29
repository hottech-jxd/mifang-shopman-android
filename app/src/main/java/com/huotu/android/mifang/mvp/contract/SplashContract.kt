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

        fun getWechatAccessToken(appid: String,appSecret:String , code :String)

        fun getWechatUserInfo( openid:String,accessToken:String)
    }
    interface View: IView<Presenter> {

         fun initDataCallback(result: ApiResult<InitDataBean>)

         //fun readCityDataCallback(list: ArrayList<Province>)
        fun getWechatAccessTokenCallback(result: WechatAccessToken)

        fun getWechatUserInfo(result: WechatUser)

    }
}