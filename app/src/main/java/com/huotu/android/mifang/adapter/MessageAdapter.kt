package com.huotu.android.mifang.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.MessageBean
import com.huotu.android.mifang.bean.MessageTypeEnum

class MessageAdapter(data:List<MessageBean>) : BaseQuickAdapter<MessageBean,BaseViewHolder>(R.layout.layout_message_item, data) {

    override fun convert(helper: BaseViewHolder?, item: MessageBean?) {

        helper!!.setText(R.id.message_item_title, item!!.title)
        helper!!.setText(R.id.message_item_content , item!!.content)
        when( item!!.type ) {
            MessageTypeEnum.SystemMessage.id-> {
                helper.setImageResource(R.id.message_item_logo , R.mipmap.message3)
            }
            MessageTypeEnum.ActivityMessage.id->{
                helper.setImageResource(R.id.message_item_logo , R.mipmap.message1)
            }
            MessageTypeEnum.MallMessage.id->{
                helper.setImageResource(R.id.message_item_logo , R.mipmap.message2)
            }
        }

        helper!!.setText(R.id.message_item_time, item.time)

        //helper.getView<SimpleDraweeView>(R.id.message_item_bg).setImageURI(item.imageUrl)
        Glide.with(mContext).load(item.imageUrl).into( helper.getView(R.id.message_item_bg) )
    }
}