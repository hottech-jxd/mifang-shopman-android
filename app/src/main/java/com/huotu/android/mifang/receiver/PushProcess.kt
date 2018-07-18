package com.huotu.android.mifang.receiver

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.huotu.android.mifang.util.GsonUtils
import cn.jpush.android.api.JPushInterface
import com.huotu.android.mifang.activity.WebActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.widget.MsgDialog
import com.huotu.android.mifang.widget.OperateDialog
import com.huotu.android.mifang.widget.SysMessageDialog

/**
 * Created by jinxiangdong on 2017/12/18.
 */
object PushProcess {
    /**
     *
     * @param context
     * @param bundle
     */
    fun process(context: Activity, bundle: Bundle) {

        //final String content = bundle.getString(JPushInterface.EXTRA_ALERT);
        //String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        val json = bundle.getString(JPushInterface.EXTRA_EXTRA)
        //        Map data = GsonUtil.getGson().fromJson(json, Map.class);
        //        String type = "";
        //        if (data.containsKey("type")) {
        //            type = data.get("type").toString();
        //        }
        //        if (TextUtils.isEmpty(type)) {
        //            type = "";
        //        }

        Log.d(PushProcess::class.java.name, json)


        try {
            val pushMessageBean = GsonUtils.gson!!.fromJson(json, PushMessageBean::class.java)
            if (pushMessageBean.msgType == PushMessageTypeEnum.SystemMessage.id) {
                val pushMessageSysBean = GsonUtils.gson!!.fromJson<PushMessageSysBean>(pushMessageBean.info, PushMessageSysBean::class.java)
                openSysDialog(context, pushMessageSysBean)

            } else if (pushMessageBean.msgType == PushMessageTypeEnum.ActivityMessage.id) {
                var bean = GsonUtils.gson!!.fromJson<PushMessageActivityBean>(pushMessageBean.info, PushMessageActivityBean::class.java)
                openAdDialog(context, bean)
            } else {
                //PushMessageSysBean pushMessageSysBean = GsonUtil.getGson().fromJson(pushMessageBean.getInfo(), PushMessageSysBean.class);
                //val intent = Intent(context, MessageActivity::class.java)
                //intent.putExtra(Constants.INTENT_MESSAGE_ID, Long.valueOf(pushMessageSysBean.getMsgId()));
                //context.startActivity(intent)
                //todo
            }
        } catch (ex: Exception) {
            Log.e(PushProcess::class.java.name, "json=" + json + " " + ex.message)
        }

    }

    /***
     * 打开系统消息推送消息框
     */
    private fun openSysDialog(context: Activity, pushMessageSysBean: PushMessageSysBean) {
        val sysMessageDialog = SysMessageDialog(context, object : SysMessageDialog.GoListener {
            override fun go(msgId: Int) {
                context.newIntent<WebActivity>(Constants.INTENT_URL, "http://www.baidu.com")
            }
        })

        sysMessageDialog.show(pushMessageSysBean!!)
    }

    /**
     * 打开广告推送消息框
     * */
    private fun openAdDialog(context: Activity, pushMessageActivityBean: PushMessageActivityBean) {
        var msgDialog = MsgDialog(context, object : OperateDialog.OnOperateListener<KeyValue> {
            override fun operate(keyValue: KeyValue) {
                context.newIntent<WebActivity>(Constants.INTENT_URL, pushMessageActivityBean.link)
            }
        })

        msgDialog.setSize(DensityUtils.getScreenWidth(context) * 80 / 100, 0)
        msgDialog.setMaxHeight(DensityUtils.getScreenHeight(context) * 2 / 3)
        msgDialog.show(pushMessageActivityBean.imageUrl)
    }


}
