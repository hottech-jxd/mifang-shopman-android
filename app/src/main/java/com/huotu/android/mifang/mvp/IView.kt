package com.huotu.android.mifang.mvp

interface IView<T> {
    fun showProgress( msg:String)

    fun hideProgress()

    fun toast(msg : String)

    fun error(err:String)
}