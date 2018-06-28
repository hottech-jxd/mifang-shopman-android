package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.MyContract
import com.huotu.android.mifang.mvp.contract.WalletContract
import com.huotu.android.mifang.mvp.model.MyModel
import com.huotu.android.mifang.mvp.model.WalletModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WalletPresenter(view: WalletContract.View):WalletContract.Presenter {

    private var mView: WalletContract.View?=null
    private val mModel: WalletModel by lazy { WalletModel() }

    init {
        mView=view
    }

    override fun myWallet() {

        val observable : Observable<ApiResult<MyWalletBean>>? = mModel.myWallet()
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<MyWalletBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<MyWalletBean>) {
                        mView!!.myWalletCallback( t )
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