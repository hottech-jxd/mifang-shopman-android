package com.huotu.android.mifang

import android.app.Application
import com.huotu.android.mifang.util.CrashHandler
import com.liulishuo.filedownloader.FileDownloader
import com.squareup.leakcanary.LeakCanary
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory


object AppInit {

    var iwxApi:IWXAPI?=null

    fun init(context :Application){

        CrashHandler.instance.init(context)

        //Fresco.initialize( context)

        if(!LeakCanary.isInAnalyzerProcess(context)){
            LeakCanary.install(context)
        }

        //初始化下载组件库
        FileDownloader.setupOnApplicationOnCreate(context)

        //注册微信授权登录
        iwxApi = WXAPIFactory.createWXAPI( context , BuildConfig.wechat_app_id , true )
        var isRegister = iwxApi!!.registerApp(BuildConfig.wechat_app_id)

    }


}