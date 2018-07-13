package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.InviteContract
import com.huotu.android.mifang.mvp.contract.MyContract
import com.huotu.android.mifang.mvp.model.InviteModel
import com.huotu.android.mifang.mvp.model.MyModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class InvitePresenter(view: InviteContract.View):InviteContract.Presenter {

    var mView: InviteContract.View?=null
    val mModel: InviteModel by lazy { InviteModel() }

    init {
        mView=view
    }

    override fun invite() {

        val observable : Observable<ApiResult<InviteBean>>? = mModel.invite()
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<InviteBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<InviteBean>) {
                        mView!!.inviteCallback( t )
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