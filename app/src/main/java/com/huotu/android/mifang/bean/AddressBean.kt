package com.huotu.android.mifang.bean

data class AddressBean (
       var pid:Int,//id
       var  name:String,//姓名
       var mobile:String,//手机号
        var province:String,//    省
        var city:String,//    市
        var county:String,//    县
    var address:String,//详细地址
        var provinceCode:String,//    省code
        var cityCode:String,//    市code
        var countyCode:String//    县code
){
    override fun toString(): String {
        return "$name  $mobile\n\n$province$city$county $address"
    }
}