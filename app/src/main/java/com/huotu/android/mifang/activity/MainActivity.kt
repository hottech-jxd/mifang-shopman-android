package com.huotu.android.mifang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.MainFragmentAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.KeyValue
import com.huotu.android.mifang.fragment.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.widget.MsgDialog
import com.huotu.android.mifang.widget.OperateDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_menu.*

class MainActivity : AppCompatActivity()
        , View.OnClickListener
        , ViewPager.OnPageChangeListener
        , OperateDialog.OnOperateListener {
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
        //fragments.add(KnowledgeFragment.newInstance())
        fragments.add(PromotionFragment.newInstance())
        fragments.add(MyFragment.newInstance())

        fragmentAdapter = MainFragmentAdapter(supportFragmentManager , fragments )
        main_viewPager.adapter = fragmentAdapter
        main_viewPager.addOnPageChangeListener(this)
        //main_tab.setupWithViewPager(main_viewPager,true)
        //main_tab.addOnTabSelectedListener(this)

        bottom_index.setOnClickListener(this)
        bottom_invite.setOnClickListener(this)
        bottom_my.setOnClickListener(this)

        openDialog()
    }


    private fun openDialog(){
        var msgDialog = MsgDialog(this,this)

        msgDialog.setSize(DensityUtils.getScreenWidth(this) *80/100, 0)
        msgDialog.setMaxHeight( DensityUtils.getScreenHeight(this)*2/3 )
        msgDialog.show()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.bottom_index->{
                main_viewPager.setCurrentItem(0,true)
            }
            R.id.bottom_invite->{
                main_viewPager.setCurrentItem(1,true)
            }
            R.id.bottom_my->{
                main_viewPager.setCurrentItem(2,true)
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        changeMenuIcon(position)
    }

    private fun changeMenuIcon(index:Int) {

        bottom_index_image.setImageResource(if (index == 0) R.mipmap.home_selected else R.mipmap.home)
        bottom_index_title.setTextColor(if(index==0) ContextCompat.getColor (this , R.color.textcolor2) else ContextCompat.getColor(this , R.color.textcolor ))
        //bottom_benefit_image.setImageResource(if (index == 1) R.mipmap.benefit2 else R.mipmap.benefit)
        //bottom_benefit_title.setTextColor( if (index ==1) ContextCompat.getColor(this , R.color.textcolor) else ContextCompat.getColor(this, R.color.textcolor2) )
        bottom_invite_image.setImageResource(if(index==1) R.mipmap.invite_selected else R.mipmap.invite)
        bottom_invite_title.setTextColor( if (index ==1) ContextCompat.getColor(this , R.color.textcolor2) else ContextCompat.getColor(this, R.color.textcolor) )
        bottom_my_image.setImageResource(if(index==2)R.mipmap.my_selected else  R.mipmap.my)
        bottom_my_title.setTextColor( if (index ==2) ContextCompat.getColor(this , R.color.textcolor2) else ContextCompat.getColor(this, R.color.textcolor) )
    }

    override fun operate(keyValue: KeyValue) {

    }
}
