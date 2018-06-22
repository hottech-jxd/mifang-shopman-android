package com.huotu.android.mifang.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.aigestudio.wheelpicker.WheelPicker
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.CommonPageAdapter
import com.huotu.android.mifang.adapter.MessagePageAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.ClassTypeEnum
import com.huotu.android.mifang.bean.MessageTypeEnum
import com.huotu.android.mifang.bean.OrderStatusEnum
import com.huotu.android.mifang.fragment.MessageFragment
import com.huotu.android.mifang.fragment.OrderFragment
import com.huotu.android.mifang.fragment.ShopperClass1Fragment
import com.huotu.android.mifang.mvp.IPresenter
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_shopper_class.*

class OrderActivity : BaseActivity<IPresenter>() ,TabLayout.OnTabSelectedListener ,
        ViewPager.OnPageChangeListener, View.OnClickListener, WheelPicker.OnItemSelectedListener{
    var commonPageAdapter: CommonPageAdapter?=null
    private var fragments=ArrayList<OrderFragment>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        initView()
    }

    private fun initView(){


        header_left_image.setOnClickListener(this)
        order_time.setOnClickListener(this)

        fragments.add(OrderFragment.newInstance(OrderStatusEnum.All.id))
        fragments.add(OrderFragment.newInstance(OrderStatusEnum.Delivery.id))
        fragments.add(OrderFragment.newInstance(OrderStatusEnum.Receive.id))
        fragments.add(OrderFragment.newInstance(OrderStatusEnum.Finish.id))
        fragments.add(OrderFragment.newInstance(OrderStatusEnum.Back.id))
        var titles = ArrayList<String>()
        titles.add(OrderStatusEnum.All.desc)
        titles.add(OrderStatusEnum.Delivery.desc)
        titles.add(OrderStatusEnum.Receive.desc)
        titles.add(OrderStatusEnum.Finish.desc)
        titles.add(OrderStatusEnum.Back.desc)

        commonPageAdapter = CommonPageAdapter(supportFragmentManager , fragments, titles)

        order_viewPager.adapter = commonPageAdapter
        order_viewPager.addOnPageChangeListener(this)
        order_tablayout.setupWithViewPager(order_viewPager,true)
        order_tablayout.addOnTabSelectedListener(this)


        order_select_year.setOnItemSelectedListener(this)
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
            R.id.order_time->{
                selectTime()
            }
        }
    }

    private fun selectTime(){

    }

    override fun onItemSelected(picker: WheelPicker?, data: Any?, position: Int) {
       if(data.toString().toInt() == 2018) {
           var months = ArrayList<Int>(7)
           months[0]=6
           months[1]=7
           months[2]=8
           months[3]=9
           months[4]=10
           months[5]=11
           months[6]=12

           order_select_month.data =months
       }else{
           var months = ArrayList<Int>(12)
           months[0]=1
           months[1]=2
           months[2]=3
           months[3]=4
           months[4]=5
           months[5]=6
           months[6]=7
           months[7]=8
           months[8]=9
           months[9]=10
           months[10]=11
           months[11]=12
           order_select_month.data =months
       }
    }
}
