package com.huotu.android.mifang.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.FilterEntry

class FilterAdapter(data:ArrayList<FilterEntry>)
    :BaseMultiItemQuickAdapter<FilterEntry,BaseViewHolder>(data ) {
    init {
        this.addItemType( 1 , R.layout.layout_filter_header )
        this.addItemType(2,R.layout.layout_filter_item)
        this.addItemType(3 , R.layout.layout_filter_item)
        this.addItemType(4,R.layout.layout_filter_item_3)
    }

    override fun convert(helper: BaseViewHolder?, item: FilterEntry?) {
        if( helper!!.itemViewType == 1){
            helper!!.setText(R.id.filter_item_text , item!!.filterBean.name )
        }else if(helper!!.itemViewType==2){
            helper!!.setText(R.id.filter_item_text , item!!.filterBean.name)
            helper!!.setGone(R.id.filter_item_value , false)
            helper!!.addOnClickListener(R.id.filter_item_container)
            helper.setBackgroundRes(R.id.filter_item_container , if(item!!.selected) R.drawable.shape_filter_selected else R.drawable.shape_filter_normal  )
        }else if(helper!!.itemViewType==3){
            helper!!.setText(R.id.filter_item_text , item!!.filterBean.name)
            helper!!.setGone(R.id.filter_item_value , true)
            helper!!.addOnClickListener(R.id.filter_item_container)
            helper.setBackgroundRes(R.id.filter_item_container , if(item!!.selected) R.drawable.shape_filter_selected else R.drawable.shape_filter_normal  )

            helper.setBackgroundRes(R.id.filter_item_value , if(item!!.selected) R.drawable.shape_filter_item_value_selected else R.drawable.shape_filter_item_value_bg )

        }
    }
}