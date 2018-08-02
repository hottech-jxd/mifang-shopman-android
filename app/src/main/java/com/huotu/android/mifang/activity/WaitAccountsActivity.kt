package com.huotu.android.mifang.activity


import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.MiAdapter
import com.huotu.android.mifang.adapter.ScoreAdapter
import com.huotu.android.mifang.base.BaseActivity
import com.huotu.android.mifang.bean.*
import com.huotu.android.mifang.mvp.contract.ScoreContract
import com.huotu.android.mifang.mvp.presenter.ScorePresenter
import com.huotu.android.mifang.widget.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_wait_accounts.*
import kotlinx.android.synthetic.main.fragment_quan_tab.*
import kotlinx.android.synthetic.main.layout_header.*
import java.math.BigDecimal

/**
 * 待结算积分 , 余额，
 */
class WaitAccountsActivity : BaseActivity<ScoreContract.Presenter>()
        ,ScoreContract.View
        ,View.OnClickListener
        , SwipeRefreshLayout.OnRefreshListener
        , BaseQuickAdapter.RequestLoadMoreListener {
    var data= ScoreBean(0,0,null)
    var scoreAdapter:ScoreAdapter?=null
    var miAdapter:MiAdapter?=null
    var type = ScoreTypeEnum.WaitAccounts.id
    var iPresenter=ScorePresenter(this)
    var pageIndex=0
    var isShowProgress=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait_accounts)

        if(intent.hasExtra(Constants.INTENT_OPERATE_TYPE)){
            type = intent.getIntExtra(Constants.INTENT_OPERATE_TYPE,ScoreTypeEnum.WaitAccounts.id )
        }

        if(type == ScoreTypeEnum.WaitAccounts.id ){
            header_title.text= ScoreTypeEnum.WaitAccounts.desc
            wait_accounts_summary.visibility=View.VISIBLE
        }else if(type ==ScoreTypeEnum.MiBean.id){
            header_title.text=ScoreTypeEnum.MiBean.desc
            wait_accounts_summary.visibility=View.GONE
        }else if(type ==ScoreTypeEnum.Balance.id){
            header_title.text=ScoreTypeEnum.Balance.desc
            wait_accounts_summary.visibility=View.GONE
        }
        header_left_image.setOnClickListener(this)

        wait_accounts_refreshView.setOnRefreshListener(this)

        wait_accounts_recyclerview.layoutManager=LinearLayoutManager(this)
        wait_accounts_recyclerview.addItemDecoration(RecyclerViewDivider(this , ContextCompat.getColor(this, R.color.bg_line), 10f))



        if( type == ScoreTypeEnum.MiBean.id){
            miAdapter = MiAdapter(ArrayList())
            var emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty, null)
            miAdapter!!.emptyView = emptyView
            miAdapter!!.isUseEmpty(false)
            miAdapter!!.setOnLoadMoreListener(this, wait_accounts_recyclerview)
            wait_accounts_recyclerview.adapter = miAdapter
        }else {
            scoreAdapter = ScoreAdapter(ArrayList())
            var emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty, null)
            scoreAdapter!!.emptyView = emptyView
            scoreAdapter!!.isUseEmpty(false)
            scoreAdapter!!.setOnLoadMoreListener(this, wait_accounts_recyclerview)
            wait_accounts_recyclerview.adapter = scoreAdapter
        }

        getData()
    }

    private fun getData(){
        when(type){
            ScoreTypeEnum.Balance.id -> {
                iPresenter.getIntegralList(0, pageIndex+1, Constants.PAGE_SIZE)
            }
            ScoreTypeEnum.WaitAccounts.id->{
                iPresenter.getIntegralList(1,pageIndex +1 , Constants.PAGE_SIZE)
            }
            ScoreTypeEnum.MiBean.id->{
                iPresenter.getMiBeanList(pageIndex+1 , Constants.PAGE_SIZE)
            }
        }
    }


    override fun showProgress(msg: String) {
        super.showProgress(msg)
        if(isShowProgress){
            wait_accounts_progress.visibility=View.VISIBLE
        }else{
            wait_accounts_progress.visibility=View.GONE
        }
    }

    override fun hideProgress() {
        super.hideProgress()
        wait_accounts_progress.visibility=View.GONE
        wait_accounts_refreshView.isRefreshing=false
    }

    override fun onRefresh() {
        pageIndex= 0
        isShowProgress=false
        getData()
    }

    override fun onLoadMoreRequested() {
        getData()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.header_left_image->{finish()}
        }
    }

    override fun getIntegralListCallback(apiResult: ApiResult<ScoreBean>) {
        isShowProgress=false
        wait_accounts_refreshView.isRefreshing=false
        scoreAdapter!!.isUseEmpty(true)
        hideProgress()
        if(processCommonErrorCode(apiResult)){return}
        if(apiResult.code !=ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.msg)
            return
        }

        if (apiResult.data == null) return

        if( type==ScoreTypeEnum.WaitAccounts.id){
            setUiData_waitScore(apiResult)
        }else if(type == ScoreTypeEnum.Balance.id){
            setUiData_balance(apiResult)
        }
    }


    override fun getMiBeanListCallback(apiResult: ApiResult<MiBean>) {
        isShowProgress = false
        wait_accounts_refreshView.isRefreshing = false
        miAdapter!!.isUseEmpty(true)
        hideProgress()
        if (processCommonErrorCode(apiResult)) {
            return
        }
        if (apiResult.code != ApiResultCodeEnum.SUCCESS.code) {
            toast(apiResult.msg)
            return
        }

        if (apiResult.data == null) return

        setUiDataMi(apiResult)
    }

    private fun setUiDataMi(apiResult: ApiResult<MiBean>){
        if(apiResult!!.data!!.Items==null) return
        if(apiResult!!.data!!.Items!!.size < Constants.PAGE_SIZE) {
            //没有数据了
            if (pageIndex == 0) {
                miAdapter!!.loadMoreEnd(true)
            } else {
                miAdapter!!.loadMoreEnd()
            }

            pageIndex++

        } else {
            miAdapter!!.loadMoreComplete()
            pageIndex++
        }

        if (pageIndex == 1) {

            miAdapter!!.setNewData(apiResult.data!!.Items)
            miAdapter!!.disableLoadMoreIfNotFullPage( wait_accounts_recyclerview )
        } else {
            miAdapter!!.addData(apiResult.data!!.Items!!)
        }
    }

    private fun setUiData_balance(apiResult: ApiResult<ScoreBean>){
        if(apiResult!!.data!!.Items==null) return
        if(apiResult!!.data!!.Items!!.size < Constants.PAGE_SIZE) {
            //没有数据了
            if (pageIndex == 0) {
                scoreAdapter!!.loadMoreEnd(true)
            } else {
                scoreAdapter!!.loadMoreEnd()
            }

            pageIndex++

        } else {
            scoreAdapter!!.loadMoreComplete()
            pageIndex++
        }

        if (pageIndex == 1) {

            scoreAdapter!!.setNewData(apiResult.data!!.Items)
            scoreAdapter!!.disableLoadMoreIfNotFullPage( wait_accounts_recyclerview )
        } else {
            scoreAdapter!!.addData(apiResult.data!!.Items!!)
        }
    }

    private fun setUiData_waitScore(apiResult: ApiResult<ScoreBean>){
        var sumImportIntegral= apiResult.data!!.SumImportIntegral
        var sumExportIntegral = apiResult.data!!.SumExportIntegral
        var sum1 = BigDecimal(sumImportIntegral).setScale(2,BigDecimal.ROUND_HALF_UP).div(BigDecimal(100))

        var sum2 = BigDecimal(sumExportIntegral).setScale(2,BigDecimal.ROUND_HALF_UP).div(BigDecimal(100))

        wait_accounts_income.text = sum1.stripTrailingZeros().toPlainString()
        wait_accounts_outcome.text = sum2.stripTrailingZeros().toPlainString()

        if(apiResult!!.data!!.Items==null) return

        if(apiResult!!.data!!.Items!!.size < Constants.PAGE_SIZE) {
            //没有数据了
            if (pageIndex == 0) {
                scoreAdapter!!.loadMoreEnd(true)
            } else {
                scoreAdapter!!.loadMoreEnd()
            }

            pageIndex++

        } else {
            scoreAdapter!!.loadMoreComplete()
            pageIndex++
        }


        if (pageIndex == 1) {

            scoreAdapter!!.setNewData(apiResult.data!!.Items)
            scoreAdapter!!.disableLoadMoreIfNotFullPage(wait_accounts_recyclerview)
        } else {
            scoreAdapter!!.addData(apiResult.data!!.Items!!)
        }

    }
}
