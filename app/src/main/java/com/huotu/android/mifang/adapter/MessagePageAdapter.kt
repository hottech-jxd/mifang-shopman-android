package com.huotu.android.mifang.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.mvp.IPresenter


class MessagePageAdapter(fragmentManager : FragmentManager, var fragments : List<BaseFragment<IPresenter>> ,
                         var titles :ArrayList<String>) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view.equals( `object`)
//    }

    override fun getCount(): Int {
        return fragments.size
    }
}