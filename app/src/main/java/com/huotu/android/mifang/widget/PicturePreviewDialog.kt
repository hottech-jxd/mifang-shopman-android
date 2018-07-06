package com.huotu.android.mifang.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.huotu.android.mifang.R
import com.huotu.android.mifang.adapter.PicturePreviewAdapter
import com.huotu.android.mifang.bean.KeyValue
import com.huotu.android.mifang.util.DensityUtils
import com.huotu.android.mifang.widget.OperateDialog.OnOperateListener

/**
 * 图片预览弹出框
 */
class PicturePreviewDialog(context: Context )
    : BaseDialog(context), View.OnClickListener , android.support.v4.view.ViewPager.OnPageChangeListener {

    var previewAdapter:PicturePreviewAdapter?=null

    init {

//        if (onOperateListener == null) {
//            throw NullPointerException("参数空异常")
//        }
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.layout_picture_preview_dialog, null)

        //var layout = view.layoutParams
        //layout.width = DensityUtils.getScreenWidth(context) *80/100
        //layout.height = DensityUtils.getScreenHeight(context);
        //view.layoutParams = layout

        this.addContentView(view)
//        val ivClose = view.findViewById<ImageView>(R.id.msg_one_close)
//        ivClose.setOnClickListener(this)
        var viewPager=view.findViewById<android.support.v4.view.ViewPager>(R.id.picture_preview_viewpager)
        viewPager.addOnPageChangeListener(this)
    }

//    fun setMaxHeight(maxHeight:Int){
//        var iv = this.dialog!!.findViewById<ImageView>(R.id.msg_one_logo)
//        iv.maxHeight = maxHeight
//        iv.adjustViewBounds =true
//    }

    fun show(imageUrls:ArrayList<String?> , current:Int){


        previewAdapter = PicturePreviewAdapter(imageUrls , this)
        var viewPager = this.dialog!!.findViewById<ViewPager>(R.id.picture_preview_viewpager)
        viewPager.adapter = previewAdapter
        viewPager.currentItem = current

        var container = this.dialog!!.findViewById<LinearLayout>(R.id.picture_preview_indexer)
        container.removeAllViews()
        if(imageUrls.size>1) {
            for (i in 0 until imageUrls.size) {
                var iv = ImageView(context)
                iv.id = i
                var px = DensityUtils.dip2px(context, 9f)
                var layoutPara = LinearLayout.LayoutParams(px, px)
                layoutPara.setMargins(4, 3, 4, 3)
                iv.layoutParams = layoutPara
                if (i == current ) {
                    iv.setImageResource(R.drawable.shape_circle_gray)
                } else {
                    iv.setImageResource(R.drawable.shape_circle_white)
                }

                container.addView(iv)
            }
        }


        show()
    }

    override fun show() {

        val window = this.dialog!!.window
        if (window != null) {
            window!!.setWindowAnimations(R.style.anim_dialog)
            window!!.setGravity(Gravity.CENTER)
        }

        this.setSize(screenWidthPixels, screenHeightPixels)

        super.show()
    }

    override fun onClick(v: View?) {
        //dialog!!.dismiss()
        //dialog=null
        dismiss()
    }



    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        var container= dialog!!.findViewById<LinearLayout>(R.id.picture_preview_indexer)
        for(i in 0 until container.childCount) {
            if( i == position) {
                (container.getChildAt(i) as ImageView).setImageResource(R.drawable.shape_circle_gray)
            }else{
                (container.getChildAt(i) as ImageView).setImageResource(R.drawable.shape_circle_white)
            }
        }
    }
}
