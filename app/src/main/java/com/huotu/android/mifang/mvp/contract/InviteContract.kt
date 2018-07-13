package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface InviteContract {
    interface Presenter: IPresenter {
        fun invite()

    }

    interface View: IView<Presenter> {
        fun inviteCallback(apiResult: ApiResult<InviteBean>)

    }
}