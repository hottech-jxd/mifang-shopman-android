package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.CashIndex
import com.huotu.android.mifang.bean.CashRecord
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.mvp.contract.BindPhoneContract
import com.huotu.android.mifang.mvp.contract.CashContract
import com.huotu.android.mifang.mvp.model.BindPhoneModel
import com.huotu.android.mifang.mvp.model.CashModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CashPresenter(view: CashContract.View) :CashContract.Presenter  {
    private var mView: CashContract.View? = null
    private val mModel: CashModel by lazy { CashModel() }

    init {
        mView = view
    }

    override fun cashList(pageIndex: Int ) {
        val observable: Observable<ApiResult<ArrayList<CashRecord>>>? = mModel.cashList( pageIndex, Constants.PAGE_SIZE )
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<ArrayList<CashRecord>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ArrayList<CashRecord>>) {
                        mView!!.cashListCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun cashIndex() {
        val observable: Observable<ApiResult<CashIndex>>? = mModel.cashIndex()
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<CashIndex>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<CashIndex>) {
                        mView!!.cashIndexCallback(t)
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