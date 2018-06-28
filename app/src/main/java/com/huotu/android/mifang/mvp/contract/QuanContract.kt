package com.huotu.android.mifang.mvp.contract

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.MaterialCategory
import com.huotu.android.mifang.bean.Quan
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.IView

interface QuanContract {

    interface Presenter: IPresenter {
        fun materialCategprys()
        fun materialList(typeId:Int , pageIndex:Int=1)
    }

    interface View: IView<Presenter> {
        fun materialCategprysCallback(apiResult: ApiResult<List<MaterialCategory>>)

        fun materialListCallback(apiResult: ApiResult<List<Quan>>)
    }

}