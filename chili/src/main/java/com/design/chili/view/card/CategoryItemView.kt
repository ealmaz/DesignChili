package com.design.chili.view.card

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.design.chili.R
import com.design.chili.view.image.SquircleView

class CategoryItemView : FrameLayout {

    private lateinit var view: CategoryItemViewVariables

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
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_category_item, this)
        this.view = CategoryItemViewVariables(
            svIcon = view.findViewById(R.id.sv_icon),
            ivNotification = view.findViewById(R.id.iv_notification),
            tvTitle = view.findViewById(R.id.tv_title),
            tvAmount = view.findViewById(R.id.tv_amount)
        )
    }

    private fun obtainAttributes(attrs: AttributeSet, defStyle: Int = R.style.Chili_CardViewStyle_CategoryItemViewStyle) {
        context?.obtainStyledAttributes(attrs, R.styleable.CategoryItemView, R.attr.categoryItemCardViewDefaultStyle, defStyle)?.run {
            getString(R.styleable.CategoryItemView_title)?.let {
                setTitle(it)
            }
            getString(R.styleable.CategoryItemView_amountText)?.let {
                setAmountText(it)
            }
            getBoolean(R.styleable.CategoryItemView_isNotificationEnabled, false).let {
                setIsNotificationEnabled(it)
            }
            getDrawable(R.styleable.CategoryItemView_squircleViewDrawable)?.let {
                setSquircleViewDrawable(it)
            }
            recycle()
        }
    }

    fun setTitle(title: String) {
        view.tvTitle.text = title
    }

    fun setTitle(@StringRes resId: Int) {
        view.tvTitle.setText(resId)
    }

    fun setIsNotificationEnabled(isEnabled: Boolean) {
        view.ivNotification.visibility = if (isEnabled) View.VISIBLE else View.GONE
    }

    fun getSquircleView(): SquircleView {
        return view.svIcon
    }

    fun setSquircleViewDrawable(drawable: Drawable) {
        view.svIcon.setImageDrawable(drawable)
    }

    fun setSquircleViewDrawable(@DrawableRes resId: Int) {
        view.svIcon.setImageResource(resId)
    }

    fun setAmountText(text: String) {
        setIsAmountVisible(text.isNotEmpty())
        view.tvAmount.text = text
    }

    fun setAmountText(@StringRes resId: Int) {
        view.tvAmount.setText(resId)
    }

    fun setIsAmountVisible(isVisible: Boolean) {
        view.tvAmount.visibility = if(isVisible) View.VISIBLE else View.GONE
    }
}

private data class CategoryItemViewVariables(
    var svIcon: SquircleView,
    var ivNotification: ImageView,
    var tvTitle: TextView,
    var tvAmount: TextView
)