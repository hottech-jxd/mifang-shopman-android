package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.CashIndex
import com.huotu.android.mifang.bean.CashRecord
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface CashContract {
    interface Presenter: IPresenter {
        fun cashList( pageIndex : Int  )
        fun cashIndex()
        fun submitApply(accountId:Long, applyMoney:Long)
        fun judgePassword(password:String)
    }

    interface View: IView<Presenter> {
        fun cashListCallback(apiResult: ApiResult<ArrayList<CashRecord>>)
        fun cashIndexCallback(apiResult: ApiResult<CashIndex>)
        fun submitApplyCallback(apiResult: ApiResult<Any>)
        fun judgePasswordCallback(apiResult: ApiResult<Any>)
    }
}