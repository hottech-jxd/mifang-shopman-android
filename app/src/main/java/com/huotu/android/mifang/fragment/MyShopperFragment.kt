package com.huotu.android.mifang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.activity.SetShopperActivity
import com.huotu.android.mifang.adapter.ShopGoodsAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.GoodsBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.newIntent
import kotlinx.android.synthetic.main.fragment_my_shopper.*
import kotlinx.android.synthetic.main.layout_header_3.*
import java.math.BigDecimal


/**
 * A simple [Fragment] subclass.
 * Use the [MyShopperFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MyShopperFragment : BaseFragment<IPresenter>()
    , View.OnClickListener{

    var shopGoodsAdapter:ShopGoodsAdapter?=null
    var data=ArrayList<GoodsBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun initView() {
        header_left_image.setImageResource(R.mipmap.myshop)
        header_left_image.setOnClickListener(this)
        header_right_image.setImageResource(R.mipmap.three)
        header_right_image.setOnClickListener(this)

        myshopper_recyclerview.layoutManager = GridLayoutManager(context ,2)
        shopGoodsAdapter = ShopGoodsAdapter(data)
        myshopper_recyclerview.adapter = shopGoodsAdapter
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{
                newIntent<SetShopperActivity>()
            }
            R.id.header_right_image->{

            }
        }
    }

    override fun fetchData() {
        for(i in 0..10){
            data.add(GoodsBean("","", BigDecimal.ZERO))
        }

        shopGoodsAdapter!!.notifyDataSetChanged()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_my_shopper
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyShopperFragment.
         */
        @JvmStatic
        fun newInstance() =
                MyShopperFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
