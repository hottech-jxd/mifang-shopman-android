package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.ProfitIndexBean
import com.huotu.android.mifang.bean.ProfitItemBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface ProfitContract {
    interface Presenter: IPresenter {
        fun getProfitIndex()
        fun getProfitItems(countType:Int ,searchYear:Int, searchMonth:Int )

    }

    interface View: IView<Presenter> {
        fun getProfitIndexCallback(apiResult: ApiResult<ProfitIndexBean>)
        fun getProfitItemsCallback(apiResult: ApiResult< ArrayList<ProfitItemBean>>)
    }
}