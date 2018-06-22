package com.huotu.android.mifang.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.OrderAdpter
import com.huotu.android.mifang.adapter.ShopperClassAdpter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.MessageTypeEnum
import com.huotu.android.mifang.bean.OrderBean
import com.huotu.android.mifang.bean.ShopperClassBean
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.fragment_shopper_class1.*

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class OrderFragment : BaseFragment<IPresenter>() {

    var orderAdpter:OrderAdpter?=null
    var data=ArrayList<OrderBean>()

    private var type: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt(ARG_TYPE , 0)

        }
    }


    override fun initView() {
        if( orderAdpter==null)
            orderAdpter= OrderAdpter(data)

        shopperclass_recyclerview.layoutManager=LinearLayoutManager(context)
        shopperclass_recyclerview.adapter=orderAdpter
        shopperclass_recyclerview.addItemDecoration(RecyclerViewDivider(context!!, ContextCompat.getColor(context!!, R.color.bg_line ) , 10f ))
    }

    override fun fetchData() {
        data.clear()
        for(i in 0 .. 10){
            data.add(OrderBean( "245664611","待发货",
                    "http://image.tkcm888.com/adSet_2018-06-04_d18eb67c0fbc43a398fc7c55f818122415281204839937212.png",
                    "分公司大法官受到犯规是大概", "256.25" , "2" ,"200", "1544.15","2018-08-25 14:00:00"))
        }

        orderAdpter!!.notifyDataSetChanged()

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
                OrderFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_TYPE, type )

                    }
                }
    }
}
