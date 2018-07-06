package com.huotu.android.mifang

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.github.moduth.blockcanary.BlockCanary
import com.huotu.android.library.libpush.PushHelper
import com.huotu.android.mifang.util.AppBlockCanaryContext
import com.huotu.android.mifang.util.CrashHandler
import com.liulishuo.filedownloader.FileDownloader
import com.squareup.leakcanary.LeakCanary
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure


object AppInit {

    var iwxApi:IWXAPI?=null

    fun init(context :Application){

        CrashHandler.instance.init(context)


        if(!LeakCanary.isInAnalyzerProcess(context)){
            LeakCanary.install(context)
        }


        //UI卡顿监测库初始化，在主进程初始化调用
        BlockCanary.install(context, AppBlockCanaryContext()).start()

        //注意：如果您已经在AndroidManifest.xml中配置过appkey和channel值，可以调用此版本初始化函数。
        UMConfigure.init(context, UMConfigure.DEVICE_TYPE_PHONE, null )
        MobclickAgent.setScenarioType( context, MobclickAgent.EScenarioType.E_UM_NORMAL)
        //设置是否将自动捕获到的程序异常传回服务器。
        MobclickAgent.setCatchUncaughtExceptions(true)

        //fresco 初始化
        Fresco.initialize( context)

        // 极光初始化
        PushHelper.init(context, BuildConfig.DEBUG, "")

        //初始化下载组件库
        FileDownloader.setupOnApplicationOnCreate(context)

        //注册微信授权登录
        iwxApi = WXAPIFactory.createWXAPI( context , BuildConfig.wechat_app_id , true )
        iwxApi!!.registerApp(BuildConfig.wechat_app_id)

    }


}