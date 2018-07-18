package com.huotu.android.mifang.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.huotu.android.mifang.AppInit
import com.huotu.android.mifang.R
import com.huotu.android.mifang.util.ToastUtils
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.constants.ConstantsAPI



/**
 * 微信支付回调类
 */
class WXPayEntryActivity : Activity(), IWXAPIEventHandler {

    //private val api: IWXAPI? = null

    override fun onReq(baseReq: BaseReq) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView ( R.layout.activity_wx_pay )
        //        api = WXAPIFactory.createWXAPI ( this, application.readWxpayAppId ( ) );
        //        api.handleIntent ( getIntent ( ), this );

        try {
            val isDeal = AppInit.iwxApi!!.handleIntent(intent, this)
            if (!isDeal) {
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun pay(resp: BaseResp) {
        when (resp.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                var msg = "支付成功"
                ToastUtils.single.showToast(msg)
                finish()
                return
            }
            BaseResp.ErrCode.ERR_COMM -> {
                var msg = "支付失败"
                ToastUtils.single.showToast(msg)
                finish()
                return
            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {
                var msg = "用户取消支付"
                ToastUtils.single.showToast(msg)
                finish()
                return
            }
            else -> {
                var msg="支付失败 错误码="+ resp.errCode +" 错误信息="+resp.errStr
                ToastUtils.single.showToast(msg)
                finish()
                return
            }
        }
    }

    override fun onResp(resp: BaseResp) {
        Log.i("info", "onPayFinish, errCode = " + resp.errCode)

        when(resp.type){
            ConstantsAPI.COMMAND_PAY_BY_WX->{
                pay(resp)
            }
        }


        //        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
        //            String msg = "";
        //            if( resp.errCode== 0)
        //            {
        //                BuyerPayUtil.paySuccessCallback( this , resp);
        //
        //                msg="支付成功";
        //                this.finish();
        //                ToastUtils.showLongToast (  msg );
        //                return;
        //            }else if( resp.errCode== -1){
        //                BuyerPayUtil.wxPayErrorCallback(resp);
        //
        //                msg="支付失败";
        //                ToastUtils.showLongToast (  msg );
        //                this.finish();
        //                return;
        //            }else if(resp.errCode ==-2){
        //                BuyerPayUtil.wxPayCancelCallback(resp);
        //
        //                msg="用户取消支付";
        //                ToastUtils.showLongToast( msg);
        //                this.finish();
        //                return;
        //            }
        //
        //            PayResp payResp = (PayResp)resp;
        //            if(null==payResp){
        //                Log.i("wxpay>>>payResp=null","");
        //                msg="支付失败";
        //                ToastUtils.showLongToast( msg);
        //                this.finish();
        //                return;
        //            }else{
        //                Log.i("wxpay>>>extData", payResp.extData==null? "": payResp.extData );
        //                //Log.i("wxpay>>>prepayid",payResp.prepayId);
        //            }
        //
        //        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        //api!!.handleIntent(intent, this)

        AppInit.iwxApi!!.handleIntent(intent, this)
    }
}
