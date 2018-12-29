package com.huotu.android.mifang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.MainFragmentAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.fragment.*
import com.huotu.android.mifang.mvp.IPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_menu.*

class MainActivity : AppCompatActivity() , View.OnClickListener , ViewPager.OnPageChangeListener{
    var fragments = ArrayList<BaseFragment<IPresenter>>()
    var fragmentAdapter : MainFragmentAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragments()
    }
    private fun initFragments(){

        fragments.clear()
        fragments.add(QuanFragment.newInstance())
        fragments.add(KnowledgeFragment.newInstance())
        fragments.add(PromotionFragment.newInstance())
        fragments.add(MyFragment.newInstance())

        fragmentAdapter = MainFragmentAdapter(supportFragmentManager , fragments )
        main_viewPager.adapter = fragmentAdapter
        main_viewPager.addOnPageChangeListener(this)
        //main_tab.setupWithViewPager(main_viewPager,true)
        //main_tab.addOnTabSelectedListener(this)

    }

    override fun onClick(v: View?) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        changeMenuIcon(position)
    }

    private fun changeMenuIcon(index:Int) {

//        bottom_index_image.setImageResource(if (index == 0) R.mipmap.home2 else R.mipmap.home)
//        bottom_index_title.setTextColor(if(index==0) ContextCompat.getColor (this , R.color.textcolor) else ContextCompat.getColor(this , R.color.textcolor2 ))
//        bottom_benefit_image.setImageResource(if (index == 1) R.mipmap.benefit2 else R.mipmap.benefit)
//        bottom_benefit_title.setTextColor( if (index ==1) ContextCompat.getColor(this , R.color.textcolor) else ContextCompat.getColor(this, R.color.textcolor2) )
//        bottom_quan_image.setImageResource(if(index==2) R.mipmap.quan2 else R.mipmap.quan)
//        bottom_quan_title.setTextColor( if (index ==2) ContextCompat.getColor(this , R.color.textcolor) else ContextCompat.getColor(this, R.color.textcolor2) )
//        bottom_my_image.setImageResource(if(index==3)R.mipmap.my2 else  R.mipmap.my)
//        bottom_my_title.setTextColor( if (index ==3) ContextCompat.getColor(this , R.color.textcolor) else ContextCompat.getColor(this, R.color.textcolor2) )
    }
}
