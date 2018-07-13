package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.MiBean
import com.huotu.android.mifang.bean.ScoreBean

class ScoreAdapter(data:ArrayList<ScoreBean.ScoreDetail>)
    :BaseQuickAdapter<ScoreBean.ScoreDetail,BaseViewHolder>( R.layout.layout_score_item , data) {

    override fun convert(helper: BaseViewHolder?, item: ScoreBean.ScoreDetail?) {

        helper!!.setText(R.id.score_item_score, item!!.ChangeIntegral.toString())
        helper!!.setText(R.id.score_item_name , item!!.ChangeDesc)
        helper!!.setText(R.id.score_item_time, "时间:"+item!!.ChangeTime)

//        helper!!.setTextColor(R.id.score_item_score,
//                if(item!!.type==1) ContextCompat.getColor( mContext , R.color.textcolor3 )
//                else ContextCompat.getColor(mContext , R.color.textcolor2) )
    }
}

class MiAdapter(data:ArrayList<MiBean.MiDetail>)
    :BaseQuickAdapter<MiBean.MiDetail,BaseViewHolder>( R.layout.layout_score_item , data) {

    override fun convert(helper: BaseViewHolder?, item: MiBean.MiDetail?) {

        helper!!.setText(R.id.score_item_score, item!!.ChangeMiBean.toString())
        helper!!.setText(R.id.score_item_name , item!!.ChangeDesc)
        helper!!.setText(R.id.score_item_time, "时间:"+item!!.ChangeTime)

//        helper!!.setTextColor(R.id.score_item_score,
//                if(item!!.type==1) ContextCompat.getColor( mContext , R.color.textcolor3 )
//                else ContextCompat.getColor(mContext , R.color.textcolor2) )
    }
}