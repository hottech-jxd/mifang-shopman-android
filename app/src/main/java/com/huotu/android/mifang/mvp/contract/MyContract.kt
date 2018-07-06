package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface MyContract {
    interface Presenter: IPresenter {
        fun myIndex()
        fun mySetting()
        fun getQrcode()
    }

    interface View: IView<Presenter> {
        fun myIndexCallback(apiResult: ApiResult<MyBean>)
        fun mySettingCallback(apiResult: ApiResult<SettingBean>)
        fun getQrcodeCallback(apiResult: ApiResult<String>)
    }
}