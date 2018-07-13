package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.PaymentItem

class PaymentAdapter(data : ArrayList<PaymentItem>): BaseQuickAdapter<PaymentItem , BaseViewHolder>( R.layout.layout_payment_item , data) {

    override fun convert(helper: BaseViewHolder?, item: PaymentItem?) {


        helper!!.setText(R.id.payment_item_name, item!!.PaymentName)

        helper!!.setBackgroundRes(R.id.payment_item_name, if (item!!.checked) R.drawable.shape_edit_pay_account_selected_bg else R.drawable.shape_edit_pay_account_bg)
    }
}
