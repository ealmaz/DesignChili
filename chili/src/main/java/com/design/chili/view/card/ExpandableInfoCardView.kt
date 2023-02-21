package com.design.chili.view.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import com.design.chili.R
import com.design.chili.extensions.gone
import com.design.chili.extensions.setTextOrHide
import com.design.chili.extensions.visible
import com.design.chili.view.shimmer.CustomBone
import com.design.chili.view.shimmer.Shimmering

class ExpandableInfoCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.expandableInfoCardViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CardViewStyle_ExpandableInfoCardView,
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes), Shimmering {

    private lateinit var view: ExpandableInfoCardViewVariables

    private var isExpandable: Boolean = false

    private var isExpanded: Boolean = false

    init {
        inflateView(context)
        obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
        setupViews()
    }

    private fun inflateView(context: Context) {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.chili_view_expandable_card_info, this, true)
        this.view = ExpandableInfoCardViewVariables(
            tvTitle = view.findViewById(R.id.tv_title),
            tvSubtitle = view.findViewById(R.id.tv_subtitle),
            tvValue = view.findViewById(R.id.tv_value),
            ivArrow = view.findViewById(R.id.iv_arrow),
            rootView = view.findViewById(R.id.root_view),
        )
    }

    private fun obtainAttributes(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) {
        context.obtainStyledAttributes(attrs, R.styleable.ExpandableInfoCardView, defStyleAttr, defStyleRes).run {
            getString(R.styleable.ExpandableInfoCardView_title).run { setTitle(this) }
            getString(R.styleable.ExpandableInfoCardView_subtitle).run { setSubtitle(this) }
            getString(R.styleable.ExpandableInfoCardView_value).run { setValue(this) }
            setIsExpandable(getBoolean(R.styleable.ExpandableInfoCardView_isExpandable, false))
            setIsExpanded(getBoolean(R.styleable.ExpandableInfoCardView_isExpanded, false))
            recycle()
        }
    }


    fun setTitle(charSequence: CharSequence?) {
        view.tvTitle.text = charSequence
    }

    fun setTitle(resId: Int) {
        view.tvTitle.setText(resId)
    }

    fun setSubtitle(charSequence: CharSequence?) {
        view.tvSubtitle.setTextOrHide(charSequence)
    }

    fun setSubtitle(resId: Int?) {
        view.tvSubtitle.setTextOrHide(resId)
    }

    fun setValue(charSequence: CharSequence?) {
        view.tvValue.setTextOrHide(charSequence)
    }

    fun setValue(resId: Int) {
        view.tvValue.setText(resId)
    }

    fun setIsExpandable(isExpandable: Boolean?) {
        this.isExpandable = isExpandable ?: false
    }

    fun setIsExpanded(isExpanded: Boolean?) {
        this.isExpanded = isExpanded ?: false
        if (this.isExpanded) {
            rotateChevron(0f)
            children.forEach {
                if (it is ExpandableInfoCardItemView) it.visible()
            }
        } else {
            rotateChevron(180f)
            children.forEach {
                if (it is ExpandableInfoCardItemView) it.gone()
            }
        }
    }

    private fun rotateChevron(rotation: Float = 0f) {
        view.ivArrow.animate().rotation(rotation)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child is ExpandableInfoCardItemView) child.isVisible = isExpanded
        super.addView(child, index, params)
    }

    private fun createExpandableRow(): ExpandableInfoCardItemView {
        return ExpandableInfoCardItemView(context)
    }

    private fun setupExpandableRow(item: ExpandableItemData, itemView: ExpandableInfoCardItemView) {
        itemView.apply {
            setTitle(item.title)
            setSubtitle(item.subTitle)
            setTitleValue(item.titleValue)
            setSubtitleValue(item.subtitleValue)
        }
    }

    private fun setupViews() {
        view.apply {
            ivArrow.isVisible = isExpandable
            ivArrow.setOnClickListener { setIsExpanded(!isExpanded) }
            rootView.setOnClickListener { setIsExpanded(!isExpanded) }
        }
    }

    override fun getIgnoredViews(): Array<View> = arrayOf(view.ivArrow)

    override fun getCustomBones(): Array<CustomBone> = arrayOf(
        CustomBone(view.tvTitle) {},
        CustomBone(view.tvSubtitle) {},
    )
}

data class ExpandableInfoCardViewVariables(
    val tvTitle: TextView,
    val tvSubtitle: TextView,
    val tvValue: TextView,
    val ivArrow: ImageView,
    val rootView: ConstraintLayout
)

data class ExpandableItemData(
    val title: CharSequence? = null,
    val subTitle: CharSequence? = null,
    val titleValue: CharSequence? = null,
    val subtitleValue: CharSequence? = null,
)