package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.CashRecord
import com.huotu.android.mifang.bean.PayAccount

class PayAccountAdapter(data :ArrayList<PayAccount>):BaseQuickAdapter<PayAccount,BaseViewHolder>( R.layout.layout_pay_account_item , data) {

    override fun convert(helper: BaseViewHolder?, item: PayAccount?) {
        helper!!.addOnClickListener(R.id.pay_account_operate)
        //helper!!.addOnClickListener(R.id.pay_account_lay_account)
        //helper!!.addOnClickListener(R.id.pay_account_check)

        var name = item!!.RealName
        name += if( item!!.IsDefault==1) "(默认)" else ""

        helper!!.setText(R.id.pay_account_name , name)
        helper!!.setText(R.id.pay_account_account, item!!.AccountInfo)

        helper!!.setImageResource(R.id.pay_account_check , if( item!!.checked) R.mipmap.check else R.mipmap.uncheck )
    }
}