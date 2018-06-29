package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface PayAccountContract {
    interface Presenter: IPresenter {
        fun getAccountList()
        fun setDefaultAccount(accountId:Long)
        fun editPayAccount(payAccount: PayAccount)
        fun deleteAccount(accountId:Long)
    }

    interface View: IView<Presenter> {
        fun getAccountListCallback(apiResult: ApiResult<ArrayList<PayAccount>>)
        fun setDefaultAccountCallback(apiResult: ApiResult<Any>)
        fun editPayAccountCallback(apiResult: ApiResult<Any>)
        fun deleteAccountCallback(apiResult: ApiResult<Any>)
    }
}