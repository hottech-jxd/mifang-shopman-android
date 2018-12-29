package com.huotu.android.mifang.util

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import com.huotu.android.mifang.activity.LoginRegisterActivity
import com.huotu.android.mifang.base.BaseApplication
import com.huotu.android.mifang.bean.Constants
import java.util.HashMap

/**
 * Created by jinxiangdong on 2018/3/28.
 */

object CookieUtils {

    fun setWebViewCookie() {
        try {
            val youxinUserid =  BaseApplication.instance!!.variable.userBean!!.UserId
            val youxinUserToken = BaseApplication.instance!!.variable.userBean!!.Token

            val youxinDomain = Uri.parse( Constants.YOUXIN_BASE_URL ).host //"http://youxin.51huotao.com";

            val youxinCookie = "User-Id=$youxinUserid;User-Token=$youxinUserToken;User-Info=;domain=$youxinDomain;path=/"


            val userInfo = UserInfo()
            userInfo.userId = youxinUserid
            userInfo.merchantId = java.lang.Long.valueOf(Constants.MerchantId)
            userInfo.userName = BaseApplication.instance!!.variable.userBean!!.LoginName
            userInfo.userToken = BaseApplication.instance!!.variable.userBean!!.Token
            userInfo.userType = 0
            userInfo.headimg = null
            userInfo.isAuthIdCard = false
            userInfo.realName = null
            userInfo.isHavePcPower = false
            userInfo.homeAddress = null
            userInfo.userCardNo = null
            userInfo.qq = null
            userInfo.education = 1
            userInfo.isMarry = false
            userInfo.realEstate = 3
            userInfo.realEstateType = 10
            val userInfoStr = GsonUtils.gson!!.toJson(userInfo)

            val cookies = HashMap<String, String>()
            cookies["User-Id"] = youxinUserid.toString()
            cookies["User-Token"] = youxinUserToken
            cookies["User-Info"] = userInfoStr
            cookies["showmenu"] = "0"

            setWebViewCookie(BaseApplication.instance!!, youxinDomain, cookies)
        } catch (ex: Exception) {
            Log.e(LoginRegisterActivity::class.java!!.getName(), ex.message)
        }

    }


    private fun setWebViewCookie(context: Context, url: String, cookies: Map<String, String>) {
        CookieSyncManager.createInstance(context)
        val cookieManager = CookieManager.getInstance()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null)
            cookieManager.flush()
        } else {
            cookieManager.removeAllCookie()
        }
        cookieManager.setAcceptCookie(true)

        for (key in cookies.keys) {
            val cookieStr = key + "=" + cookies[key] + ";"
            cookieManager.setCookie(url, cookieStr)
        }

        CookieSyncManager.getInstance().sync()
    }

    fun clearWebViewCookie() {
        CookieSyncManager.createInstance(BaseApplication.instance )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()
        } else {
            CookieManager.getInstance().removeAllCookie()
        }
        CookieSyncManager.getInstance().sync()
    }


    internal class UserInfo {
        var userId: Long = 0
        var merchantId: Long = 0
        var userName: String?=""
        var userToken: String?=""
        var headimg: String? = null
        var userType: Int = 0
        var isAuthIdCard: Boolean = false
        var realName: String? = null
        var isHavePcPower: Boolean = false
        var homeAddress: String? = null
        var userCardNo: String? = null
        var qq: String? = null
        var education: Int = 0
        var isMarry: Boolean = false
        var realEstate: Int = 0
        var realEstateType: Int = 0
    }
}
