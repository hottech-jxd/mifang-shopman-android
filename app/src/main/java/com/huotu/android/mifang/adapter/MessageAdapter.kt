package com.huotu.android.mifang.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.util.GsonUtils

class MessageAdapter(data:List<MessageBean> )
    : BaseQuickAdapter<MessageBean,BaseViewHolder>(R.layout.layout_message_item, data) {

    init {

    }

    override fun convert(helper: BaseViewHolder?, item: MessageBean?) {

       when( item!!.JpushType ){
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

        when(item.NoticeScene ){
            NoticeSceneEnum.下线会员注册成功通知.id->{
                var bean = GsonUtils.gson!!.fromJson(item.NotificationContent , UserRegisterViewModel::class.java)
                helper!!.setGone(R.id.message_layout_system,false)
                helper!!.setGone(R.id.message_layout_register,true)
                helper!!.setGone(R.id.message_layout_pay,false)

                helper!!.setGone(R.id.layout_message_item_register_4,true)
                helper!!.setGone(R.id.layout_message_item_register_5,false)
                helper!!.setGone(R.id.layout_message_item_register_6,false)
                helper!!.setGone(R.id.layout_message_item_register_7,false)

                //helper!!.setText(R.id.message_item_register_4_title , bean.)
                helper!!.setText(R.id.message_item_register_4_time , "注册时间:"+bean.NoticeTime)
                helper!!.setText(R.id.message_item_register_4_content , bean.NoticeContent)
                helper!!.setText(R.id.message_item_register_4_RefereeNickName , "推荐人:"+ bean.RefereeNickName)
                helper!!.setText(R.id.message_item_register_4_nickname , "会员昵称:"+bean.UserLoginName +"("+ bean.UserWxNickName+")")
                helper!!.getView<SimpleDraweeView>(R.id.message_item_register_4_logo).setImageURI(bean.UserHeadImgURL)

            }
            NoticeSceneEnum.用户升级为代理商.id->{

                var bean = GsonUtils.gson!!.fromJson(item.NotificationContent , AgentUpgradeViewModel::class.java)
                helper!!.setGone(R.id.message_layout_system,false)
                helper!!.setGone(R.id.message_layout_register,true)
                helper!!.setGone(R.id.message_layout_pay,false)

                helper!!.setGone(R.id.layout_message_item_register_4,false)
                helper!!.setGone(R.id.layout_message_item_register_5,true)
                helper!!.setGone(R.id.layout_message_item_register_6,false)
                helper!!.setGone(R.id.layout_message_item_register_7,false)


                helper!!.setText(R.id.message_item_register_5_SurplusDeposit , "现有货款:"+ bean.SurplusDeposit+"元")
                helper!!.setText(R.id.message_item_register_5_nickname , "会员昵称:"+bean.UserWxNickName)
                helper!!.setText(R.id.message_item_register_5_time , bean.NoticeTime)
                helper!!.setText(R.id.message_item_register_5_sub_title, bean.NoticeContent)
                helper!!.getView<SimpleDraweeView>(R.id.message_item_register_5_avator).setImageURI(bean.UserHeadImgURL)


                helper!!.addOnClickListener(R.id.message_item_register_5_see)
            }
            NoticeSceneEnum.成功邀请用户成为营养师.id->{

                var bean = GsonUtils.gson!!.fromJson(item.NotificationContent , UserUpgradeViewModel::class.java)
                helper!!.setGone(R.id.message_layout_system,false)
                helper!!.setGone(R.id.message_layout_register,true)
                helper!!.setGone(R.id.message_layout_pay,false)

                helper!!.setGone(R.id.layout_message_item_register_4,false)
                helper!!.setGone(R.id.layout_message_item_register_5,false)
                helper!!.setGone(R.id.layout_message_item_register_6,true)
                helper!!.setGone(R.id.layout_message_item_register_7,false)

                helper!!.setText(R.id.message_item_register_6_UpgradeTime , "升级时间:"+ bean.UpgradeTime)
                helper!!.setText(R.id.message_item_register_6_nickname , "会员昵称:"+bean.UserWxNickName)
                helper!!.setText(R.id.message_item_register_6_time , bean.NoticeTime)
                helper!!.setText(R.id.message_item_register_6_sub_title, bean.NoticeContent)
                helper!!.setText(R.id.message_item_register_6_ProfitAmount , "获取收益:"+bean.ProfitAmount+"元")

                helper!!.getView<SimpleDraweeView>(R.id.message_item_register_6_avator).setImageURI(bean.UserHeadImgURL)
            }
            NoticeSceneEnum.成功邀请用户成为代理商.id->{
                var bean = GsonUtils.gson!!.fromJson(item.NotificationContent , UserUpgradeViewModel::class.java)
                helper!!.setGone(R.id.message_layout_system,false)
                helper!!.setGone(R.id.message_layout_register,true)
                helper!!.setGone(R.id.message_layout_pay,false)

                helper!!.setGone(R.id.layout_message_item_register_4,false)
                helper!!.setGone(R.id.layout_message_item_register_5,false)
                helper!!.setGone(R.id.layout_message_item_register_6,false)
                helper!!.setGone(R.id.layout_message_item_register_7,true)

                helper!!.setText(R.id.message_item_register_7_UpgradeTime , "升级时间:"+ bean.UpgradeTime)
                helper!!.setText(R.id.message_item_register_7_nickname , "会员昵称:"+bean.UserWxNickName)
                helper!!.setText(R.id.message_item_register_7_time , bean.NoticeTime)
                helper!!.setText(R.id.message_item_register_7_sub_title, bean.NoticeContent)
                helper!!.setText(R.id.message_item_register_7_ProfitAmount , "获取货款:"+bean.ProfitAmount+"元")
                helper!!.getView<SimpleDraweeView>(R.id.message_item_register_7_avator).setImageURI(bean.UserHeadImgURL)

            }
        }

    }



    private fun setPayMessage(helper: BaseViewHolder?,item:MessageBean){

        when(item.NoticeScene){

            NoticeSceneEnum.下线会员订单支付成功通知.id->{
                var bean = GsonUtils.gson!!.fromJson(item.NotificationContent , OrderPaySuccessViewModel::class.java)
                helper!!.setGone(R.id.message_layout_system,false)
                helper!!.setGone(R.id.message_layout_register,false)
                helper!!.setGone(R.id.message_layout_pay,true)

                helper!!.setGone(R.id.message_item_layout_pay_9,true)
                helper!!.setGone(R.id.message_item_layout_pay_10,false)

                helper!!.setText(R.id.message_item_pay_9_OrderId , "订单编号:"+ bean.OrderId)
                helper!!.setText(R.id.message_item_pay_9_OrderName , "订单商品:"+bean.OrderName)
                helper!!.setText(R.id.message_item_pay_9_OrderAmount , "支付金额:" + bean.OrderAmount)
                helper!!.setText(R.id.message_item_pay_9_ProfitAmount , "获取返利:"+bean.ProfitAmount)
                helper!!.setText(R.id.message_item_pay_9_UserWxNickName , "购买人昵称:"+bean.UserWxNickName)
                helper!!.setText(R.id.message_item_pay_9_paytime , "支付时间:"+ bean.PayTime)
                helper!!.setText(R.id.message_item_pay_9_time ,  bean.NoticeTime)


                helper!!.getView<SimpleDraweeView>(R.id.message_item_pay_9_avator).setImageURI(bean.UserHeadImgURL)

            }
            NoticeSceneEnum.用户商品购买成功通知.id->{
                var bean = GsonUtils.gson!!.fromJson(item.NotificationContent , AgentOrderPaySuccessViewModel::class.java)
                helper!!.setGone(R.id.message_layout_system,false)
                helper!!.setGone(R.id.message_layout_register,false)
                helper!!.setGone(R.id.message_layout_pay,true)

                helper!!.setGone(R.id.message_item_layout_pay_9,false)
                helper!!.setGone(R.id.message_item_layout_pay_10,true)

                helper!!.setText(R.id.message_item_pay_10_OrderId , "订单编号:"+ bean.OrderId)
                helper!!.setText(R.id.message_item_pay_10_OrderName , "订单商品:"+bean.OrderName)
                helper!!.setText(R.id.message_item_pay_10_OrderAmount , "支付金额:" + bean.OrderAmount)
                helper!!.setText(R.id.message_item_pay_10_AgentPrice , "代理价:"+bean.AgentPrice)
                helper!!.setText(R.id.message_item_pay_10_UserWxNickName , "购买人昵称:"+bean.UserWxNickName)
                helper!!.setText(R.id.message_item_pay_10_paytime , "支付时间:"+ bean.PayTime)
                helper!!.setText(R.id.message_item_pay_10_SurplusDeposit , "剩余货款:"+ bean.SurplusDeposit)
                helper!!.setText(R.id.message_item_pay_10_time ,  bean.NoticeTime)
                helper!!.getView<SimpleDraweeView>(R.id.message_item_pay_10_avator).setImageURI(bean.UserHeadImgURL)

            }
        }

//        helper!!.setGone(R.id.message_layout_pay , true)
//        helper!!.setGone(R.id.message_layout_register, false)
//        helper!!.setGone(R.id.message_layout_system , false)

    }

    private fun setSystemMessage(helper: BaseViewHolder?,item:MessageBean){
        when(item.NoticeScene){
            NoticeSceneEnum.后台系统消息.id->{
                var bean = GsonUtils.gson!!.fromJson(item.NotificationContent, SystemNoticeViewModel::class.java)
                helper!!.setGone(R.id.message_layout_system,true)
                helper!!.setGone(R.id.message_layout_register,false)
                helper!!.setGone(R.id.message_layout_pay,false)

                helper!!.setGone(R.id.message_item_layout_system_0,true)
                helper!!.setGone(R.id.message_item_layout_system_1,false)
                helper!!.setGone(R.id.message_item_layout_system_2,false)
                helper!!.setGone(R.id.message_item_layout_system_3,false)


                helper!!.setImageResource(R.id.message_item_system_0_logo , R.mipmap.message3)
                helper!!.setText(R.id.message_item_system_0_title , bean.NoticeTitle)
                helper!!.setText(R.id.message_item_system_0_content , bean.NoticeContent)
                helper!!.setText(R.id.message_item_system_0_time , bean.NoticeTime)
                Glide.with(mContext).load(bean.NoticeImgURL).into( helper.getView(R.id.message_item_system_0_bg))

                helper.addOnClickListener(R.id.message_item_layout_system_0)//点击系统消息，调转网页

            }
            NoticeSceneEnum.营养师到期通知.id->{

                var bean = GsonUtils.gson!!.fromJson(item.NotificationContent, DietitianNoticeViewModel::class.java)
                helper!!.setGone(R.id.message_layout_system,true)
                helper!!.setGone(R.id.message_layout_register,false)
                helper!!.setGone(R.id.message_layout_pay,false)

                helper!!.setGone(R.id.message_item_layout_system_0,false)
                helper!!.setGone(R.id.message_item_layout_system_1,true)
                helper!!.setGone(R.id.message_item_layout_system_2,false)
                helper!!.setGone(R.id.message_item_layout_system_3,false)

                helper!!.setText(R.id.message_item_system_1_ExpireTime , "到期时间:"+ bean.ExpireTime )
                helper!!.setText(R.id.message_item_system_1_SurplusDay , "剩余时间:"+ bean.SurplusDay+"天")
                helper!!.setText(R.id.message_item_system_1_nickname, "会员昵称:"+bean.UserWxNickName)
                helper!!.setText(R.id.message_item_system_1_sub_title , bean.NoticeContent)
                helper!!.getView<SimpleDraweeView>(R.id.message_item_system_1_logo).setImageURI(bean.UserHeadImgURL)


                helper!!.addOnClickListener(R.id.message_item_system_1_cash)

            }
            NoticeSceneEnum.营养师续费成功通知.id->{

                var bean = GsonUtils.gson!!.fromJson(item.NotificationContent, DietitianNoticeViewModel::class.java)

                helper!!.setGone(R.id.message_layout_system,true)
                helper!!.setGone(R.id.message_layout_register,false)
                helper!!.setGone(R.id.message_layout_pay,false)

                helper!!.setGone(R.id.message_item_layout_system_0,false)
                helper!!.setGone(R.id.message_item_layout_system_1,false)
                helper!!.setGone(R.id.message_item_layout_system_2,true)
                helper!!.setGone(R.id.message_item_layout_system_3,false)

                helper!!.setText(R.id.message_item_system_2_ExpireTime , "到期时间:"+ bean.ExpireTime )
                helper!!.setText(R.id.message_item_system_2_SurplusDay , "剩余时间:"+ bean.SurplusDay+"天")
                helper!!.setText(R.id.message_item_system_2_nickname, "会员昵称:"+bean.UserWxNickName)
                helper!!.setText(R.id.message_item_system_2_sub_title , bean.NoticeContent)
                helper!!.getView<SimpleDraweeView>(R.id.message_item_system_2_logo).setImageURI(bean.UserHeadImgURL)

            }
            NoticeSceneEnum.代理商货款不足.id->{
                var bean = GsonUtils.gson!!.fromJson(item.NotificationContent, DepositNoticeViewModel::class.java)

                helper!!.setGone(R.id.message_layout_system,true)
                helper!!.setGone(R.id.message_layout_register,false)
                helper!!.setGone(R.id.message_layout_pay,false)

                helper!!.setGone(R.id.message_item_layout_system_0,false)
                helper!!.setGone(R.id.message_item_layout_system_1,false)
                helper!!.setGone(R.id.message_item_layout_system_2,false)
                helper!!.setGone(R.id.message_item_layout_system_3,true)

                helper!!.setText(R.id.message_item_system_3_nickname, "会员昵称:"+ bean.UserWxNickName)
                helper!!.setText(R.id.message_item_system_3_LockIntegral , "冻结收益:"+bean.LockIntegral+"元")
                helper!!.setText(R.id.message_item_system_3_OweDeposit,"已欠货款:"+bean.OweDeposit+"元")
                helper!!.getView<SimpleDraweeView>(R.id.message_item_system_3_logo).setImageURI(bean.UserHeadImgURL)
                helper!!.setText(R.id.message_item_system_3_sub_title , bean.NoticeContent)


                helper!!.addOnClickListener(R.id.message_item_system_3_cash)
            }
        }


        //helper!!.setGone(R.id.message_layout_pay , false)
        //helper!!.setGone(R.id.message_layout_register, false)
        //helper!!.setGone(R.id.message_layout_system , true)

        //helper!!.setText(R.id.message_item_system_title, item!!.)
        //helper!!.setText(R.id.message_item_system_content , item!!.content)
        //helper.setImageResource(R.id.message_item_system_logo , R.mipmap.message3)
        //helper!!.setText(R.id.message_item_system_time, item.time)
        //helper.getView<SimpleDraweeView>(R.id.message_item_bg).setImageURI(item.imageUrl)
        //Glide.with(mContext).load(item.imageUrl).into( helper.getView(R.id.message_item_system_bg) )
    }
}