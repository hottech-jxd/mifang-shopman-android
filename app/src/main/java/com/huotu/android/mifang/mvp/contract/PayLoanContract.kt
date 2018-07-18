package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface PayLoanContract {
    interface Presenter: IPresenter {
        fun getDepositList(  pageIndex:Int , pageSize:Int)
        fun getFrozenFlow( pageIndex: Int, pageSize: Int)
    }

    interface View: IView<Presenter> {
        fun getDepositListCallback(apiResult: ApiResult<ArrayList<PayLoanBean>>)
        fun getFrozenFlowCallback(apiResult: ApiResult<ArrayList<FrozenFlow>>)
    }
}