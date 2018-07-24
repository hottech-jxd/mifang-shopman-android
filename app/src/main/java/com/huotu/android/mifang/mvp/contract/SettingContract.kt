package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface SettingContract {
    interface Presenter: IPresenter {
        fun shopperSetting( type :Int , content:String)
        fun uploadLogo(byteArray: ByteArray)
        fun getStoreInfo()
    }

    interface View: IView<Presenter> {
        fun shopperSettingCallback(apiResult: ApiResult<Any>)
        fun uploadLogoCallback( apiResult: ApiResult<UploadImageBean>)
        fun getStoreInfo(apiResult: ApiResult<ShopperInfo>)
    }
}