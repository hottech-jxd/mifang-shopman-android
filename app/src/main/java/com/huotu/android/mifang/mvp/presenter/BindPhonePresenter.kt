package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.MyWalletBean
import com.huotu.android.mifang.mvp.contract.BindPhoneContract
import com.huotu.android.mifang.mvp.contract.WalletContract
import com.huotu.android.mifang.mvp.model.BindPhoneModel
import com.huotu.android.mifang.mvp.model.WalletModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BindPhonePresenter(view: BindPhoneContract.View) :BindPhoneContract.Presenter {

    private var mView: BindPhoneContract.View? = null
    private val mModel: BindPhoneModel by lazy { BindPhoneModel() }

    init {
        mView = view
    }

    override fun bindPhone(phone: String, code: String) {
        val observable: Observable<ApiResult<Any>>? = mModel.updateMobile(phone, code)
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<Any>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<Any>) {
                        mView!!.bindPhoneCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }


    override fun sendCode(phone: String) {
        val observable: Observable<ApiResult<Any>>? = mModel.sendCode(phone)
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<Any>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<Any>) {
                        mView!!.sendCodeCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun updatePayPassword(payPassword: String) {
        val observable: Observable<ApiResult<Any>>? = mModel.updatePayPassword( payPassword )
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<Any>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<Any>) {
                        mView!!.updatePayPasswordCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun checkCode(phone: String, code: String) {
        val observable: Observable<ApiResult<Any>>? = mModel.checkCode( phone ,code )
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<Any>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<Any>) {
                        mView!!.checkCodeCallback(t)
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