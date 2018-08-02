package com.huotu.android.mifang.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.huotu.android.mifang.bean.Constants

class PayReceiver: BroadcastReceiver() {

    private var payListener: PayListener? = null
    fun setPayListener( payListener: PayListener?) {
        this.payListener = payListener
    }

    interface PayListener {
        fun payCallback( success:Boolean)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent!!.action
        if (action == PayReceiver.ACTION_PAY) {
            if (payListener != null) {
                var status = intent!!.getBooleanExtra(Constants.INTENT_STATUS , true)
                payListener!!.payCallback(status)
            }
        }
    }

    companion object {
        var ACTION_PAY="com.huotu.android.mifang.ACTION_PAY"
    }

}