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
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.ClassTypeEnum
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.MessageTypeEnum
import com.huotu.android.mifang.bean.OrderStatusEnum
import com.huotu.android.mifang.fragment.MessageFragment
import com.huotu.android.mifang.fragment.OrderFragment
import com.huotu.android.mifang.fragment.ShopperClass1Fragment
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.OrderContract
import com.huotu.android.mifang.widget.DateDialog
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.activity_shopper_class.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class OrderActivity : BaseActivity<IPresenter>()
        , TabLayout.OnTabSelectedListener
        , DateDialog.OnOperateListener
        , ViewPager.OnPageChangeListener
        , View.OnClickListener {
    var commonPageAdapter: CommonPageAdapter?=null
    private var fragments=ArrayList<BaseFragment<IPresenter>>()
    private var searchYear:Int=0
    private var searchMonth:Int=0
    private var searchDay :Int=0
    private var listener = ArrayList<OrderFilterListener>()
    private var orderSourceType = -1 /*订单来源类型,默认-1，主要用于查看邀请的营养师订单，传入100 */
    private var dateDialog:DateDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        initView()
    }

    private fun initView(){

        header_left_image.setOnClickListener(this)
        order_time.setOnClickListener(this)

        if(intent.hasExtra(Constants.INTENT_ORDER_SOURCE)){
            orderSourceType = intent.getIntExtra(Constants.INTENT_ORDER_SOURCE, -1)
        }

        searchYear = Calendar.getInstance().get(Calendar.YEAR)
        searchMonth = Calendar.getInstance().get(Calendar.MONTH)+1
        searchDay = -1//Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        fragments.add(OrderFragment.newInstance(OrderStatusEnum.All.id , searchYear, searchMonth , searchDay , orderSourceType) as BaseFragment<IPresenter>)
        fragments.add(OrderFragment.newInstance(OrderStatusEnum.Delivery.id, searchYear, searchMonth , searchDay , orderSourceType) as BaseFragment<IPresenter>)
        fragments.add(OrderFragment.newInstance(OrderStatusEnum.Receive.id, searchYear, searchMonth , searchDay , orderSourceType) as BaseFragment<IPresenter>)
        fragments.add(OrderFragment.newInstance(OrderStatusEnum.Finish.id, searchYear, searchMonth , searchDay , orderSourceType ) as BaseFragment<IPresenter>)
        fragments.add(OrderFragment.newInstance(OrderStatusEnum.Back.id, searchYear, searchMonth , searchDay , orderSourceType) as BaseFragment<IPresenter>)
        val titles = ArrayList<String>()
        titles.add(OrderStatusEnum.All.desc)
        titles.add(OrderStatusEnum.Delivery.desc)
        titles.add(OrderStatusEnum.Receive.desc)
        titles.add(OrderStatusEnum.Finish.desc)
        titles.add(OrderStatusEnum.Back.desc)

        listener.addAll(fragments as ArrayList<OrderFilterListener>)

        commonPageAdapter = CommonPageAdapter(supportFragmentManager , fragments, titles)

        order_viewPager.adapter = commonPageAdapter
        order_viewPager.addOnPageChangeListener(this)
        order_tablayout.setupWithViewPager(order_viewPager,true)
        order_tablayout.addOnTabSelectedListener(this)



        operate( searchYear , searchMonth , searchDay)

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
        if(dateDialog==null) {
            dateDialog = DateDialog(this, this)
        }
        dateDialog!!.show( searchYear , searchMonth , searchDay , true, false )
    }

    override fun operate(year: Int, month: Int, day: Int) {
        searchYear = year
        searchMonth = month
        searchDay = -1 // day
        order_time.text =  year.toString() + "年" + month + "月"

        for( item in listener) {
            item.filter(searchYear , searchMonth, searchDay )
        }
    }


    interface OrderFilterListener{
        fun filter(year:Int,month:Int,day:Int)
    }

//    override fun onItemSelected(picker: WheelPicker?, data: Any?, position: Int) {
//       if(data.toString().toInt() == 2018) {
//           var months = ArrayList<Int>(7)
//           months[0]=6
//           months[1]=7
//           months[2]=8
//           months[3]=9
//           months[4]=10
//           months[5]=11
//           months[6]=12
//
//           order_select_month.data =months
//       }else{
//           var months = ArrayList<Int>(12)
//           months[0]=1
//           months[1]=2
//           months[2]=3
//           months[3]=4
//           months[4]=5
//           months[5]=6
//           months[6]=7
//           months[7]=8
//           months[8]=9
//           months[9]=10
//           months[10]=11
//           months[11]=12
//           order_select_month.data =months
//       }
//    }
}
