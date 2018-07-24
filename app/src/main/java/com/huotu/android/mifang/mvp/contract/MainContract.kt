package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.AdBean
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView


interface MainContract {
    interface Presenter: IPresenter {
        fun getAd()

    }

    interface View: IView<Presenter> {
        fun getAdCodeCallback(apiResult: ApiResult<AdBean>)

    }


}