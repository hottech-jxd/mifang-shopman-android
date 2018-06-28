package com.huotu.android.mifang.widget

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView

import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.PushMessageSysBean
import com.huotu.android.mifang.util.DensityUtils

/**
 * 系统消息通知弹出框
 */
class SysMessageDialog(context: Context, var goListener: GoListener) : BaseDialog(context), View.OnClickListener {

    private var tvOperate: TextView
    private var tvClose: ImageView
    private var tvTitle: TextView
    private var tvContent: TextView
    private var bean: PushMessageSysBean?=null

    interface GoListener {
        fun go( msgId : Int )
    }

    init {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.layout_sys_message_dialog, null)
        addContentView(view)


        tvTitle = view.findViewById(R.id.biz_message_dialog_title)
        tvOperate = view.findViewById(R.id.sys_message_dialog_operate)

        tvClose = view.findViewById(R.id.sys_message_dialog_cancel)
        tvContent = view.findViewById(R.id.sys_message_dialog_content)

        tvOperate.setOnClickListener(this)
        tvClose.setOnClickListener(this)
    }


    fun show(bean: PushMessageSysBean) {
        this.bean = bean

        tvTitle.text = bean.msgTitle
        tvContent.text = bean.msgContent


        val window = dialog!!.window
        window?.setGravity(Gravity.CENTER)


        var width = DensityUtils.getScreenWidth(context)
        width = width * 80 / 100
        setSize(width, 0)

        dialog!!.show()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.sys_message_dialog_operate) {
            this.dialog!!.dismiss()
            goListener.go( bean!!.msgId )
        } else if (v.id == R.id.sys_message_dialog_cancel) {
            this.dialog!!.dismiss()
        }
    }


}
