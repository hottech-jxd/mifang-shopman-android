package com.huotu.android.mifang.mvp.presenter


import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.MainContract
import com.huotu.android.mifang.mvp.model.MainModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(view: MainContract.View) :MainContract.Presenter{
        private var mView: MainContract.View?=null
        private val mModel: MainModel by lazy { MainModel() }

        init {
            mView=view
        }

    override fun getAd() {
        val observable : Observable<ApiResult<AdBean>> = mModel.getAd()
        observable?.subscribeOn(Schedulers.io())
                //?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<AdBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<AdBean>) {
                        mView!!.getAdCodeCallback( t )
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