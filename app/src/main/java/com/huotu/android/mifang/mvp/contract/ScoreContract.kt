package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.ScoreBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface ScoreContract {
    interface Presenter: IPresenter {
        fun getIntegralList( searchType:Int , pageIndex:Int , pageSize:Int)

    }

    interface View: IView<Presenter> {
        fun getIntegralListCallback(apiResult: ApiResult<ScoreBean>)

    }
}