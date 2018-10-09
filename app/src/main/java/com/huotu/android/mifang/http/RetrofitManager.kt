package com.huotu.android.mifang.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.huotu.android.mifang.BuildConfig
import com.huotu.android.mifang.bean.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {
    private var headerIntercepter:HeaderIntercepter?=null
    private var okHttpClient:OkHttpClient?=null
    private var gson : Gson?=null
    private var gsonConverterFactory : GsonConverterFactory?=null
    private var rxJava2CallAdapterFactory : RxJava2CallAdapterFactory?=null
    private var apiService : ApiService?=null
    private var retrofit:Retrofit?=null
    private var wechatRetrofit:Retrofit?=null
    private var wechatService:WechatService?=null

    private fun provideHeaderIntercepter(): HeaderIntercepter {
        if(headerIntercepter==null) headerIntercepter = HeaderIntercepter()
        return headerIntercepter!!
    }

    private fun provideOkHttpClient( headerIntercepter: HeaderIntercepter):OkHttpClient?{

        if(okHttpClient==null){
            var builder =OkHttpClient.Builder()
                .readTimeout(Constants.READ_TIMEOUT , TimeUnit.SECONDS)
                .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIMEOUT , TimeUnit.SECONDS)
                .addInterceptor(headerIntercepter)

            if(BuildConfig.DEBUG){
                builder.addInterceptor(provideHttpLogIntercepter())
            }

            okHttpClient =builder.build()
        }

        return okHttpClient
    }

    private fun provideHttpLogIntercepter():HttpLoggingInterceptor{
        var httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private fun provideGson(): Gson? {
        if(gson==null) gson = GsonBuilder().serializeNulls().create()
        return gson
    }

    private fun provideGsonConverter(gson: Gson?): GsonConverterFactory? {
        if(gsonConverterFactory==null ) gsonConverterFactory = GsonConverterFactory.create(gson)
        return gsonConverterFactory
    }

    private fun provideRxJava2CallAdapter(): RxJava2CallAdapterFactory? {
        if(rxJava2CallAdapterFactory==null) rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()
        return rxJava2CallAdapterFactory
    }

    private fun provideRetroft( baseUrl : String = Constants.BASE_URL , okHttpClient: OkHttpClient?,
                       gsonConverterFactory: GsonConverterFactory?,
                       rxJava2CallAdapterFactory: RxJava2CallAdapterFactory?): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build()
    }




    private fun getRetrofit():Retrofit?{
        if(retrofit==null) retrofit =  provideRetroft( Constants.BASE_URL , provideOkHttpClient(provideHeaderIntercepter()) ,
               provideGsonConverter( provideGson() ) ,
               provideRxJava2CallAdapter() )
        return retrofit
    }



    fun getApiService(): ApiService? {
        if(apiService==null) apiService = getRetrofit()!!.create(ApiService::class.java)
        return apiService
    }

    private fun getWechatRetroft():Retrofit?{
        if(wechatRetrofit==null) wechatRetrofit =  provideRetroft( "https://api.weixin.qq.com" , provideOkHttpClient(provideHeaderIntercepter()) ,
                provideGsonConverter( provideGson() ) ,
                provideRxJava2CallAdapter() )
        return wechatRetrofit
    }

    fun getWechatService():WechatService?{
        if(wechatService==null) wechatService= getWechatRetroft()!!.create(WechatService::class.java)
        return wechatService
    }

}