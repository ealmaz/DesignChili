package com.design.chili.view.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.design.chili.R
import com.design.chili.extensions.setTextOrHide
import com.design.chili.view.shimmer.CustomBone
import com.design.chili.view.shimmer.Shimmering

class ExpandableInfoCardItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.expandableInfoCardItemViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CardViewStyle_ExpandableInfoCardItemView
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), Shimmering {

    private lateinit var view: ExpandableInfoCardItemViewVariables

    init {
        inflateView(context)
        obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun inflateView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_expandable_card_info_item, this, true)
        this.view = ExpandableInfoCardItemViewVariables(
            tvTitle = view.findViewById(R.id.tv_title),
            tvSubtitle = view.findViewById(R.id.tv_subtitle),
            tvTitleValue = view.findViewById(R.id.tv_title_value),
            tvSubtitleValue = view.findViewById(R.id.tv_subtitle_value)
        )
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.ExpandableInfoCardInfoView, defStyleAttr, defStyleRes).run {
            getString(R.styleable.ExpandableInfoCardInfoView_title).run { setTitle(this) }
            getString(R.styleable.ExpandableInfoCardInfoView_subtitle).run { setSubtitle(this) }
            getString(R.styleable.ExpandableInfoCardInfoView_titleValue).run { setTitleValue(this) }
            getString(R.styleable.ExpandableInfoCardInfoView_subtitleValue).run { setSubtitleValue(this) }
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

    fun setTitleValue(charSequence: CharSequence?) {
        view.tvTitleValue.setTextOrHide(charSequence)
    }

    fun setTitleValue(resId: Int) {
        view.tvTitleValue.setText(resId)
    }

    fun setSubtitleValue(charSequence: CharSequence?) {
        view.tvSubtitleValue.setTextOrHide(charSequence)
    }

    fun setSubtitleValue(resId: Int) {
        view.tvSubtitleValue.setText(resId)
    }

    override fun getIgnoredViews(): Array<View> = emptyArray()

    override fun getCustomBones(): Array<CustomBone> {
        return arrayOf(CustomBone(this) { setWidth(0f) })
    }
}

data class ExpandableInfoCardItemViewVariables(
    val tvTitle: TextView,
    val tvSubtitle: TextView,
    val tvTitleValue: TextView,
    val tvSubtitleValue: TextView,
)