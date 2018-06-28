package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface WalletContract {
    interface Presenter: IPresenter {
        fun myWallet()

    }

    interface View: IView<Presenter> {
        fun myWalletCallback(apiResult: ApiResult<MyWalletBean>)

    }
}