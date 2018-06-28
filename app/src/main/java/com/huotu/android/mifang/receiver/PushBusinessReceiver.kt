package com.huotu.android.mifang.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.huotu.android.library.libpush.PushReceiver
import com.huotu.android.mifang.activity.PushHandlerActivity
import com.huotu.android.mifang.bean.Constants


/**
 *
 * Created by jinxiangdong on 2017/12/18.
 */
class PushBusinessReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == PushReceiver.ACTION_PUSH_BUSINESS) {
            dealMessage(context, intent)
        }
    }

    protected fun dealMessage(context: Context, intent: Intent) {
        val bd = intent.extras ?: return
//        String content = bd.getString(JPushInterface.EXTRA_ALERT);
        //        String title = bd.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        //        String json = bd.getString(JPushInterface.EXTRA_EXTRA);

        //        ToastUtils.showLongToast(title+ " " +content+ " " + json );


        val actionIntent = Intent()
        actionIntent.putExtra(Constants.INTENT_PUSH_KEY, bd)
        actionIntent.setClass(context, PushHandlerActivity::class.java)
        //actionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        actionIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(actionIntent)
    }
}
