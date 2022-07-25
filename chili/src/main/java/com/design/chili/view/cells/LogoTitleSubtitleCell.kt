package com.design.chili.view.cells

import android.content.Context
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.design.chili.R
import com.design.chili.extensions.*
import com.design.chili.extensions.gone
import com.design.chili.extensions.visible

class LogoTitleSubtitleCell : ConstraintLayout {

    private lateinit var view: LogoTitleSubtitleCellVariables

    constructor(context: Context) : super(context) {
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupView()
        obtainAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setupView()
        obtainAttributes(attrs, defStyle)
    }

    private fun setupView() {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_item_logo_title_subtitle, this)
        this.view = LogoTitleSubtitleCellVariables(
            tvTitle = view.findViewById(R.id.tv_title),
            tvSubtitle = view.findViewById(R.id.tv_subtitle),
            logo = view.findViewById(R.id.iv_icon),
            divider = view.findViewById(R.id.view_divider),
            rootView = view.findViewById(R.id.root)
        )
        removeAllElevation()
    }

    private fun obtainAttributes(
        attrs: AttributeSet,
        defStyle: Int = R.style.Chili_CellViewStyle_LogoTitleSubtitleCell
    ) {
        context?.obtainStyledAttributes(attrs, R.styleable.LogoTitleSubtitleCell, R.attr.logoTitleSubtitleCellDefaultStyle, defStyle)?.run {

                getResourceId(R.styleable.LogoTitleSubtitleCell_android_icon, -1).let {
                    if (it > -1) setLogo(it)
                }
                getString(R.styleable.LogoTitleSubtitleCell_title)?.let { setTitle(it) }
                getString(R.styleable.LogoTitleSubtitleCell_subtitle)?.let { setSubtitle(it) }
                getBoolean(R.styleable.LogoTitleSubtitleCell_hide_divider, false).let {
                    view.divider.isVisible = !it
                }
                getInteger(R.styleable.LogoTitleSubtitleCell_roundedCornerMode, 0).let {
                    view.rootView.setupRoundedCardCornersMode(it)
                }

            recycle()
        }

    }

    fun setTitle(label: CharSequence?) {
        view.tvTitle.text = label
    }

    fun setTitle(@StringRes stringId: Int) {
        view.tvTitle.text = context.getString(stringId)
    }

    fun changeTitleColor(@ColorRes colorId: Int) {
        view.tvTitle.setTextColor(context.color(colorId))
    }

    fun setSubtitle(label: CharSequence?) {
        view.tvSubtitle.run {
            text = label
            if (!label.isNullOrBlank())
                visible()
        }
    }

    fun changeSubtitleColor(@ColorRes colorId: Int) {
        view.tvSubtitle.setTextColor(context.color(colorId))
    }

    fun setTitlesWithColor(titleText: Spanned?, subtitleText: Spanned?, @ColorRes colorId: Int) {
        setTitle(titleText)
        changeTitleColor(colorId)
        setSubtitle(subtitleText)
        changeSubtitleColor(colorId)
    }

    fun setup(titleLabel: String?, subtitleLabel: String?, logo: Any?) {
        setTitle(titleLabel)
        setSubtitle(subtitleLabel)
        when (logo) {
            is String? -> setLogo(logo)
            is Int -> setLogo(logo)
        }

    }

    fun setLogo(logo: Any?) {
        when (logo) {
            is String -> setLogo(logo)
            is Int -> setLogo(logo)
        }
    }

    fun setLogo(@DrawableRes drawableRes: Int?) {
        drawableRes?.let {
            view.logo.run {
                setImageDrawable(context.drawable(drawableRes))
                visible()
            }
        }
    }

    fun setLogo(url: String?) {
        url?.let {
            view.logo?.run {
                setImageByUrl(url)
                visible()
            }
        }
    }

    fun removeDivider() = view.divider.gone()
    fun hideSubtitle() = view.tvSubtitle.gone()

    private fun removeAllElevation() {
        elevation = 0.0f
    }

}

private data class LogoTitleSubtitleCellVariables(
    var tvTitle: TextView,
    var tvSubtitle: TextView,
    var logo: ImageView,
    var divider: View,
    var rootView: ConstraintLayout,
)