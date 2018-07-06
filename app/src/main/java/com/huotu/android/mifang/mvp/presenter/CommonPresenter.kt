package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.AppVersionBean
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.mvp.contract.CommonContract
import com.huotu.android.mifang.mvp.model.CommonModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CommonPresenter (view: CommonContract.View): CommonContract.Presenter {

    var mView: CommonContract.View?=null
    val mModel: CommonModel by lazy { CommonModel() }

    init {
        mView=view
    }

    override fun checkAppVersion(appType: String, version: String) {


        val observable : Observable<ApiResult<AppVersionBean>>? = mModel.checkAppVersion(appType , version )
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
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

    override fun onDestory() {

    }
}