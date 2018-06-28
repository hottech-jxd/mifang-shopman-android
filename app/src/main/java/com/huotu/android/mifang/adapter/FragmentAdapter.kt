package com.huotu.android.mifang.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.MaterialCategory
import com.huotu.android.mifang.mvp.IPresenter

class FragmentAdapter( fragmentManager :FragmentManager ,var fragments : List<BaseFragment<IPresenter>>,
                       var catagory : List<MaterialCategory> )
    :FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return catagory[position].title
    }
}