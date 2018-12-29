package com.huotu.android.mifang.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huotu.android.mifang.R
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.MessageTypeEnum
import com.huotu.android.mifang.mvp.IPresenter


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShopperClass1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ShopperClass1Fragment : BaseFragment<IPresenter>() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var type: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt(ARG_TYPE , 0)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopper_class1, container, false)
    }


    override fun initView() {

    }

    override fun fetchData() {

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
