package com.zhy.shoppingdemo

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class ItemAdapter(val mdata: ArrayList<String>, layoutResId: Int) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutResId, mdata) {
    private var itemClick: ItemClick? = null
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.getView<View>(R.id.jiaImage).setOnClickListener {

            itemClick?.itemClick(it,holder.adapterPosition)
        }
    }
    fun setItemClick(itemClick: ItemClick) {
        this.itemClick = itemClick
    }

}