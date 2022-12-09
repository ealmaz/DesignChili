package com.design.chili.view.card

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Path.Op
import android.graphics.drawable.GradientDrawable
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.design.chili.R
import com.design.chili.extensions.getColorFromAttr
import com.design.chili.extensions.gone
import com.design.chili.extensions.setImageByUrl
import com.design.chili.extensions.visible
import com.design.chili.model.Option
import com.design.chili.util.IconStatus
import com.design.chili.view.cells.TitledInfoCellView
import com.design.chili.view.image.SquircleView

class ConfigToggledCardView : ConstraintLayout {

    private lateinit var view: ConfigToggledCardViewVariables

    constructor(context: Context) : super(context) {
        inflateViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflateViews()
        obtainAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        inflateViews()
        obtainAttributes(attrs, defStyle)
    }

    private fun inflateViews() {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_config_toggle_card, this)
        this.view = ConfigToggledCardViewVariables(
            root = view.findViewById(R.id.root_view),
            titledInfoView = view.findViewById(R.id.ticv_view),
            rvItems = view.findViewById(R.id.rv_items)
        )
    }

    private fun obtainAttributes(attrs: AttributeSet, defStyle: Int = R.style.Chili_CardViewStyle_SingleSelectedCard) {
        context?.obtainStyledAttributes(attrs, R.styleable.SingleSelectedCardView, R.attr.singleSelectedCardViewDefaultStyle, defStyle)?.run {
            getString(R.styleable.SingleSelectedCardView_title)?.let {
                setTitleText(it)
            }
            getString(R.styleable.SingleSelectedCardView_value)?.let {
                setSubtitle(it)
            }
            recycle()
        }
    }

    fun setTitleText(title: String) {
        view.titledInfoView.setTitle(title)
    }

    fun setSubtitle(subtitle: String) {
        view.titledInfoView.setSubtitle(subtitle)
    }

    fun setInfoBtnVisibilty(isVisible: Boolean) {
        view.titledInfoView.setIsInfoButtonVisible(isVisible)
    }

    fun setInfoBtnClickListener(onClick: () -> Unit) {
        view.titledInfoView.setInfoButtonClickListener(onClick)
    }

    fun setToggles(items: ArrayList<Option<*>>) {
        val adapter = TitledTogglesAdapter()
        view.rvItems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.rvItems.adapter = adapter
        adapter.addItems(items)
    }

}

private data class ConfigToggledCardViewVariables(
    var root: ConstraintLayout,
    var titledInfoView: TitledInfoCellView,
    var rvItems: RecyclerView
)

class TitledTogglesAdapter() : RecyclerView.Adapter<TitledTogglesAdapter.TitledToggleVH>() {

    val items = ArrayList<Option<*>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitledToggleVH {
        val view = TitledToggleCardView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        return TitledToggleVH(view)
    }

    override fun onBindViewHolder(holder: TitledToggleVH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(items: ArrayList<Option<*>>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class TitledToggleVH(view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: Option<*>) {
            (itemView as TitledToggleCardView).apply {
                item.title?.let { setTitleText(it) }
                item.description?.let { setValue(it) }
                setIsInfoButtonVisible(item.isInfoBtnVisible == true)
                item.icons?.let { setIcons(it) }
            }
        }
    }
}
