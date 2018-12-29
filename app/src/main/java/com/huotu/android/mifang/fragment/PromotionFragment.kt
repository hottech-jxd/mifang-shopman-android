package com.huotu.android.mifang.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.FragmentAdapter
import com.huotu.android.mifang.adapter.PromotionAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.util.DensityUtils
import kotlinx.android.synthetic.main.fragment_promotion.*


/**
 * A simple [Fragment] subclass.
 * Use the [PromotionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class PromotionFragment : BaseFragment<IPresenter>() ,ViewPager.OnPageChangeListener{
    var fragmentAdapter:FragmentAdapter?=null
    var fragments=ArrayList<BaseFragment<IPresenter>>()
    var fragment1:Promotion1Fragment?=null
    var fragment2:Promotion2Fragment?=null
    var fragment3:Promotion3Fragment?=null

    var promotionAdapter:PromotionAdapter?=null
    var currentIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun initView() {
        var data=ArrayList<String>()
        data.add("http://image.tkcm888.com/adSet_2018-05-31_a13475823f524d5f8b3b9480673e339915277602221601122.png")
        data.add("http://image.tkcm888.com/adSet_2018-05-31_56440f86ea1d4d60a9a4d725e26e62c015277545962763144.png")
        data.add("http://image.tkcm888.com/adSet_2018-06-01_f406f8550f0f4b21b41fca881bbcb11415278577614883710.png")
        if(promotionAdapter==null){
            promotionAdapter=PromotionAdapter(data)
        }
        //promotion_banner.offscreenPageLimit=3
        //promotion_banner.pageMargin = DensityUtils.dip2px(this.context,10f)
        //promotion_banner.adapter = promotionAdapter


       fragments.clear()
        if(fragment1==null){
            fragment1 = Promotion1Fragment.newInstance("","")
        }
        if(fragment2==null){
            fragment2= Promotion2Fragment.newInstance("","")
        }
        if(fragment3==null){
            fragment3 = Promotion3Fragment.newInstance("","")
        }
        fragments.add(fragment1!!)
        fragments.add(fragment2!!)
        fragments.add(fragment3!!)
        var titles = ArrayList<String>()
        titles.add("1")
        titles.add("2")
        titles.add("3")
        if(fragmentAdapter==null){
            fragmentAdapter = FragmentAdapter(childFragmentManager, fragments,titles)
        }

        promotion_viewPager.adapter=fragmentAdapter
        promotion_banner.offscreenPageLimit=3
        promotion_banner.pageMargin = DensityUtils.dip2px(context , 10f)
        //promotion_banner.layoutManager=LinearLayoutManager(context , LinearLayoutManager.HORIZONTAL,false)
        promotion_banner.adapter = promotionAdapter
        promotion_banner.addOnPageChangeListener(this)

        promotion_viewPager.offscreenPageLimit=3
        promotion_viewPager.addOnPageChangeListener(this)

//        promotion_banner.addOnScrollListener(object :RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
////               var  firstVisibleItem = ( recyclerView!!.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
////               var lastVisibleItem = (recyclerView!!.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
//
//                 //mShouldReverseLayout = !(dx>0)
//                if(dx>0){
//                    mShouldReverseLayout = false
//                }else if(dx<0){
//                    mShouldReverseLayout=true
//                }
//
//
//
//            }
//
//            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                var layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
//                var first = layoutManager.findFirstVisibleItemPosition()
//                var firstVis = layoutManager.findFirstCompletelyVisibleItemPosition()
//
//                if(first<0) return
//
//                if(first == firstVis ){
//                    return
//                }
//
//                if(first != currentIndex){
//                    currentIndex = first
//                    //return
//                }
//
//
//                if(newState == SCROLL_STATE_IDLE) {
//                    currentIndex = if(mShouldReverseLayout) currentIndex-1 else currentIndex+1
//                    if(currentIndex<0){
//                        currentIndex =0
//                    }else if(currentIndex>= layoutManager.itemCount){
//                        currentIndex = layoutManager.itemCount-1
//                    }
//                    promotion_banner.smoothScrollToPosition(currentIndex)
//                }
//
//            }
//        })
    }

//    var mShouldReverseLayout=false

//      fun getCurrentPosition( layoutManager: LinearLayoutManager ):Int {
//          if ( layoutManager.itemCount == 0) return 0
//
//          var position = layoutManager.getCurrentPositionOffset()
//          //if (!mInfinite) return Math.abs(position)
//
//          position = if (!mShouldReverseLayout)
//          //take care of position = getItemCount()
//              if (position >= 0)
//                  position % layoutManager.itemCount
//              else
//                  layoutManager.itemCount + position % layoutManager.itemCount
//          else
//              (if (position > 0)
//                  layoutManager.itemCount - position % layoutManager.itemCount
//              else
//                  -position % layoutManager.itemCount)
//          return if (position == layoutManager.itemCount) 0 else position
//      }




    override fun fetchData() {

    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_promotion
    }

    /**
     * 其中arg0这个参数
     * 有三种状态（0，1，2）。arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
     * 当页面开始滑动的时候，三种状态的变化顺序为（1，2，0），演示如下：
     */
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
//        if(position== (promotionAdapter!!.count-1)){
//            var leftPad = DensityUtils.dip2px(context , 40f)
//            var rightPad = DensityUtils.dip2px(context,10f)
//            var topPad = promotion_banner.paddingTop
//            var bottomPad = promotion_banner.paddingBottom
//            promotion_banner.setPadding(leftPad,topPad ,rightPad ,bottomPad )
//        }else if(position == 0){

//            var leftPad = DensityUtils.dip2px(context , 10f)
//            var rightPad = DensityUtils.dip2px(context,40f)
//            var topPad = promotion_banner.paddingTop
//            var bottomPad = promotion_banner.paddingBottom
//            promotion_banner.setPadding(leftPad,topPad ,rightPad ,bottomPad )
//        }else {
//            var leftPad = DensityUtils.dip2px(context , 40f)
//            var rightPad = DensityUtils.dip2px(context,40f)
//            var topPad = promotion_banner.paddingTop
//            var bottomPad = promotion_banner.paddingBottom
//            promotion_banner.setPadding(leftPad,topPad ,rightPad ,bottomPad )
//            promotion_banner.invalidate()
//        }

        if(promotion_banner.currentItem != position) {
            promotion_banner.setCurrentItem(position, true)
        }
        if(promotion_viewPager.currentItem!=position) {
            promotion_viewPager.setCurrentItem(position, true)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PromotionFragment.
         */
        @JvmStatic
        fun newInstance() =
                PromotionFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
