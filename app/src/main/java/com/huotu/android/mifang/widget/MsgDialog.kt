package com.huotu.android.mifang.widget

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.activity.WebActivity
import com.huotu.android.mifang.bean.AdBean
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.KeyValue
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.widget.OperateDialog.OnOperateListener

/**
 * 首页消息弹出框
 */
class MsgDialog(context: Context, onOperateListener: OnOperateListener<KeyValue>?)
    : BaseDialog(context), View.OnClickListener {

    var bean:AdBean?=null

//    interface OnOperateListener {
//        fun operate(keyValue: KeyValue)
//    }

    init {

        if (onOperateListener == null) {
            throw NullPointerException("参数空异常")
        }
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.layout_msg_one_dialog, null)

        val iv = view.findViewById<ImageView>(R.id.msg_one_logo)
        iv.setOnClickListener(this)

        //var layout = view.layoutParams
        //layout.width = DensityUtils.getScreenWidth(context) *80/100
        //layout.height = DensityUtils.getScreenHeight(context);
        //view.layoutParams = layout

        this.addContentView(view)
        val ivClose = view.findViewById<ImageView>(R.id.msg_one_close)
        ivClose.setOnClickListener(this)

    }

    fun setMaxHeight(maxHeight:Int){
        var iv = this.dialog!!.findViewById<ImageView>(R.id.msg_one_logo)
        iv.maxHeight = maxHeight
        iv.adjustViewBounds =true
    }

    fun show(imageUrl:String?){
        show()

        var iv = this.dialog!!.findViewById<ImageView>(R.id.msg_one_logo)
        Glide.with(context).load( imageUrl )
                .into(iv)
    }


    fun show(bean: AdBean){
        this.bean = bean
        var iv = dialog!!.findViewById<ImageView>(R.id.msg_one_logo)
        Glide.with(context).load( bean.pictureUrl)
                .into(iv)

        show()
    }

    override fun show() {

        val window = this.dialog!!.window
        if (window != null) {
            window!!.setWindowAnimations(R.style.anim_dialog)
            window!!.setGravity(Gravity.CENTER)
        }
        super.show()
    }

    override fun onClick(v: View?) {
        when( v!!.id ){
            R.id.msg_one_close->{
                dismiss()
            }
            R.id.msg_one_logo->{
                dismiss()
                var intent = Intent(context, WebActivity::class.java)
                intent.putExtra(Constants.INTENT_URL, bean!!.linkUrl)
                context.startActivity(intent)
            }
        }

    }
}
