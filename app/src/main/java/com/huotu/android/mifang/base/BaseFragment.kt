package com.huotu.android.mifang.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.huotu.android.mifang.activity.LoginRegisterActivity
import com.huotu.android.mifang.activity.SplashActivity
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.ApiResultCodeEnum
import com.huotu.android.mifang.mvp.IView
import com.huotu.android.mifang.newIntent
import com.trello.rxlifecycle2.components.support.RxFragment


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BaseFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
abstract class BaseFragment<T> : RxFragment() , IView<T>  {

    var isViewPrepared :Boolean = false
    var isVisibleToUser = false
    var isDataInited = false
    var rootView :View?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        if(rootView !=null){
            if(rootView!!.parent!=null) {
                var parent: ViewGroup = rootView!!.parent as ViewGroup
                if (parent != null) {
                    parent.removeView(rootView)
                }
            }
            return rootView
        }


        rootView = inflater.inflate(getLayoutResourceId(), container , false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isViewPrepared) return
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewPrepared=true
        lazyLoadData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        lazyLoadData()
    }

    fun lazyLoadData(){

        if(isViewPrepared && isVisibleToUser && !isDataInited){
            fetchData()
            isDataInited=true
        }
    }

    abstract fun initView()

    abstract fun fetchData()

    abstract fun getLayoutResourceId():Int

    //abstract fun getPageTitle():String?

    override fun showProgress(msg: String) {

    }

    override fun hideProgress() {

    }

    override fun toast(msg: String) {
        Toast.makeText(context ,msg,Toast.LENGTH_LONG).show()
    }

    override fun error(err: String) {
        toast(err)
    }

    protected fun processCommonErrorCode(apiResult: ApiResult<*>): Boolean {
        if ( apiResult.code == ApiResultCodeEnum.USER_NO_LOGIN.code
            || apiResult.code == ApiResultCodeEnum.USER_FREEZE.code
            || apiResult.code == ApiResultCodeEnum.USER_ILLEGAL.code ) {

            toast( apiResult.msg )

            //EventBus.getDefault().post(LogoutEvent())

            newIntent<SplashActivity>()
            return true
        }
        return false
    }


}
