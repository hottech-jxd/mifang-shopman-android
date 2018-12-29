package com.huotu.android.mifang.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.huotu.android.mifang.R
import com.huotu.android.mifang.activity.*
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.Constants
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.newIntent
import com.huotu.android.mifang.newIntentForLogin
import kotlinx.android.synthetic.main.fragment_my.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MyFragment : BaseFragment<IPresenter>(),View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun initView() {
        my_lay_message.setOnClickListener(this)
        my_lay_feedback.setOnClickListener(this)
        my_lay_set.setOnClickListener(this)
        my_lay_shopper.setOnClickListener(this)
        my_lay_aboutme.setOnClickListener(this)
    }

    override fun fetchData() {

    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_my
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.my_lay_message->{
                newIntent<MessageActivity>()
            }
            R.id.my_lay_feedback->{
                newIntent<FeedbackActivity>()
            }
            R.id.my_lay_set->{
                newIntent<SettingActivity>()
            }
            R.id.my_lay_shopper->{
                newIntent<ShopperClassActivity>()
            }
            R.id.my_lay_aboutme->{
                newIntent<WebActivity>( Constants.INTENT_URL , "http://www.baidu.com")
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                MyFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
