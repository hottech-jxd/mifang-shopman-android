package com.huotu.android.mifang.mvp.presenter

import android.provider.ContactsContract
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.MaterialCategory
import com.huotu.android.mifang.bean.Quan
import com.huotu.android.mifang.mvp.contract.LoginContract
import com.huotu.android.mifang.mvp.contract.QuanContract
import com.huotu.android.mifang.mvp.model.LoginModel
import com.huotu.android.mifang.mvp.model.QuanModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class QuanPresenter(view: QuanContract.View) : QuanContract.Presenter{

    var mView: QuanContract.View?=null
    val mModel: QuanModel by lazy { QuanModel() }

    init {
        mView=view
    }

    override fun materialCategprys() {

        val observable: Observable<ApiResult<List<MaterialCategory>>>? = mModel.materialCategprys()
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<List<MaterialCategory>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<List<MaterialCategory>>) {
                        mView!!.materialCategprysCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun materialList(typeId: Int, pageIndex: Int) {
        val observable: Observable<ApiResult<List<Quan>>>? = mModel.materialList(typeId, pageIndex)
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<List<Quan>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<List<Quan>>) {
                        mView!!.materialListCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun shareSuccess(dataId: Long) {
        val observable: Observable<ApiResult<Any>>? = mModel.shareSuccess(dataId)
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
                        mView!!.shareSuccessCallback(t)
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