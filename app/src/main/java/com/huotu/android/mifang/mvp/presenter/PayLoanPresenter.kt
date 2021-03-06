package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.PayLoanContract
import com.huotu.android.mifang.mvp.contract.QuanContract
import com.huotu.android.mifang.mvp.contract.ScoreContract
import com.huotu.android.mifang.mvp.model.CommonModel
import com.huotu.android.mifang.mvp.model.PayLoanModel
import com.huotu.android.mifang.mvp.model.QuanModel
import com.huotu.android.mifang.mvp.model.ScoreModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PayLoanPresenter(view: PayLoanContract.View) : PayLoanContract.Presenter {

    var mView: PayLoanContract.View? = null
    val mModel: PayLoanModel by lazy { PayLoanModel() }
    val mCommonModel:CommonModel by lazy{ CommonModel()}

    init {
        mView = view
    }

    override fun getDepositList(pageIndex: Int, pageSize: Int) {

        val observable: Observable<ApiResult<ArrayList<PayLoanBean>>>? = mModel.getDepositList( pageIndex, pageSize)
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<ArrayList<PayLoanBean>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ArrayList<PayLoanBean>>) {
                        mView!!.getDepositListCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun getFrozenFlow(pageIndex: Int, pageSize: Int) {
        val observable: Observable<ApiResult<ArrayList<FrozenFlow>>>? = mModel.getFrozenFlow( pageIndex, pageSize)
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<ArrayList<FrozenFlow>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ArrayList<FrozenFlow>>) {
                        mView!!.getFrozenFlowCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun getDepositIndex() {
        val observable: Observable<ApiResult<DepositBean>>? = mModel.getDepositIndex()
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : Observer<ApiResult<DepositBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<DepositBean>) {
                        mView!!.getDepositIndexCallback(t)
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun getPaymentItems() {

        val observable : Observable<ApiResult<ArrayList<PaymentItem>>>? = mCommonModel.getPaymentItems()
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<ArrayList<PaymentItem>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ArrayList<PaymentItem>>) {
                        mView!!.getPaymentItemsCallback( t )
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                } )
    }

    override fun submitGoodsDepositOrder(payType: Int, goodsId: Long, productId: Long) {
        val observable : Observable<ApiResult<DepositOrderBean>>? = mModel.submitGoodsDepositOrder(payType,goodsId,productId)
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<DepositOrderBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<DepositOrderBean>) {
                        mView!!.submitGoodsDepositOrderCallback( t )
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