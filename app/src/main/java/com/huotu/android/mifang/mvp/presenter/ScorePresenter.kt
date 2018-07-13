package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.QuanContract
import com.huotu.android.mifang.mvp.contract.ScoreContract
import com.huotu.android.mifang.mvp.model.QuanModel
import com.huotu.android.mifang.mvp.model.ScoreModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ScorePresenter(view: ScoreContract.View) : ScoreContract.Presenter {

    var mView: ScoreContract.View? = null
    val mModel: ScoreModel by lazy { ScoreModel() }

    init {
        mView = view
    }

    override fun getIntegralList(searchType: Int, pageIndex: Int, pageSize: Int) {

        val observable: Observable<ApiResult<ScoreBean>>? = mModel.getIntegralList(searchType, pageIndex, pageSize)
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<ScoreBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ScoreBean>) {
                        mView!!.getIntegralListCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun getMiBeanList(pageIndex: Int, pageSize: Int) {
        val observable: Observable<ApiResult<MiBean>>? = mModel.getMiBeanList( pageIndex, pageSize)
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<MiBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<MiBean>) {
                        mView!!.getMiBeanListCallback(t)
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