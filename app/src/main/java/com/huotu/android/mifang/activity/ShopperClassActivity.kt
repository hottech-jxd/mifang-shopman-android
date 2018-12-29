package com.huotu.android.mifang.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.CommonPageAdapter
import com.huotu.android.mifang.adapter.MessagePageAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.ClassTypeEnum
import com.huotu.android.mifang.bean.MessageTypeEnum
import com.huotu.android.mifang.fragment.MessageFragment
import com.huotu.android.mifang.fragment.ShopperClass1Fragment
import com.huotu.android.mifang.mvp.IPresenter
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.activity_shopper_class.*
import kotlinx.android.synthetic.main.layout_header.*

class ShopperClassActivity : BaseActivity<IPresenter>() ,TabLayout.OnTabSelectedListener , ViewPager.OnPageChangeListener, View.OnClickListener{
    var commonPageAdapter: CommonPageAdapter?=null
    private var fragments=ArrayList<ShopperClass1Fragment>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopper_class)

        initView()
    }

    private fun initView(){

        header_title.text="店主课堂"
        header_left_image.setOnClickListener(this)

        fragments.add(ShopperClass1Fragment.newInstance(ClassTypeEnum.Base.id))
        fragments.add(ShopperClass1Fragment.newInstance(ClassTypeEnum.Sale.id))
        fragments.add(ShopperClass1Fragment.newInstance(ClassTypeEnum.ShopShare.id))
        fragments.add(ShopperClass1Fragment.newInstance(ClassTypeEnum.Other.id))
        var titles = ArrayList<String>()
        titles.add(ClassTypeEnum.Base.desc)
        titles.add(ClassTypeEnum.Sale.desc)
        titles.add(ClassTypeEnum.ShopShare.desc)
        titles.add(ClassTypeEnum.Other.desc)
        commonPageAdapter = CommonPageAdapter(supportFragmentManager , fragments, titles)

        shopperclass_viewPager.adapter = commonPageAdapter
        shopperclass_viewPager.addOnPageChangeListener(this)
        shopperclass_tablayout.setupWithViewPager(message_viewPager,true)
        shopperclass_tablayout.addOnTabSelectedListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
        }
    }
}
