package com.huotu.android.mifang.bean

import java.io.Serializable

data class PayAccount(var AccountId:Long,
                      var RealName:String,
                      var IsDefault : Int /*是否默认(1-是,0-否)*/,
                      var AccountInfo:String,
                      var AccountType:Int,
                      var checked:Boolean=false) :Serializable
