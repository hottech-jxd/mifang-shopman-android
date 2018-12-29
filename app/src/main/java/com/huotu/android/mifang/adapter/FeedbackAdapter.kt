package com.huotu.android.mifang.adapter

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huotu.android.mifang.R
import com.huotu.android.mifang.bean.FeedbackBean

class FeedbackAdapter(data:ArrayList<FeedbackBean>):BaseQuickAdapter<FeedbackBean,BaseViewHolder>(R.layout.layout_feedback_image_item , data) {

    override fun convert(helper: BaseViewHolder?, item: FeedbackBean?) {

        if(item!!.isNew) {
            helper!!.setImageResource(R.id.feedback_image_item_pic , R.mipmap.ic_launcher)
            helper.setGone(R.id.feedback_image_item_delete , false)
        }else {
            var iv = helper!!.getView<ImageView>(R.id.feedback_image_item_pic)
            Glide.with(mContext)
                    .asBitmap()
                    .load(item!!.url)
                    //.into(helper!!.getView(R.id.feedback_image_item_pic))
                    .into(object :SimpleTarget<Bitmap>(){
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            var w =iv.width
                            var h = w
                            var rw = resource.width
                            var rh = resource.height

                            var tw = 0
                            var th = 0
                            if( rw > rh){
                                tw = w
                                th = w * rh / rw
                            }else{
                                tw =  rw * h / rh //  w/h = rw / rh
                                th = h
                            }

                            //var tw = if(rw>rh) rw else rh
                            //var tw =  if(rw > rh ) w else w
                            //var th = if(rw> rh)  h else rh * h / w
                            var layoutparam = iv.layoutParams
                            layoutparam.width = tw
                            layoutparam.height = th
                            iv.layoutParams = layoutparam
                            iv.setImageBitmap(resource)
                        }
                    })
            helper!!.setGone(R.id.feedback_image_item_delete,true)
        }

        helper!!.addOnClickListener(R.id.feedback_image_item_delete)
    }
}