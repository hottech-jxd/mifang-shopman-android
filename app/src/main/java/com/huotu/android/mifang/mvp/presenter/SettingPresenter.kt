package com.huotu.android.mifang.mvp.presenter

import android.os.Build
import com.huotu.android.mifang.base.BaseApplication
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.*
import com.huotu.android.mifang.mvp.model.*
import com.huotu.android.mifang.util.SignUtils
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.BuildConfig
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import java.util.HashMap

class SettingPresenter(view: SettingContract.View): SettingContract.Presenter {

    var mView: SettingContract.View?=null
    private val mModel: SettingModel by lazy { SettingModel() }

    init {
        mView=view
    }

    override fun shopperSetting(type: Int, content: String) {
        val observable = mModel.shopperSeting(type , content )

        observable.subscribeOn(Schedulers.io())
                .bindToLifecycle(mView as LifecycleProvider<*>)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ApiResult<Any>>{
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<Any>) {

                        mView!!.shopperSettingCallback( t )

                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun uploadLogo(type: Int, byteArray: ByteArray) {

        val requestBodyMap = HashMap<String, RequestBody>()
        val maps = HashMap<String, String?>()


//                .addHeader("appVersion" , headerParameter.appVersion )
//                .addHeader("hwid" , headerParameter.hwid )
//                .addHeader("mobileType" , headerParameter.mobileType )
//                .addHeader("osType",headerParameter.osType.toString())
//                .addHeader("osVersion" , headerParameter.osVersion)
//                .addHeader("userId" , headerParameter.userId.toString())
//                .addHeader("userToken" , headerParameter.userToken )
//                .addHeader("customerId", headerParameter.customerId.toString())


        val userid = if (BaseApplication.instance!!.variable.userBean == null) "0" else BaseApplication.instance!!.variable.userBean!!.userId.toString()
        val userToken = if (BaseApplication.instance!!.variable.userBean == null) "" else BaseApplication.instance!!.variable.userBean!!.token

        maps.put("customerId", Constants.CUSTOMERID.toString())
        maps.put("userId", userid)
        maps.put("userToken", userToken)
        //maps.put("appVersion", BuildConfig.VERSION_CODE.toString())
        //maps.put("hwid",  Build.ID)
        //maps.put("osType", Constants.OS_TYPE.toString())
        //maps.put("osVersion", Build.VERSION.SDK_INT.toString())
        maps.put("type", type.toString())
        val timestamp = System.currentTimeMillis()
        maps.put("timestamp", timestamp.toString())


        var requestBody = RequestBody.create(MediaType.parse("image/*"), byteArray)
        requestBodyMap["img\"; filename=\"logo.png"] = requestBody

        val sign = SignUtils.getInstance().sign(maps, Constants.APP_SECRET)
        requestBody = RequestBody.create(MediaType.parse("text/plain"), sign)
        requestBodyMap["sign"] = requestBody

        requestBody = RequestBody.create(MediaType.parse("text/plain"), timestamp.toString())
        requestBodyMap["timestamp"] = requestBody

        val observable = mModel.uploadLogo(requestBodyMap)

        observable.subscribeOn(Schedulers.io())
                .bindToLifecycle(mView as LifecycleProvider<*>)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ApiResult<Map<String, String>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<Map<String, String>>) {

                        mView!!.uploadLogoCallback(t)

                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun getStoreInfo() {
        val observable = mModel.getStoreInfo()

        observable.subscribeOn(Schedulers.io())
                .bindToLifecycle(mView as LifecycleProvider<*>)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ApiResult<ShopperInfo>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ShopperInfo>) {

                        mView!!.getStoreInfo(t)

                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun onDestory() {

    }
}