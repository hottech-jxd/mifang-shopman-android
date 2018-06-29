package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.MyWalletBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface BindPhoneContract {
    interface Presenter: IPresenter {
        fun bindPhone(phone:String, code:String)
        //fun payPassword()
        fun sendCode(phone:String)
        fun updatePayPassword(payPassword:String)
    }

    interface View: IView<Presenter> {
        fun bindPhoneCallback(apiResult: ApiResult<Any>)
        //fun payPasswordCallback(apiResult: ApiResult<Any>)
        fun sendCodeCallback(apiResult: ApiResult<Any>)
        fun updatePayPasswordCallback(apiResult: ApiResult<Any>)
    }
}