package com.design.chili.view.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.design.chili.R
import com.design.chili.extensions.setImageByUrl
import com.design.chili.extensions.visible
import com.design.chili.model.Option

class ConfigSelectorCardView : ConstraintLayout {

    private lateinit var view: ConfigSelectorCardViewVariables

    constructor(context: Context) : super(context) {
        inflateViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflateViews()
        obtainAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        inflateViews()
        obtainAttributes(attrs, defStyle)
    }

    private fun inflateViews() {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_config_selector_card, this)
        this.view = ConfigSelectorCardViewVariables(
            root = view.findViewById(R.id.root_view),
            tvTitle = view.findViewById(R.id.tv_title),
            ivIcon = view.findViewById(R.id.iv_icon),
            rvItems = view.findViewById(R.id.rv_items)
        )
    }

    private fun obtainAttributes(
        attrs: AttributeSet,
        defStyle: Int = R.style.Chili_CardViewStyle_SingleSelectedCard
    ) {
        context?.obtainStyledAttributes(
            attrs,
            R.styleable.SingleSelectedCardView,
            R.attr.singleSelectedCardViewDefaultStyle,
            defStyle
        )?.run {
            getString(R.styleable.SingleSelectedCardView_title)?.let {
                setTitleText(it)
            }
            recycle()
        }
    }

    fun setTitleText(title: String) {
        view.tvTitle.text = title
    }

    fun setIcon(imgUrl: String) {
        view.ivIcon.visible()
        view.ivIcon.setImageByUrl(imgUrl)
    }

    fun setIcon(@DrawableRes resId: Int) {
        view.ivIcon.visible()
        view.ivIcon.setImageResource(resId)
    }

    fun setSelectors(
        items: ArrayList<Option<*>>,
        listener: SingleSelectorAdapter.SingleSelectedListener
    ) {
        val adapter = SingleSelectorAdapter(listener)
        view.rvItems.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.rvItems.adapter = adapter
        adapter.addItems(items)
    }
}

private data class ConfigSelectorCardViewVariables(
    var root: ConstraintLayout,
    var tvTitle: TextView,
    var ivIcon: ImageView,
    var rvItems: RecyclerView
)

class SingleSelectorAdapter(val listener: SingleSelectedListener) :
    RecyclerView.Adapter<SingleSelectorAdapter.SingleSelectorVH>() {

    private val items = ArrayList<Option<*>>()

    var selectedItemPosition = -1
    @Synchronized set(value) {
        if (field != -1) notifyItemChanged(field)
        field = value
        if (value != -1) notifyItemChanged(value)
    }
    @Synchronized get

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleSelectorVH {
        val view = SingleSelectedCardView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return SingleSelectorVH(view, listener)
    }

    override fun onBindViewHolder(holder: SingleSelectorVH, position: Int) {
        (holder.itemView as SingleSelectedCardView).apply {
            items[position].color?.let { setupColor(it) }
        }
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

    inner class SingleSelectorVH(
        view: View,
        private val listener: SingleSelectedListener
    ) : RecyclerView.ViewHolder(view) {
        fun bind(item: Option<*>) {
            (itemView as SingleSelectedCardView).apply {
                if (selectedItemPosition == adapterPosition) setSelected()
                else reset()

                item.title?.let { setTitleText(it) }
                item.description?.takeIf { it.isEmpty().not() }?.let { setValueHtml(it) }

                if (item.isActive) setActive()

                setOnClickListener {
                    if (selectedItemPosition != adapterPosition) {
                        val prevSelectedItem = selectedItemPosition
                        selectedItemPosition = adapterPosition
                        if (prevSelectedItem != -1) {
                            listener.onUnselected(prevSelectedItem)
                        }
                        listener.onSelected(selectedItemPosition)
                        true
                    }
                    else {
                        selectedItemPosition = -1
                        listener.onUnselected(adapterPosition)
                        false
                    }
                }

                setUnavailable(item.isUnavailable)

                setOnIconClickListener {
                    selectedItemPosition = -1
                    listener.onUnselected(adapterPosition)
                }
            }
        }
    }

    interface SingleSelectedListener {
        fun onSelected(position: Int)
        fun onUnselected(position: Int)
    }
}
