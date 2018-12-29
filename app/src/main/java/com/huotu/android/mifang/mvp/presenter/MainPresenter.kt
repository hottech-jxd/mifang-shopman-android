package com.huotu.android.mifang.mvp.presenter


import com.huotu.android.mifang.mvp.contract.MainContract
import com.huotu.android.mifang.mvp.model.MainModel

class MainPresenter(view: MainContract.View) :MainContract.Presenter{
        var mView: MainContract.View?=null
        val mModel: MainModel by lazy { MainModel() }

        init {
            mView=view
        }

    override fun onDestory() {

    }
}