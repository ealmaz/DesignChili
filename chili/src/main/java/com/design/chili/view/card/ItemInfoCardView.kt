package com.design.chili.view.card

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.design.chili.R

class ItemInfoCardView : ConstraintLayout {

    private lateinit var view: ItemInfoCardViewVariables

    constructor(context: Context) : super(context) {
        inflateViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflateViews()
        obtainAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        inflateViews()
        obtainAttributes(attrs)
    }

    private fun inflateViews() {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_item_info_card, this)
        this.view = ItemInfoCardViewVariables(
            tvTitle = view.findViewById(R.id.tv_title),
            tvValue = view.findViewById(R.id.tv_value),
            ivIcon = view.findViewById(R.id.iv_icon)

        )
    }

    private fun obtainAttributes(attrs: AttributeSet) {
        context?.obtainStyledAttributes(attrs, R.styleable.ItemInfoCardView)?.run {
            getString(R.styleable.ItemInfoCardView_title)?.let {
                setTitle(it)
            }
            getString(R.styleable.ItemInfoCardView_value)?.let {
                setValueText(it)
            }
            getDrawable(R.styleable.ItemInfoCardView_icon).let {
                setIconDrawable(it)
            }
            recycle()
        }
    }

    fun setTitle(text: String?) {
        view.tvTitle.text = text
    }

    fun setTitle(@StringRes resId: Int) {
        view.tvTitle.setText(resId)
    }

    fun setValueText(text: String?) {
        view.tvValue.text = text
    }

    fun setValueText(@StringRes resId: Int) {
        view.tvValue.setText(resId)
    }

    fun setIconDrawable(drawable: Drawable?) {
        view.ivIcon.setImageDrawable(drawable)
    }

    fun setIconDrawable(@DrawableRes resId: Int) {
        view.ivIcon.setImageResource(resId)
    }

}

private data class ItemInfoCardViewVariables(
    var tvTitle: TextView,
    var tvValue: TextView,
    var ivIcon: ImageView,
)