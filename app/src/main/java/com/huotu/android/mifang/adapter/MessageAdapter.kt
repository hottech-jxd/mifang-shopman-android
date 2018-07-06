package com.huotu.android.mifang.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.MessageBean
import com.huotu.android.mifang.bean.MessageTypeEnum

class MessageAdapter(data:List<MessageBean> )
    : BaseQuickAdapter<MessageBean,BaseViewHolder>(R.layout.layout_message_item, data) {

    init {

    }

    override fun convert(helper: BaseViewHolder?, item: MessageBean?) {

       when( item!!.type ){
           MessageTypeEnum.SystemMessage.id->{
               setSystemMessage(helper, item)
           }
           MessageTypeEnum.PayMessage.id->{
               setPayMessage(helper, item)
           }
           MessageTypeEnum.RegisterMessage.id->{
               setRegisterMessage(helper,item)
           }
       }
    }
    private fun setRegisterMessage(helper: BaseViewHolder?,item:MessageBean){
        helper!!.setGone(R.id.message_layout_pay , false)
        helper!!.setGone(R.id.message_layout_register, true)
        helper!!.setGone(R.id.message_layout_system , false)

        helper!!.setText(R.id.message_item_register_title, item.title)
        helper!!.setText(R.id.message_item_register_time , item.time)
        helper!!.setText(R.id.message_item_register_content , item.content)
        Glide.with(mContext).load(item.imageUrl).into( helper.getView(R.id.message_item_register_logo) )
    }

    private fun setPayMessage(helper: BaseViewHolder?,item:MessageBean){
        helper!!.setGone(R.id.message_layout_pay , true)
        helper!!.setGone(R.id.message_layout_register, false)
        helper!!.setGone(R.id.message_layout_system , false)

        helper!!.setText(R.id.message_item_pay_title, item.title)
        helper!!.setText(R.id.message_item_pay_time , item.time)
        helper!!.setText(R.id.message_item_pay_content , item.content)
    }

    private fun setSystemMessage(helper: BaseViewHolder?,item:MessageBean){
        helper!!.setGone(R.id.message_layout_pay , false)
        helper!!.setGone(R.id.message_layout_register, false)
        helper!!.setGone(R.id.message_layout_system , true)

        helper!!.setText(R.id.message_item_system_title, item!!.title)
        helper!!.setText(R.id.message_item_system_content , item!!.content)
        helper.setImageResource(R.id.message_item_system_logo , R.mipmap.message3)
        helper!!.setText(R.id.message_item_system_time, item.time)
        //helper.getView<SimpleDraweeView>(R.id.message_item_bg).setImageURI(item.imageUrl)
        Glide.with(mContext).load(item.imageUrl).into( helper.getView(R.id.message_item_system_bg) )
    }
}