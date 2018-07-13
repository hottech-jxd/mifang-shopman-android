package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface ShopperClassContract {
    interface Presenter: IPresenter {
        fun getCategorys()
        fun getList(typeId:Int , pageIndex:Int)

    }

    interface View: IView<Presenter> {
        fun getCategorysCallback(apiResult: ApiResult<ArrayList<ShopperClassCategoryBean>>)
        fun getListCallback(apiResult: ApiResult<ArrayList<ShopperClassBean>>)
    }
}