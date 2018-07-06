package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.MessageBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface MessageContract {
    interface Presenter: IPresenter {
        fun getMessageList(pageIndex:Int , pageSize:Int)

    }

    interface View: IView<Presenter> {
        fun getMessageList(apiResult: ApiResult<ArrayList<MessageBean>>)
    }
}