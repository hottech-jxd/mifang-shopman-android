package com.huotu.android.mifang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.mvp.IPresenter



/**
 * A simple [Fragment] subclass.
 * Use the [MyShopperFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MyShopperFragment : BaseFragment<IPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun initView() {

    }

    override fun fetchData() {

    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_my_shopper
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyShopperFragment.
         */
        @JvmStatic
        fun newInstance() =
                MyShopperFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
