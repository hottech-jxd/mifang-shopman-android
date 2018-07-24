package com.huotu.android.mifang.adapter

import android.support.constraint.ConstraintLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.huotu.android.mifang.R
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.util.FrescoDraweeController
import com.huotu.android.mifang.util.FrescoDraweeListener


class GoodsDetailAdapter(data :ArrayList<String?>)
    :BaseQuickAdapter<String,BaseViewHolder>(R.layout.layout_goodsdetail_item ,  data) , FrescoDraweeListener.ImageCallback {



    override fun convert(helper: BaseViewHolder?, item: String?) {

       var view = helper!!.getView<SimpleDraweeView>(R.id.goodsdetail_item_pic)
       var  sw = DensityUtils.getScreenWidth(mContext)
        FrescoDraweeController.loadImage( view , sw , 0, item!! , this)
    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView?) {
        if(simpleDraweeView==null) return
        simpleDraweeView.layoutParams = ConstraintLayout.LayoutParams(width , height)
    }
}