package com.huotu.android.mifang.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.utils.AppUtil


class PushHandlerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pushProcess()
    }

     private fun pushProcess() {
        if (null == intent || !intent.hasExtra(Constants.INTENT_PUSH_KEY)) {
            finish()
            return
        }

        val bundle = intent.getBundleExtra(Constants.INTENT_PUSH_KEY)
        if (null == bundle) {
            finish()
            return
        }


        //通过配置文件获取登录界面的类名，进行相应的登录操作
        //        String loginActivityClassName = this.getString(R.string.login_activity_classname);
        //        if( TextUtils.isEmpty(loginActivityClassName)) {
        //            loginActivityClassName = PhoneLoginActivity.class.getName();
        //        }

        val splashClassName = SplashActivity::class.java!!.name


        val splashActivityIsLoaded = AppUtil.isAppLoaded(this, splashClassName)

        if (splashActivityIsLoaded) {
            val intent = Intent()
            intent.setClassName(this.packageName, splashClassName)
            intent.putExtra(Constants.INTENT_PUSH_KEY, bundle)
            this.startActivity(intent)
            this.finish()
            return
        }

        val fragMainActivityIsLoaded = AppUtil.isAppLoaded(this, MainActivity::class.java.name)
        if (fragMainActivityIsLoaded) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.INTENT_PUSH_KEY, bundle)
            this.startActivity(intent)
            this.finish()
            return
        }

        val intent = Intent(this, SplashActivity::class.java)
        intent.putExtra(Constants.INTENT_PUSH_KEY, bundle)
        this.startActivity(intent)
        this.finish()
        return
    }
}
