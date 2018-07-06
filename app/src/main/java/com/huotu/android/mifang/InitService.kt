package com.huotu.android.mifang

import android.app.IntentService
import android.content.Context
import android.content.Intent

class InitService : IntentService(ACTION_INITSERVICE) {

    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) return
        val action = intent.action ?: return
        if (ACTION_INITSERVICE != action) return
        AppInit.init(this.application)
    }

    companion object {
        val ACTION_INITSERVICE = IntentService::class.java.name

        fun start(context: Context) {
            val intent = Intent(context, InitService::class.java)
            intent.action = ACTION_INITSERVICE
            context.startService(intent)
        }
    }
}


