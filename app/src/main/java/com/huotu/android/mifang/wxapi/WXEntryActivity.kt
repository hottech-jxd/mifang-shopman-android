package com.huotu.android.mifang.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.huotu.android.mifang.AppInit
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.util.ToastUtils
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler


/*
 *
 * 微信客户端回调activity示例
 */
class WXEntryActivity : Activity(), IWXAPIEventHandler {
    internal val TAG = this@WXEntryActivity.javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.layout_wechat)

        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，
        // 如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，
        // 避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            val isDeal = AppInit.iwxApi!!.handleIntent(intent, this)
            if (!isDeal) {
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        AppInit.iwxApi!!.handleIntent(intent, this)
    }

    override fun onReq(baseReq: BaseReq) {

    }

    override fun onResp(baseResp: BaseResp) {
        Log.d(TAG, "baseResp.type =" + baseResp.type)

        when(baseResp.type){
            ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM->{//启动小程序，回调
                var launchMinProResp =   baseResp as WXLaunchMiniProgram.Resp
                var extraData = launchMinProResp.extMsg//
            }
            ConstantsAPI.COMMAND_SENDAUTH->{//微信授权登录,回调
                authLogin(baseResp)
            }

        }

//        when (baseResp.errCode) {
//            BaseResp.ErrCode.ERR_OK -> {
//                ToastUtils.single.showLongToast(this, "操作成功" + baseResp.errStr)
//                var intent = Intent()
//                intent.setAction(Constants.ACTION_WECHAT_LOGIN)
//                intent.putExtra("code", (baseResp as SendAuth.Resp).code )
//                sendBroadcast( intent)
//            }
//            BaseResp.ErrCode.ERR_AUTH_DENIED -> ToastUtils.single.showLongToast(this, "授权失败" + baseResp.errStr)
//            BaseResp.ErrCode.ERR_UNSUPPORT -> ToastUtils.single.showLongToast(this, "操作不支持" + baseResp.errStr)
//            BaseResp.ErrCode.ERR_USER_CANCEL -> ToastUtils.single.showLongToast(this, "用户取消操作" + baseResp.errStr)
//            else -> ToastUtils.single.showLongToast(this, "操作异常=" + baseResp.errCode + baseResp.errStr)
//        }

        finish()
    }

    private fun authLogin( baseResp : BaseResp){

        when(baseResp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                ToastUtils.single.showLongToast(this, "授权成功" )
                var intent = Intent()
                intent.action =Constants.ACTION_WECHAT_LOGIN
                intent.putExtra("code", (baseResp as SendAuth.Resp).code)
                sendBroadcast(intent)
            }
            BaseResp.ErrCode.ERR_AUTH_DENIED -> {
                ToastUtils.single.showLongToast(this, "授权失败" + baseResp.errStr)
            }
            BaseResp.ErrCode.ERR_UNSUPPORT ->{
                ToastUtils.single.showLongToast(this, "操作不支持" + baseResp.errStr)
            }
            BaseResp.ErrCode.ERR_USER_CANCEL ->{
                ToastUtils.single.showLongToast(this, "用户取消操作" + baseResp.errStr)
            }
            else ->{
                ToastUtils.single.showLongToast(this, "操作异常=" + baseResp.errCode + baseResp.errStr)
            }
        }
    }

    private fun getAccessToken( code :String  ){

    }


}
