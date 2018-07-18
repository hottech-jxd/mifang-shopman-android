package com.huotu.android.mifang.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
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
 * 分享弹出框
 */
class ShareDialog(context: Context,
                  var onOperateListener: OnOperateListener?,
                  var list: List<KeyValue>)
    : BaseDialog(context), BaseQuickAdapter.OnItemClickListener {
    var shareAdapter: ShareAdapter?=null
    //var title: TextView

    interface OnOperateListener {
        fun operate(keyValue: KeyValue)
    }

    init {

        if (onOperateListener == null) {
            throw NullPointerException("参数空异常")
        }
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.layout_share_dialog, null)

        this.addContentView(view)
        val recyclerView = view.findViewById<RecyclerView>(R.id.share_dialog_recyclerview)

        val columnCount = if( list.size<=3) list.size else 3

        recyclerView.layoutManager =GridLayoutManager(context,columnCount )

        shareAdapter = ShareAdapter(list)
        shareAdapter!!.onItemClickListener = this
        recyclerView.adapter = shareAdapter
        //recyclerView.addItemDecoration(RecyclerViewDivider(context, ContextCompat.getColor( context , R.color.bg_line ),1f))

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
        onOperateListener!!.operate(adapter.getItem(position) as KeyValue)
    }

    inner class ShareAdapter(data: List<KeyValue>?) : BaseQuickAdapter<KeyValue, BaseViewHolder>(R.layout.layout_share_dialog_item, data) {

        override fun convert(helper: BaseViewHolder, item: KeyValue) {
            helper.setImageResource(R.id.share_dialog_item_logo , item.code)
            helper.setText(R.id.share_dialog_item_title, item.name )
        }
    }


}
