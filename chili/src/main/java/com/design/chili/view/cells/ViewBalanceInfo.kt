package com.design.chili.view.cells

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
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

    init {
        initView(context)
        obtainAttributes(context, attributeSet, defStyleAttr, defStyleRes)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_balance_info, this)
        this.view = BalanceInfoViewVariables(
            root = view.findViewById(R.id.root),
            tvTitle = view.findViewById(R.id.tv_title),
            tvSubtitle = view.findViewById(R.id.tv_subtitle),
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
            setSubtitle(getString(R.styleable.ViewBalanceInfo_subtitle))
            setValue(getString(R.styleable.ViewBalanceInfo_value))
            setIcon(getDrawable(R.styleable.ViewBalanceInfo_icon))
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

    fun setSubtitle(text: String?) {
        view.tvSubtitle.setTextOrHide(text)
    }

    fun setSubtitle(@StringRes textResId: Int) {
        view.tvSubtitle.setTextOrHide(textResId)
    }

    fun setSubtitleTextAppearance(@StyleRes resId: Int) {
        view.tvSubtitle.setAppearance(resId)
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
}

data class BalanceInfoViewVariables(
    val root: ConstraintLayout,
    val tvTitle: TextView,
    val tvSubtitle: TextView,
    val tvValue: TextView,
    val ivIcon: ImageView
)