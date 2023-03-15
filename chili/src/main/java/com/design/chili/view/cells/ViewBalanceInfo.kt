package com.design.chili.view.cells

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.design.chili.R
import com.design.chili.extensions.setAppearance
import com.design.chili.extensions.setTextOrHide

class ViewBalanceInfo @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.detailCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_DetailCellView
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private lateinit var view: BalanceInfoViewVariables
    private var topPadding: Int = 0
    private var bottomPadding: Int = 0

    init {
        initView(context)
        obtainAttributes(context, attributeSet, defStyleAttr, defStyleRes)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_balance_info, this)
        this.view = BalanceInfoViewVariables(
            root = view.findViewById(R.id.root),
            tvTitle = view.findViewById(R.id.tv_title),
            tvValue = view.findViewById(R.id.tv_value),
            ivIcon = view.findViewById(R.id.iv_icon)
        )
    }

    private fun obtainAttributes(
        context: Context,
        attributeSet: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        context.obtainStyledAttributes(
            attributeSet,
            R.styleable.ViewBalanceInfo,
            defStyleAttr,
            defStyleRes
        ).run {
            setTitle(getString(R.styleable.ViewBalanceInfo_title))
            setValue(getString(R.styleable.ViewBalanceInfo_value))
            setIcon(getDrawable(R.styleable.ViewBalanceInfo_icon))
            setVerticalPadding(getResourceId(R.styleable.ViewBalanceInfo_rootVerticalPadding, R.dimen.view_0dp))
            setTopPadding(getResourceId(R.styleable.ViewBalanceInfo_rootTopPadding, R.dimen.view_0dp))
            setBottomPadding(getResourceId(R.styleable.ViewBalanceInfo_rootBottomPadding, R.dimen.view_0dp))
            recycle()
        }
    }

    fun setTitle(text: String?) {
        view.tvTitle.text = text
    }

    fun setTitle(@StringRes textResId: Int) {
        view.tvTitle.setText(textResId)
    }

    fun setTitleTextAppearance(@StyleRes resId: Int) {
        view.tvTitle.setAppearance(resId)
    }

    fun setValue(text: String?) {
        view.tvValue.setTextOrHide(text)
    }

    fun setValue(@StringRes textResId: Int?) {
        view.tvValue.setTextOrHide(textResId)
    }

    fun setValue(text: Spanned?) {
        view.tvValue.setTextOrHide(text)
    }

    fun setValueTextColor(@ColorInt colorInt: Int) {
        view.tvValue.setTextColor(colorInt)
    }

    fun setValueTextAppearance(@StyleRes resId: Int) {
        view.tvValue.setAppearance(resId)
    }

    fun setIcon(@DrawableRes drawableResId: Int) {
        view.ivIcon.setImageResource(drawableResId)
    }

    fun setIcon(drawable: Drawable?) {
        view.ivIcon.setImageDrawable(drawable)
    }

    fun setIconUrl(url: String?) {
        if (url == null) return
        Glide.with(view.ivIcon)
            .load(url)
            .dontTransform()
            .into(view.ivIcon)
    }

    fun setVerticalPadding(@DimenRes resId: Int) {
        val padding = resources.getDimensionPixelSize(resId)
        view.root.setPadding(0, padding, 0, padding)
    }

    fun setTopPadding(@DimenRes resId: Int) {
        val padding = resources.getDimensionPixelSize(resId)
        topPadding = padding
        view.root.setPadding(0, topPadding, 0, bottomPadding)
    }

    fun setBottomPadding(@DimenRes resId: Int) {
        val padding = resources.getDimensionPixelSize(resId)
        bottomPadding = padding
        view.root.setPadding(0, topPadding, 0, bottomPadding)
    }
}

data class BalanceInfoViewVariables(
    val root: ConstraintLayout,
    val tvTitle: TextView,
    val tvValue: TextView,
    val ivIcon: ImageView
)