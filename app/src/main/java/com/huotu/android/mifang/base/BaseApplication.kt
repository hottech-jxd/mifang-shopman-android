package com.huotu.android.mifang.base

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.huotu.android.mifang.AppInit
import com.huotu.android.mifang.InitService
import com.huotu.android.mifang.bean.Variable

class BaseApplication : MultiDexApplication() {

    var variable : Variable = Variable()

    override fun onCreate() {
        super.onCreate()
        instance = this

        //AppInit.init(this)



    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)

        //把初始化工作放到单独的一个线程中处理。
        InitService.start(this)
    }

    companion object {
        var instance :BaseApplication?=null
    }
}