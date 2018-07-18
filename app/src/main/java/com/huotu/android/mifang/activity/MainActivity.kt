package com.huotu.android.mifang.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.MainFragmentAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.bean.KeyValue
import com.huotu.android.mifang.fragment.*
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.receiver.PushProcess
import com.huotu.android.mifang.update.UpdateManager
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.widget.MsgDialog
import com.huotu.android.mifang.widget.OperateDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_menu.*

class MainActivity : BaseActivity<IPresenter>()
        , View.OnClickListener
        , ViewPager.OnPageChangeListener
        , OperateDialog.OnOperateListener<KeyValue> {
    var fragments = ArrayList<BaseFragment<IPresenter>>()
    private var fragmentAdapter : MainFragmentAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPush(intent)

        initFragments()

        checkAppVersion()
    }

    private fun checkAppVersion(){
        UpdateManager(this).check()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        initPush(intent)
    }

    /***
     * 初始化极光推送
     */
    private fun initPush(intent: Intent?) {
        if (null == intent || !intent.hasExtra(Constants.INTENT_PUSH_KEY)) return
        val bundle = intent.getBundleExtra(Constants.INTENT_PUSH_KEY) ?: return

        PushProcess.process(this, bundle)
    }


    private fun initFragments(){

        fragments.clear()
        fragments.add(QuanFragment.newInstance() as BaseFragment<IPresenter>)
        fragments.add(MyShopperFragment.newInstance() as BaseFragment<IPresenter>)
        fragments.add(PromotionFragment.newInstance() as BaseFragment<IPresenter>)
        fragments.add(MyFragment.newInstance() as BaseFragment<IPresenter>)

        fragmentAdapter = MainFragmentAdapter(supportFragmentManager , fragments )
        main_viewPager.adapter = fragmentAdapter
        main_viewPager.addOnPageChangeListener(this)
        main_viewPager.offscreenPageLimit=3
        //main_tab.setupWithViewPager(main_viewPager,true)
        //main_tab.addOnTabSelectedListener(this)

        bottom_index.setOnClickListener(this)
        bottom_invite.setOnClickListener(this)
        bottom_my.setOnClickListener(this)
        bottom_benefit.setOnClickListener(this)

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
                main_viewPager.setCurrentItem(2,true)
            }
            R.id.bottom_my->{
                main_viewPager.setCurrentItem(3,true)
            }
            R.id.bottom_benefit->{
                main_viewPager.setCurrentItem(1,true)
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
        bottom_benefit_image.setImageResource(if (index == 1) R.mipmap.zs_selected else R.mipmap.zs)
        bottom_benefit_title.setTextColor( if (index ==1) ContextCompat.getColor(this , R.color.textcolor2) else ContextCompat.getColor(this, R.color.textcolor) )
        bottom_invite_image.setImageResource(if(index==2) R.mipmap.invite_selected else R.mipmap.invite)
        bottom_invite_title.setTextColor( if (index ==2) ContextCompat.getColor(this , R.color.textcolor2) else ContextCompat.getColor(this, R.color.textcolor) )
        bottom_my_image.setImageResource(if(index==3)R.mipmap.my_selected else  R.mipmap.my)
        bottom_my_title.setTextColor( if (index ==3) ContextCompat.getColor(this , R.color.textcolor2) else ContextCompat.getColor(this, R.color.textcolor) )
    }

    override fun operate(keyValue: KeyValue) {

    }
}
