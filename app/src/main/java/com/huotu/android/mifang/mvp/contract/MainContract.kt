package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView


interface MainContract {
    interface Presenter: IPresenter {
        //fun loginByVerifyCode(phone :String , code :String)

    }

    interface View: IView<Presenter> {
        //fun loginByVerifyCodeCallback(apiResult: ApiResult<UserBean>)

    }
}