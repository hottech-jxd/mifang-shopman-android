package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.TermBean
import com.huotu.android.mifang.bean.TermItemBean
import com.huotu.android.mifang.util.DateUtils
import java.util.*

class TermAdapter(data :ArrayList<TermItemBean>) :BaseQuickAdapter<TermItemBean,BaseViewHolder>( R.layout.layout_term_item , data) {

    override fun convert(helper: BaseViewHolder?, item: TermItemBean?) {
        helper!!.getView<SimpleDraweeView>(R.id.term_item_logo)
                .setImageURI(item!!.UserImage)
        helper!!.setText(R.id.term_item_name , item!!.NickName)
        helper!!.setText(R.id.term_item_type , item!!.UserLevelName)
        helper!!.setText(R.id.term_item_level , if(item!!.Relation==0) "一级团队" else "二级团队")

        helper!!.setText(R.id.term_item_count , "粉丝数:"+item!!.FansAmount+"人")
        helper!!.setText(R.id.term_item_score , item!!.OfferScore)
        helper!!.setText(R.id.term_item_invite_man , "推荐人:"+item!!.Recommender)
        helper!!.setText(R.id.term_item_register_time , "注册:"+item!!.RegisterTime)

        var lastLoginTime =DateUtils.formatDate( item!!.LastLoginTime)


        helper!!.setText(R.id.term_item_login_time , "最后登录:"+ lastLoginTime )
    }
}