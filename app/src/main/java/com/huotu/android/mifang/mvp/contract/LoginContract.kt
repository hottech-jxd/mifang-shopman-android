package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.UserBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView


interface LoginContract {
    interface Presenter: IPresenter {
        fun register(mobile:String, inviteCode:String , smsCode : String)

        fun login(phone :String , code :String)

        fun sendVerifyCode(phone:String)
    }

    interface View: IView<Presenter> {
        fun registerCallback(apiResult: ApiResult<UserBean>)

        fun loginCallback(apiResult: ApiResult<UserBean>)

        fun sendVerifyCodeCallback(apiResult: ApiResult<Any>)
    }
}