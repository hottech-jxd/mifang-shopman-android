package com.huotu.android.mifang.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import com.huotu.android.mifang.base.BaseApplication
import kotlinx.android.synthetic.main.activity_web.*

class WebView :WebView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        this.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        this.isVerticalScrollBarEnabled = false
        this.isHorizontalScrollBarEnabled = false
        this.isClickable = true
        this.settings.useWideViewPort = true
        //是否需要避免页面放大缩小操作
        //viewPage.getSettings().setSupportZoom(true);
        //viewPage.getSettings().setBuiltInZoomControls(true);
        this.settings.javaScriptEnabled = true
        this.settings.cacheMode = WebSettings.LOAD_DEFAULT
        //webView.getSettings().setSaveFormData(true)
        this.settings.allowFileAccess = true
        this.settings.loadWithOverviewMode = false
        //viewPage.getSettings().setSavePassword(true);
        this.settings.loadsImagesAutomatically = true
        this.settings.domStorageEnabled = true
        this.settings.setAppCacheEnabled(true)
        this.settings.databaseEnabled = true
        BaseApplication.instance!!.getDir("database", Context.MODE_PRIVATE).path
        //webView.getSettings().setGeolocationDatabasePath(dir);
        this.settings.setGeolocationEnabled(true)
        //webView.addJavascriptInterface(this, "android");
        val appCacheDir = BaseApplication.instance!!.getDir("cache", Context.MODE_PRIVATE).path
        this.settings.setAppCachePath(appCacheDir)

        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }

    }
}