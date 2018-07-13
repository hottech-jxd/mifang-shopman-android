package com.huotu.android.mifang.util

import android.app.Application
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.huotu.android.mifang.base.BaseApplication


class ToastUtils {
    private var toast: Toast? = null



    private fun showToast( context: Context , msg: String, duration: Int) {
        if (toast == null) {
            toast = Toast.makeText( context , msg, duration)
        }
        toast!!.duration = duration
        toast!!.setText(msg)
        toast!!.show()
    }

    fun showLongToast( context: Context , msg: String) {
        showToast(context ,msg, Toast.LENGTH_LONG)
    }

    fun showToast(msg:String){
        showLongToast(BaseApplication.instance as Context , msg )
    }

    fun showToast(context: Context, msg: String, resId: Int, duration: Int) {
        val t = Toast.makeText( context , msg, duration)
        t.setGravity(Gravity.CENTER, 0, 0)
        val v = View(context)
        v.setBackgroundResource(resId)
        t.view = v
        t.setText(msg)
        t.show()
    }


    companion object {
        var single = ToastUtils()
        fun getInstance():ToastUtils{
            return single
        }
    }

}
