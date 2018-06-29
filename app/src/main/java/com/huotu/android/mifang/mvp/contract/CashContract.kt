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
    }

    interface View: IView<Presenter> {
        fun cashListCallback(apiResult: ApiResult<ArrayList<CashRecord>>)
        fun cashIndexCallback(apiResult: ApiResult<CashIndex>)
    }
}