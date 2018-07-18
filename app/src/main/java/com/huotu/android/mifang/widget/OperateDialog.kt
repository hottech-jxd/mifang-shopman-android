package com.huotu.android.mifang.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.KeyValue


/**
 * 操作对话框
 */
class OperateDialog<T>(context: Context,
                    var onOperateListener: OnOperateListener<T>?,
                     var list: List<T>, titleString: String)
    : BaseDialog(context), BaseQuickAdapter.OnItemClickListener {
    internal var operateAdapter: OperateAdapter<T>
    internal var title: TextView

    interface OnOperateListener<T> {
        fun operate(keyValue: T)
    }

    init {

        if (onOperateListener == null) {
            throw NullPointerException("参数空异常")
        }
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.layout_operate_dialog, null)


        this.addContentView(view)
        val recyclerView = view.findViewById<RecyclerView>(R.id.operate_dialog_list)

        recyclerView.isVerticalScrollBarEnabled=true

        recyclerView.layoutManager =LinearLayoutManager(context)
        title = view.findViewById(R.id.operate_dialog_title)
        title.text = titleString
        operateAdapter = OperateAdapter(list)
        operateAdapter.onItemClickListener = this
        recyclerView.adapter = operateAdapter
        recyclerView.addItemDecoration(RecyclerViewDivider(context, ContextCompat.getColor( context , R.color.bg_line ),1f))

    }

    override fun show() {
        val window = this.dialog!!.window
        if (window != null) {
            window!!.setWindowAnimations(R.style.anim_dialog)
        }
        super.show()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        dialog!!.dismiss()
        onOperateListener!!.operate(adapter.getItem(position) as T)
    }

    inner class OperateAdapter<T>(data: List<T>?) : BaseQuickAdapter<T, BaseViewHolder>(R.layout.layout_operate_dialog_item, data) {

        override fun convert(helper: BaseViewHolder, item: T) {
            helper.setText(R.id.operate_dialog_item_name, item.toString() )
        }
    }


}
