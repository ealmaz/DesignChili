package com.design.chili.view.modals.bottom_sheet.serach_bottom_sheet

import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.design.chili.R
import com.design.chili.extensions.setOnSingleClickListener

class SearchSelectorItemVH(val view: SearchSelectorItemVHViewData): RecyclerView.ViewHolder(view.rootView) {

    private var item: Option? = null

    fun onBind(item: Option, isLast: Boolean) {
        this.item = item
        view.ivChoice.isVisible = item.isSelected
        view.tvTitle.text = item.value
        view.divider.isVisible = !isLast
    }

    companion object{
        fun create(parent: ViewGroup, onClick: (Option?) -> Unit): SearchSelectorItemVH {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chili_item_search_selector, parent, false)
            val holder = SearchSelectorItemVH(SearchSelectorItemVHViewData(
                itemView,
                itemView.findViewById(R.id.tv_title),
                itemView.findViewById(R.id.iv_choice),
                itemView.findViewById(R.id.divider))
            )
            holder.itemView.setOnSingleClickListener { onClick(holder.item) }
            return holder
        }
    }
}

data class SearchSelectorItemVHViewData(val rootView: View, val tvTitle: TextView, val ivChoice: ImageView, val divider: View)

class SearchSelectorHeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBind(item: String) {
        (itemView as TextView).text = item
    }

    companion object {

        fun create(parent: ViewGroup): SearchSelectorHeaderVH {
            val itemView = TextView(parent.context)
            itemView.run {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setPadding(resources.getDimension(R.dimen.padding_16dp).toInt(),
                        resources.getDimension(R.dimen.padding_8dp).toInt(),
                        resources.getDimension(R.dimen.padding_8dp).toInt(),
                        resources.getDimension(R.dimen.padding_8dp).toInt())
                gravity = Gravity.START
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setTextAppearance(R.style.Chili_H8_Primary)
                } else {
                    setTextAppearance(context, R.style.Chili_H8_Primary)
                }
                setBackgroundResource(R.color.gray_4)
            }
            return SearchSelectorHeaderVH(itemView)
        }
    }
}
