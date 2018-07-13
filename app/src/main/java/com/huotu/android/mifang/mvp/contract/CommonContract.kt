package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.AppVersionBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface CommonContract {

    interface Presenter: IPresenter {
        fun checkAppVersion(appType:String,version:String)
        fun feedback( submitType:Int , memo:String, mobile:String)
    }

    interface View: IView<Presenter> {
        fun checkAppVersionCallback(apiResult: ApiResult<AppVersionBean>)
        fun feedbackCallback(apiResult: ApiResult<Any>)
    }

}