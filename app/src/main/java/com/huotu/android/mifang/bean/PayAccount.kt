package com.huotu.android.mifang.bean

import java.io.Serializable

data class PayAccount(var AccountId:Long,
                      var RealName:String,
                      var IsDefault : Int /*是否默认(1-是,0-否)*/,
                      var AccountInfo:String,
                      var AccountType:Int, /*1支付宝 2 银行卡 4 微信零钱*/
                      var Account:String,
                      var checked:Boolean=false) :Serializable
