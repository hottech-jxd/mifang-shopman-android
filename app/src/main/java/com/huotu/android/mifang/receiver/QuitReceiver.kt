package com.huotu.android.mifang.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class QuitReceiver: BroadcastReceiver() {
    private var quitListener: QuitListener? = null
    fun setQuitListener(quitListener: QuitListener?) {
        this.quitListener = quitListener
    }

    interface QuitListener {
        fun quitUI()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent!!.action
        if (action == QuitReceiver.ACTION_QUIT) {
            if (quitListener != null) {
                quitListener!!.quitUI()
            }
        }
    }

    companion object {
        var ACTION_QUIT="com.huotu.android.mifang.ACTION_QUIT"
    }
}