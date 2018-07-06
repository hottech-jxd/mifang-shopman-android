package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.TermIndex0Entity
import com.huotu.android.mifang.bean.TermIndex1Entity
import com.huotu.android.mifang.bean.TermIndex2Entity

class TermIndexAdapter(var data :ArrayList<MultiItemEntity>) : BaseMultiItemQuickAdapter<MultiItemEntity , BaseViewHolder>(data ) {

    init {
        addItemType(0 , R.layout.layout_my_term_item_0)
        addItemType(1 , R.layout.layout_my_term_item_1)
        addItemType(2 , R.layout.layout_my_term_item_2)
    }

    override fun convert(helper: BaseViewHolder?, item: MultiItemEntity?) {
        when(helper!!.itemViewType ){
            0-> {
                var bean = item as TermIndex0Entity
                helper!!.setText(R.id.my_term_item_title, bean.title)
            }
            1->{
                var bean = item as TermIndex1Entity
                helper!!.setText(R.id.my_term_item_title1 , bean.content1)
                helper!!.setText(R.id.my_term_item_title2, bean.content2)
                helper!!.setText(R.id.my_term_item_title3 , bean.content3)
            }
            2->{
                var bean = item as TermIndex2Entity
                helper!!.setText(R.id.my_term_item_title1, bean.termInfo.LevelName)
                helper!!.setText(R.id.my_term_item_title2 , bean.termInfo.BelongOneMemberNum.toString())
                helper!!.setText(R.id.my_term_item_title3 , bean.termInfo.BelongTwoMemberNum.toString())

            }
        }
    }
}