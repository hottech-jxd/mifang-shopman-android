package com.huotu.android.mifang.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.math.BigDecimal

data class ProfitItemBean (
       var  ProfitIntegral	:BigDecimal	/*收益积分*/,
       var OrderNum	:Int/*订单数*/ ,
       var ProfitTime	:String	/*收益时间段*/,
       var SearchData	:Int/*(根据查询输出,当月第几天,当年第几周,当年第几月)*/
)

data class ProfitItemEntity(  var content1:String,
                              var content2:String,
                              var content3:String ,
                              var type:Int): MultiItemEntity{

    override fun getItemType(): Int {
        return type
    }
}