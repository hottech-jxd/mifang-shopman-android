package com.huotu.android.mifang.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.MessagePageAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.MessageTypeEnum
import com.huotu.android.mifang.fragment.MessageFragment
import com.huotu.android.mifang.mvp.IPresenter
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.layout_header.*

class MessageActivity : BaseActivity<IPresenter>(),View.OnClickListener , ViewPager.OnPageChangeListener,TabLayout.OnTabSelectedListener {
    var messagePageAdapter: MessagePageAdapter?=null
    private var fragments=ArrayList<MessageFragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        initView()
    }

    private fun initView(){

        header_title.text="消息动态"
        header_left_image.setOnClickListener(this)

        fragments.add(MessageFragment.newInstance(MessageTypeEnum.All.id))
        //fragments.add(MessageFragment.newInstance(MessageTypeEnum.MallMessage.id))
        fragments.add(MessageFragment.newInstance(MessageTypeEnum.SystemMessage.id))
        fragments.add(MessageFragment.newInstance(MessageTypeEnum.RegisterMessage.id))
        fragments.add(MessageFragment.newInstance(MessageTypeEnum.PayMessage.id))
        var titles = ArrayList<String>()
        titles.add("全部")
        //titles.add("商城通知")
        titles.add("系统通知")
        titles.add("注册通知")
        titles.add("支付通知")
        messagePageAdapter = MessagePageAdapter(supportFragmentManager , fragments, titles)

        message_viewPager.adapter = messagePageAdapter
        message_viewPager.addOnPageChangeListener(this)
        message_tablayout.setupWithViewPager(message_viewPager,true)

        message_tablayout.addOnTabSelectedListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                finish()
            }
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
//        if(tab!!.position==0){
//            message_index1.visibility=View.VISIBLE
//            message_index2.visibility = View.INVISIBLE
//        }else{
//            message_index1.visibility=View.INVISIBLE
//            message_index2.visibility = View.VISIBLE
//        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
//        if(position==0){
//            message_index1.visibility=View.VISIBLE
//            message_index2.visibility = View.INVISIBLE
//        }else{
//            message_index1.visibility=View.INVISIBLE
//            message_index2.visibility = View.VISIBLE
//        }
    }
}
