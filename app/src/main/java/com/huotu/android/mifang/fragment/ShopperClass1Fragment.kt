package com.huotu.android.mifang.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.ShopperClassAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.ShopperClassBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_shopper_class1.*
/**
 * A simple [Fragment] subclass.
 * Use the [ShopperClass1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ShopperClass1Fragment : BaseFragment<IPresenter>() {

    var shopperClassAdapter:ShopperClassAdapter?=null
    var data=ArrayList<ShopperClassBean>()

    private var type: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt(ARG_TYPE , 0)

        }
    }


    override fun initView() {
        if( shopperClassAdapter==null)
            shopperClassAdapter= ShopperClassAdapter(data)

        shopperclass_recyclerview.layoutManager=LinearLayoutManager(context)
        shopperclass_recyclerview.addItemDecoration(RecyclerViewDivider(context!!, ContextCompat.getColor(context!!, R.color.bg_line ) , 1f ))
        shopperclass_recyclerview.adapter=shopperClassAdapter
    }

    override fun fetchData() {
        data.clear()
        for(i in 0 .. 10){
            data.add(ShopperClassBean("http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png","分公司大法官受到犯规是大概","大师飞阿是as阿是打发阿是打发dfa的萨芬士大夫打发是发沙士大夫撒飞洒发双方萨芬阿是打发"))
        }

        shopperClassAdapter!!.notifyDataSetChanged()

    }

    override fun getLayoutResourceId(): Int {
        return  R.layout.fragment_shopper_class1
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShopperClass1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(type:Int) =
                ShopperClass1Fragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_TYPE, type )

                    }
                }
    }
}
