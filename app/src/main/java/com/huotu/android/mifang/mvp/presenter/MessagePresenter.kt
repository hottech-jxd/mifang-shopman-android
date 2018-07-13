package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.MessageBean
import com.huotu.android.mifang.bean.MyBean
import com.huotu.android.mifang.mvp.contract.MessageContract
import com.huotu.android.mifang.mvp.contract.MyContract
import com.huotu.android.mifang.mvp.model.MessageModel
import com.huotu.android.mifang.mvp.model.MyModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MessagePresenter(view: MessageContract.View): MessageContract.Presenter {

    var mView: MessageContract.View?=null
    val mModel: MessageModel by lazy { MessageModel() }

    init {
        mView=view
    }

    override fun getMessageList( type :Int , pageIndex: Int, pageSize: Int) {


        val observable : Observable<ApiResult<ArrayList<MessageBean>>>? = mModel.getMessageList( type , pageIndex , pageSize)
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<ArrayList<MessageBean>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ArrayList<MessageBean>>) {
                        mView!!.getMessageList( t )
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