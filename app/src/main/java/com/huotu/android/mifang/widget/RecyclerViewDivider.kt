package com.huotu.android.mifang.widget

import android.content.Context
import android.support.annotation.ColorInt
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration

class RecyclerViewDivider(context:Context , @ColorInt var dividerColor: Int , var width:Float ) :Y_DividerItemDecoration(context){

    override fun getDivider(itemPosition: Int): Y_Divider {
        return Y_DividerBuilder()
                .setBottomSideLine(true, dividerColor , width , 0f,0f )
                .create()
    }

}