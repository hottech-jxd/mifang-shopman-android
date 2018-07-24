package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.DepositBean


class MomeyAdapter(data : ArrayList<DepositBean.DepositItem>): BaseQuickAdapter<DepositBean.DepositItem , BaseViewHolder>( R.layout.layout_momey_item , data) {

    override fun convert(helper: BaseViewHolder?, item: DepositBean.DepositItem?) {

        helper!!.setText(R.id.momey_item_name, item!!.DepositPrice )

        helper!!.setBackgroundRes(R.id.momey_item_name, if (item!!.checked) R.drawable.shape_edit_pay_account_selected_bg else R.drawable.shape_edit_pay_account_bg)
    }
}
