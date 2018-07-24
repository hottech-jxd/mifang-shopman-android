package com.huotu.android.mifang.base

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.huotu.android.mifang.util.StatusBarUtils
import com.huotu.android.mifang.R
import com.huotu.android.mifang.activity.LoginRegisterActivity
import com.huotu.android.mifang.activity.SplashActivity
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.ApiResultCodeEnum
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.mvp.IView
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.showToast
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.umeng.analytics.MobclickAgent


open class BaseActivity<T> : RxAppCompatActivity() , IView<T> {

    var isWhite:Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setStatusBar()
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    open fun setStatusBar(){
        //禁止横屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if(isWhite){
            setStatusBarTextBlackColor()
        }else {
            StatusBarUtils.setColor(this, resources.getColor( R.color.my_statusbar_color ) )
        }
    }


    private fun setStatusBarTextBlackColor():Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var newUiVisibility = this.window.decorView.systemUiVisibility
            newUiVisibility = newUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            newUiVisibility = newUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            this.window.decorView.systemUiVisibility = newUiVisibility
            window.statusBarColor = Color.WHITE
            return true
        }
        return false
    }


    override fun showProgress(msg: String) {

    }

    override fun hideProgress() {

    }

    override fun toast(msg: String) {
        showToast(msg)
    }

    override fun error(err: String) {
        toast(err)
    }


    /**
     * 处理服务端返回的公共错误代码
     * @param apiResult
     * @return
     */
    protected fun processCommonErrorCode( apiResult: ApiResult<*> ): Boolean {
        if (apiResult.code == ApiResultCodeEnum.USER_NO_LOGIN.code ||
            apiResult.code == ApiResultCodeEnum.USER_FREEZE.code ||
            apiResult.code == ApiResultCodeEnum.USER_NO_LOGIN.code  ) {

            toast(Constants.MESSAGE_TOKEN_LOST)

            //EventBus.getDefault().post(LogoutEvent())

            newIntent<SplashActivity>()
            return true
        }
        return false
    }

    protected fun isLogin(): Boolean {
        return return !(BaseApplication.instance!!.variable.userBean == null
                        || BaseApplication.instance!!.variable.userBean!!.token == ""
                        || BaseApplication.instance!!.variable.userBean!!.userId == 0L)
    }

}
