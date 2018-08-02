package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.MiBean
import com.huotu.android.mifang.bean.ScoreBean
import java.math.BigDecimal

class ScoreAdapter(data:ArrayList<ScoreBean.ScoreDetail>)
    :BaseQuickAdapter<ScoreBean.ScoreDetail,BaseViewHolder>( R.layout.layout_score_item , data) {

    override fun convert(helper: BaseViewHolder?, item: ScoreBean.ScoreDetail?) {


        var score = BigDecimal( item!!.ChangeIntegral).setScale(2,BigDecimal.ROUND_HALF_UP).div(BigDecimal(100))


        helper!!.setText(R.id.score_item_score, score.stripTrailingZeros().toPlainString() )
        helper!!.setText(R.id.score_item_name , item!!.ChangeDesc)
        helper!!.setText(R.id.score_item_time, "时间:"+item!!.ChangeTime)

    }
}

class MiAdapter(data:ArrayList<MiBean.MiDetail>)
    :BaseQuickAdapter<MiBean.MiDetail,BaseViewHolder>( R.layout.layout_score_item , data) {

    override fun convert(helper: BaseViewHolder?, item: MiBean.MiDetail?) {

        //var score = BigDecimal( item!!.ChangeMiBean).setScale(2,BigDecimal.ROUND_HALF_UP).div(BigDecimal(100))

        helper!!.setText(R.id.score_item_score, item!!.ChangeMiBean.toString())
        helper!!.setText(R.id.score_item_name , item!!.ChangeDesc)
        helper!!.setText(R.id.score_item_time, "时间:"+item!!.ChangeTime)

    }
}