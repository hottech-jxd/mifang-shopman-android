package com.huotu.android.mifang.mvp.model


import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.http.RetrofitManager
import io.reactivex.Observable

class SplashModel {

    fun initData():Observable<ApiResult<InitDataBean>> {
        val apiService = RetrofitManager.getApiService()
        val observable = apiService!!.init()
        return observable
    }


    fun getWechatAccessToken( appid :String, appsecret:String , code:String):Observable<WechatAccessToken>{
        val apiService = RetrofitManager.getWechatService()
        val observable = apiService!!.getAccessToken(appid , appsecret , code )
        return observable
    }

    fun getWechatUser( openid :String, accessToken: String ):Observable<WechatUser>{
        val apiService = RetrofitManager.getWechatService()
        val observable = apiService!!.getUserInfo( accessToken ,openid )
        return observable
    }

    fun loginByUnionId( openid :String,  unionid :String,  nickname :String,  userhead :String):Observable<ApiResult<UserBean>>{
        val apiService = RetrofitManager.getApiService()
        val observable = apiService!!.loginByUnionId(openid,unionid,nickname,userhead)
        return observable
    }

//    fun readCityData():Observable<ArrayList<Province>> {
//        val observable = Observable.create(ObservableOnSubscribe<ArrayList<Province>> { e ->
//            e.onNext(getCityData())
//            e.onComplete()
//        })
//
//        return observable
//    }

//    private fun getCityData(): ArrayList<Province> {
//        val json = AssetsUtils.readAddress("citydata.json")
//        var addressBeanList: List<AddressBean>? = ArrayList<AddressBean>()
//        val type = object : TypeToken<List<AddressBean>>() {
//
//        }.type
//        addressBeanList = GsonUtils.gson!!.fromJson(json, type)
//
//        val citys = ArrayList<Province>()
//        if (addressBeanList != null) {
//            for (bean in addressBeanList!!) {
//                val province = Province()
//                province.areaId = bean.value
//                province.areaName = bean.text
//                setCity(bean.children!!, province)
//                citys.add(province)
//            }
//        }
//        return citys
//    }

//    protected fun setCity(addressBeans: List<AddressBean>, province: Province) {
//        val citys = ArrayList<City>()
//        for (bean in addressBeans) {
//            val city = City()
//            city.areaId = bean.value
//            city.areaName = bean.text
//            setArea(bean.children!! , city)
//            citys.add(city)
//        }
//        province.cities = citys
//    }

//    protected fun setArea(areas: List<AddressBean>, city: City) {
//        val counties = ArrayList<County>()
//        for (bean in areas) {
//            val county = County()
//            county.cityId = city.areaId
//            county.areaId = bean.value
//            county.areaName = bean.text
//            counties.add(county)
//        }
//        city.counties = counties
//    }

}