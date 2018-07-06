package com.huotu.android.mifang.bean

data class ScoreBean (var SumImportIntegral :Int /*总支出*/, var SumExportIntegral:Int , var Items :ArrayList<ScoreDetail>? ){
    data class ScoreDetail(var ChangeIntegral:Int /*变动金额*/, var ChangeTime :String /*变动时间*/, var ChangeDesc:String/*描述*/)
}