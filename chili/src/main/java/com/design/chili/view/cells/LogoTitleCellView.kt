package com.design.chili.view.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.design.chili.R
import com.design.chili.extensions.*
import com.design.chili.extensions.visible

class LogoTitleCellView : ConstraintLayout {

    private lateinit var view: LogoTitleCellViewVariables

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
        val view = LayoutInflater.from(context).inflate(R.layout.chili_item_logo_title, this)
        this.view = LogoTitleCellViewVariables(
            tvTitle = view.findViewById(R.id.tv_title),
            logo = view.findViewById(R.id.iv_icon),
            rootView = view.findViewById(R.id.root)
        )
        removeAllElevation()
    }

    private fun obtainAttributes(
        attrs: AttributeSet,
        defStyle: Int = R.style.Chili_CellViewStyle_LogoTitleCell
    ) {
        context?.obtainStyledAttributes(attrs, R.styleable.LogoTitleCell, R.attr.logoTitleCellDefaultStyle, defStyle)?.run {

            getResourceId(R.styleable.LogoTitleCell_android_icon, -1).let {
                if (it > -1) setLogo(it)
            }
            getString(R.styleable.LogoTitleCell_title)?.let { setTitle(it) }
            getInteger(R.styleable.LogoTitleCell_roundedCornerMode, 0).let {
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

    private fun removeAllElevation() {
        elevation = 0.0f
    }

}

private data class LogoTitleCellViewVariables(
    var tvTitle: TextView,
    var logo: ImageView,
    var rootView: ConstraintLayout
)