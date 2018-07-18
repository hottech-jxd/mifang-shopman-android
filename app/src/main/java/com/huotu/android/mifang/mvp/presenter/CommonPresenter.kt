package com.huotu.android.mifang.mvp.presenter

import com.google.gson.reflect.TypeToken
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.AppVersionBean
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.ProvinceCityArea
import com.huotu.android.mifang.mvp.contract.CommonContract
import com.huotu.android.mifang.mvp.model.CommonModel
import com.huotu.android.mifang.util.AssetsUtils
import com.huotu.android.mifang.util.GsonUtils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class CommonPresenter (view: CommonContract.View): CommonContract.Presenter {

    var mView: CommonContract.View?=null
    val mModel: CommonModel by lazy { CommonModel() }

    init {
        mView=view
    }

    override fun checkAppVersion(appType: String, version: String) {


        val observable : Observable<ApiResult<AppVersionBean>>? = mModel.checkAppVersion(appType , version )
        observable?.subscribeOn(Schedulers.io())
                //?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<AppVersionBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<AppVersionBean>) {
                        mView!!.checkAppVersionCallback( t )
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                } )
    }

    override fun feedback(submitType: Int, memo: String, mobile: String) {
        val observable : Observable<ApiResult<Any>>? = mModel.feedback(submitType , memo  , mobile )
        observable?.subscribeOn(Schedulers.io())
                //?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<Any>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<Any>) {
                        mView!!.feedbackCallback( t )
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                } )
    }

    override fun getProvinceCityArea() {
        var observable = Observable.create<ArrayList<ProvinceCityArea>> { e ->
            e.onNext(getProvinceCityAreaData())
            e.onComplete()
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( object :Observer<ArrayList<ProvinceCityArea>>{
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ArrayList<ProvinceCityArea>) {
                        mView!!.getProvinceCityAreaCallback( t )
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })

    }

    private fun getProvinceCityAreaData(): ArrayList<ProvinceCityArea> {
        val json = AssetsUtils.readAddress("citydata.json")
        var addressBeanList  = ArrayList<ProvinceCityArea>()
        val type = object : TypeToken<List<ProvinceCityArea>>() {
        }.type
        addressBeanList = GsonUtils.gson!!.fromJson(json, type)

        return addressBeanList
    }

    override fun onDestory() {

    }
}