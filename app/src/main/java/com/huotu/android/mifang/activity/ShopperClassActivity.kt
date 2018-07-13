package com.huotu.android.mifang.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.CommonPageAdapter
import com.huotu.android.mifang.adapter.MessagePageAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.fragment.MessageFragment
import com.huotu.android.mifang.fragment.ShopperClass1Fragment
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.ShopperClassContract
import com.huotu.android.mifang.mvp.presenter.ShopperClassPresenter
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.activity_shopper_class.*
import kotlinx.android.synthetic.main.layout_header.*

class ShopperClassActivity : BaseActivity<ShopperClassContract.Presenter>()
        ,TabLayout.OnTabSelectedListener
        ,ShopperClassContract.View
        , ViewPager.OnPageChangeListener
        , View.OnClickListener{
    var commonPageAdapter: CommonPageAdapter?=null
    private var fragments=ArrayList<BaseFragment<IPresenter>>()
    private var iPresenter=ShopperClassPresenter(this)
    private var titles = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopper_class)

        initView()
    }

    private fun initView(){

        header_title.text="店主课堂"
        header_left_image.setOnClickListener(this)

//        fragments.add(ShopperClass1Fragment.newInstance(ClassTypeEnum.Base.id))
//        fragments.add(ShopperClass1Fragment.newInstance(ClassTypeEnum.Sale.id))
//        fragments.add(ShopperClass1Fragment.newInstance(ClassTypeEnum.ShopShare.id))
//        fragments.add(ShopperClass1Fragment.newInstance(ClassTypeEnum.Other.id))
        titles.clear()
        fragments.clear()
//        titles.add(ClassTypeEnum.Base.desc)
//        titles.add(ClassTypeEnum.Sale.desc)
//        titles.add(ClassTypeEnum.ShopShare.desc)
//        titles.add(ClassTypeEnum.Other.desc)
        commonPageAdapter = CommonPageAdapter(supportFragmentManager , fragments, titles)

        shopperclass_viewPager.adapter = commonPageAdapter
        shopperclass_viewPager.addOnPageChangeListener(this)
        shopperclass_tablayout.setupWithViewPager(shopperclass_viewPager,true)
        shopperclass_tablayout.addOnTabSelectedListener(this)

        iPresenter.getCategorys()
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        shopperclass_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        shopperclass_progress.visibility=View.GONE
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

    override fun getCategorysCallback(apiResult: ApiResult<ArrayList<ShopperClassCategoryBean>>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code!=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            return
        }
        fragments.clear()
        titles.clear()
        for(bean in apiResult.data!!){
            fragments.add( ShopperClass1Fragment.newInstance( bean.typeId ) as BaseFragment<IPresenter>)
            titles.add(bean.title)
        }
        commonPageAdapter!!.notifyDataSetChanged()

    }

    override fun getListCallback(apiResult: ApiResult<ArrayList<ShopperClassBean>>) {

    }
}
