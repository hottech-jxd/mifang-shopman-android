package com.huotu.android.mifang.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.FragmentAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.mvp.IPresenter
import kotlinx.android.synthetic.main.fragment_quan.*


/**
 * A simple [Fragment] subclass.
 * Use the [QuanFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class QuanFragment : BaseFragment<IPresenter>() {

    var fragments=ArrayList<BaseFragment<IPresenter>>()
    var fragmentAdapter : FragmentAdapter?=null
    var category=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun initView() {
        category.clear()
        category.add( "0" )
        category.add("1")
        category.add("2")
        category.add("3")
        fragments.clear()
        fragments.add(QuanTabFragment.newInstance(0))
        fragments.add(QuanTabFragment.newInstance(1))
        fragments.add(QuanTabFragment.newInstance(2))
        fragments.add(QuanTabFragment.newInstance(3))
        fragmentAdapter= FragmentAdapter(childFragmentManager,fragments , category)
        quan_viewPager.adapter=fragmentAdapter
        quan_tablayout.setupWithViewPager(quan_viewPager,true)
    }

    override fun fetchData() {
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_quan
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                QuanFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
