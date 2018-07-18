package com.huotu.android.mifang.mvp.presenter

import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.BuyContract
import com.huotu.android.mifang.mvp.contract.InviteContract
import com.huotu.android.mifang.mvp.contract.MyContract
import com.huotu.android.mifang.mvp.model.BuyModel
import com.huotu.android.mifang.mvp.model.CommonModel
import com.huotu.android.mifang.mvp.model.InviteModel
import com.huotu.android.mifang.mvp.model.MyModel
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BuyPresenter(view: BuyContract.View):BuyContract.Presenter {

    private var mView: BuyContract.View?=null
    private val mModel: BuyModel by lazy { BuyModel() }
    private val mCommonModel:CommonModel by lazy { CommonModel() }

    init {
        mView=view
    }

    override fun getBuyInfo() {

        val observable1 = mModel.getRenewGoods()
        val observable2 = mCommonModel.getPaymentItems()

        Observable.concat(observable1, observable2)
                .subscribeOn(Schedulers.io())
                .bindToLifecycle(mView as LifecycleProvider<*>)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ApiResult<*>>{
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<*>) {
                        if( t.data is GoodsBean){
                            mView!!.getRenewGoodsCallback(t as ApiResult<GoodsBean>)
                        }else{
                            mView!!.getPaymentItemsCallback( t as ApiResult<ArrayList<PaymentItem>>)
                        }
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                })
    }

    override fun getRenewGoods() {
        val observable : Observable<ApiResult<GoodsBean>>? = mModel.getRenewGoods()
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<GoodsBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<GoodsBean>) {
                        mView!!.getRenewGoodsCallback( t )
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                } )
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

    override fun submitOrder(quantity: Int, payType: Int) {
        val observable : Observable<ApiResult<InviteOrderBean>>? = mModel.submitInviteOrder(quantity, payType)
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<InviteOrderBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<InviteOrderBean>) {
                        mView!!.submitOrderCallback( t )
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                } )
    }

    override fun getAgentUpgradeGoods() {
        val observable : Observable<ApiResult<AgentUpgradeGoodsBean>>? = mModel.getAgentUpgradeGoods()
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<AgentUpgradeGoodsBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<AgentUpgradeGoodsBean>) {
                        mView!!.getAgentUpgradeGoodsCallback( t )
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                } )
    }

    override fun getAddressList() {
        val observable : Observable<ApiResult<ArrayList<AddressBean>>>? = mModel.getAddressList()
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<ArrayList<AddressBean>>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<ArrayList<AddressBean>>) {
                        mView!!.getAddressListCallback( t )
                    }

                    override fun onError(e: Throwable) {
                        mView!!.hideProgress()
                        mView!!.error(Constants.MESSAGE_ERROR)
                    }
                } )
    }


    override fun submitAgentUpgradeOrder(shipName: String, shipMobile: String, shipAddress: String,
                                         shipArea: String, shipAreaCode: String, payType: Int) {
        val observable : Observable<ApiResult<InviteOrderBean>>? = mModel.submitAgentUpgradeOrder(shipName,shipMobile
        ,shipAddress,shipArea,shipAreaCode,payType)
        observable?.subscribeOn(Schedulers.io())
                ?.bindToLifecycle(mView as LifecycleProvider<*>)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe( object : Observer<ApiResult<InviteOrderBean>> {
                    override fun onComplete() {
                        mView!!.hideProgress()
                    }

                    override fun onSubscribe(d: Disposable) {
                        mView!!.showProgress(Constants.TIP_LOADING)
                    }

                    override fun onNext(t: ApiResult<InviteOrderBean>) {
                        mView!!.submitAgentUpgradeOrderCallback( t )
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