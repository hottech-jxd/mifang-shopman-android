package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.MiBean
import com.huotu.android.mifang.bean.PayLoanBean
import com.huotu.android.mifang.bean.ScoreBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface PayLoanContract {
    interface Presenter: IPresenter {
        fun getDepositList(  pageIndex:Int , pageSize:Int)

    }

    interface View: IView<Presenter> {
        fun getDepositListCallback(apiResult: ApiResult<ArrayList<PayLoanBean>>)

    }
}