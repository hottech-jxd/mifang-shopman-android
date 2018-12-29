package com.huotu.android.mifang.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class WechatLoginReceiver(var loginListener:LoginListener?):BroadcastReceiver() {



    interface LoginListener{
        fun weChatCallback( code :String)
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        if(loginListener!=null) {
            var code = intent!!.getStringExtra("code")
            loginListener!!.weChatCallback(code)
        }
    }
}