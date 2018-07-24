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


class RecyclerViewDivider2(context: Context , @ColorInt var dividerColor: Int ,var width: Float ):Y_DividerItemDecoration(context){
    override fun getDivider(itemPosition: Int): Y_Divider {
         return Y_DividerBuilder()
                //.setTopSideLine(true , dividerColor , width , 0f,0f)
                .setRightSideLine(true, dividerColor , width , 0f,0f )
                .create()
    }
}


class RecyclerViewDividerEmpty(context: Context ):Y_DividerItemDecoration(context){
    override fun getDivider(itemPosition: Int): Y_Divider {
         return Y_DividerBuilder().create()
    }
}

class RecyclerViewDivider4(context: Context , @ColorInt var dividerColor: Int ,var width: Float ):Y_DividerItemDecoration(context){
    override fun getDivider(itemPosition: Int): Y_Divider {
        if(itemPosition %2 == 0 )
            return Y_DividerBuilder()
                .setRightSideLine(true, dividerColor , width/2 , 0f,0f )
                    .setBottomSideLine(true,dividerColor, width,0f,0f)
                .create()
        else  return Y_DividerBuilder()
                .setLeftSideLine(true, dividerColor, width/2,0f,0f)
                .setBottomSideLine(true,dividerColor,width,0f,0f).create()
    }
}

class RecyclerViewDivider5(context: Context , @ColorInt var dividerColor: Int ,var width: Float ):Y_DividerItemDecoration(context){
    override fun getDivider(itemPosition: Int): Y_Divider {
        if(itemPosition % 3 == 0 || itemPosition % 3==1)
            return Y_DividerBuilder()
                .setRightSideLine(true, dividerColor , width , 0f,0f )
                    .setBottomSideLine(true, dividerColor, width,0f,0f)
                .create()
        else
            return Y_DividerBuilder()
                    .setBottomSideLine(true,dividerColor,width,0f,0f)
                    .create()
    }
}


