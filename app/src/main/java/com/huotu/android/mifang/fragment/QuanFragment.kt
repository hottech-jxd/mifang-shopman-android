package com.huotu.android.mifang.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.FragmentAdapter
import com.huotu.android.mifang.base.BaseFragment
import com.huotu.android.mifang.bean.ApiResult
import com.huotu.android.mifang.bean.ApiResultCodeEnum
import com.huotu.android.mifang.bean.MaterialCategory
import com.huotu.android.mifang.bean.Quan
import com.huotu.android.mifang.mvp.IPresenter
import com.huotu.android.mifang.mvp.contract.QuanContract
import com.huotu.android.mifang.mvp.presenter.QuanPresenter
import kotlinx.android.synthetic.main.fragment_quan.*
import kotlinx.android.synthetic.main.layout_header.*


/**
 * A simple [Fragment] subclass.
 * Use the [QuanFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class QuanFragment : BaseFragment<QuanContract.Presenter>() , QuanContract.View {

    var fragments=ArrayList<BaseFragment<IPresenter>>()
    var fragmentAdapter : FragmentAdapter?=null
    var category:List<MaterialCategory>?=null
    var iPresenter=QuanPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun initView() {
        header_left_image.visibility=View.GONE
        header_title.text="首页"
        quan_viewPager.offscreenPageLimit=3
    }

    override fun fetchData() {
        iPresenter.materialCategprys()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_quan
    }

    override fun materialCategprysCallback(apiResult: ApiResult<List<MaterialCategory>>) {
        if(processCommonErrorCode(apiResult)){
            return
        }
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }
        if(apiResult.data==null){
            toast("素材分类个数0异常")
            return
        }
        category = apiResult.data
        fragments.clear()
        for(i in 0 until category!!.size){
            fragments.add( QuanTabFragment.newInstance( category!![i].typeId ) as BaseFragment<IPresenter> )
        }
        fragmentAdapter = FragmentAdapter(childFragmentManager , fragments , category!!)
        quan_viewPager.adapter = fragmentAdapter
        quan_tablayout.setupWithViewPager(quan_viewPager ,true)
    }

    override fun materialListCallback(apiResult: ApiResult<List<Quan>>) {

    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        quan_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        quan_progress.visibility=View.GONE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() :QuanFragment =
                QuanFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
