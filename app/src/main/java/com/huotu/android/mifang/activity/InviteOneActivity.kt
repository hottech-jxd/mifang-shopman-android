package com.huotu.android.mifang.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.provider.SyncStateContract
import android.security.keystore.KeyNotYetValidException
import android.support.v4.content.ContextCompat
import android.view.View
import android.webkit.*
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.KeyValue
import com.huotu.android.mifang.bean.ShareBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.util.WechatShareUtil
import com.huotu.android.mifang.utils.UrlInterceptor
import com.huotu.android.mifang.widget.ShareDialog
import com.huotu.android.mifang.widget.TipAlertDialog
import kotlinx.android.synthetic.main.activity_invite_one.*
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.layout_header.*

class InviteOneActivity : BaseActivity<IPresenter>()
        ,ShareDialog.OnOperateListener
        ,View.OnClickListener {
    val urlIntercepter : UrlInterceptor = UrlInterceptor(this)
    var url:String?=null
    var type :Int=1
    var wechatShareUtil=WechatShareUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_one)

        header_title.text="邀请入驻"
        header_left_image.setOnClickListener(this)
        invite_one_buy.setOnClickListener(this)

        initWebView()

        if(intent.hasExtra(Constants.INTENT_URL)){
            url = intent.getStringExtra(Constants.INTENT_URL)
            invite_one_webView.loadUrl(url)
        }
        if(intent.hasExtra(Constants.INTENT_OPERATE_TYPE)){
            type = intent.getIntExtra(Constants.INTENT_OPERATE_TYPE,1)
        }

        if(type == 1){
            invite_one_buy.text="购买开店账号"
            invite_one_buy.setBackgroundColor( ContextCompat.getColor(this , R.color.bgcolor))
        }else if(type ==2){
            invite_one_buy.text = "申请代理商"
            invite_one_buy.setBackgroundColor( ContextCompat.getColor( this , R.color.bgcolor2))
        }else if(type ==3){
            invite_one_buy.text="转发邀请函"
            invite_one_buy.setBackgroundColor(ContextCompat.getColor(this,R.color.bgcolor2))
        }
    }

    private fun initWebView(){
        invite_one_webView.webViewClient =
                object : WebViewClient() {

                    override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                        if (url != null && url.startsWith("tel:")) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intent)
                            return true
                        }

                        return if (urlIntercepter.shouldOverrideUrlLoading(view, url)) {
                            true
                        } else {
                            super.shouldOverrideUrlLoading(view, url)
                        }
                    }


                    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                        if (view.url != null && view.url.startsWith("tel:")) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(view.url))
                            startActivity(intent)
                            return true
                        }

                        return if (urlIntercepter.shouldOverrideUrlLoading(view, url)) {
                            true
                        } else {
                            super.shouldOverrideUrlLoading(view, request)
                        }

                        //return super.shouldOverrideUrlLoading(view, request)
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                        super.doUpdateVisitedHistory(view, url, isReload)

                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        if (header_title == null) return
                        header_title.text =view?.title
                    }


                    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                        super.onReceivedError(view, request, error)
                        if (web_progressBar == null) return
                        web_progressBar.visibility = View.GONE
                    }


                    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                        super.onReceivedSslError(view, handler, error)
                        //当访问https网页，发生错误时，继续浏览网页
                        handler?.proceed()
                    }

                }


        invite_one_webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String?) {
                super.onReceivedTitle(view, title)
                if (header_title == null) {
                    return
                }
                if (title == null) {
                    return
                }
                header_title.text =title
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if ( web_progressBar == null) return
                if (100 == newProgress) {
                    web_progressBar.visibility =View.GONE
                } else {
                    if (web_progressBar.visibility == View.GONE) web_progressBar.visibility = View.VISIBLE
                    web_progressBar.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
//                mUploadMessage = uploadMsg
//                val i = Intent(Intent.ACTION_GET_CONTENT)
//                i.addCategory(Intent.CATEGORY_OPENABLE)
//                i.type = "*/*"
//                this@WebActivity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String) {
                openFileChooser(uploadMsg)
            }

            //For Android 4.1
            fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String, capture: String) {
                openFileChooser(uploadMsg)
            }

            override fun onShowFileChooser(webView: WebView, filePathCallback: ValueCallback<Array<Uri>>, fileChooserParams: WebChromeClient.FileChooserParams): Boolean {
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }

            override fun onGeolocationPermissionsShowPrompt(origin: String, callback: GeolocationPermissions.Callback) {
                callback.invoke(origin, true, false)
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }

            override fun onJsConfirm(view: WebView?, url: String, message: String, result: JsResult): Boolean {
                //return super.onJsConfirm(view, url, message, result);
                if (view == null || view.context == null) return true

                val tipAlertDialog = TipAlertDialog(view.context, false)
                tipAlertDialog.show("询问", message, View.OnClickListener {
                    tipAlertDialog.dismiss()
                    result.cancel()
                }, View.OnClickListener {
                    tipAlertDialog.dismiss()
                    result.confirm()
                })

                return true
            }
        }

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
            R.id.invite_one_buy->{
                operate()
            }
        }
    }

    fun operate(){
        if(type==1){
            newIntent<BuyActivity>(Constants.INTENT_OPERATE_TYPE , 0)
        }else if(type==2){
            //newIntent<AgentUpgradeActivity>(Constants.INTENT_GOODSID, -1)//todo
            newIntent<ApplyAgentActivity>()
        }else if(type==3){
            share()
        }
    }

    fun share(){

        var kv=ArrayList<KeyValue>()
        kv.add(KeyValue(R.mipmap.ssdk_oks_classic_wechat,"微信好友"))
        kv.add(KeyValue(R.mipmap.ssdk_oks_classic_wechatmoments,"微信朋友圈"))
        kv.add(KeyValue(R.mipmap.ssdk_oks_classic_wechatfavorite,"微信收藏夹"))
        var shareDialog=ShareDialog(this, this, kv )
        shareDialog.show()
    }

    override fun operate(keyValue: KeyValue) {
        var bean = ShareBean("title","desciption",
                "http://www.baidu.com",
                "https://images0.cnblogs.com/news_topic/apple.png",
                "",null,-1)
        if(keyValue.code == R.mipmap.ssdk_oks_classic_wechat){

            wechatShareUtil.shareWechat(bean)
        }else if(keyValue.code==R.mipmap.ssdk_oks_classic_wechatmoments){
            wechatShareUtil.shareWechatMoments(bean)
        }else if(keyValue.code==R.mipmap.ssdk_oks_classic_wechatfavorite){
            wechatShareUtil.shareWechatFavorite(bean)
        }
    }
}
