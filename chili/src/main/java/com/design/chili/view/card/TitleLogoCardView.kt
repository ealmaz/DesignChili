package com.design.chili.view.card

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.design.chili.R
import com.design.chili.extensions.setupRoundedCardCornersMode
import com.design.chili.util.IconType

class TitleLogoCardView : FrameLayout {

    private lateinit var view: TitleLogoCardViewVariables

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
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_title_logo_card, this)
        this.view = TitleLogoCardViewVariables(
            root = view.findViewById(R.id.root_view),
            title = view.findViewById(R.id.tv_title),
            logo = view.findViewById(R.id.iv_logo),
            icon = view.findViewById(R.id.iv_action_icon),
        )
    }

    private fun obtainAttributes(attrs: AttributeSet, defStyle: Int = R.style.Chili_CardViewStyle_TitleLogoCard) {
        context?.obtainStyledAttributes(attrs, R.styleable.BalanceCardView, R.attr.titleLogoCardViewDefaultStyle, defStyle)?.run {
            getString(R.styleable.BalanceCardView_title)?.let {
                setTitleText(it)
            }
            getDrawable(R.styleable.BalanceCardView_logoDrawable)?.let {
                setLogoDrawable(it)
            }
            getInt(R.styleable.BalanceCardView_iconType, -1).let {
                when (it) {
                    0 -> setIconType(IconType.PLUS)
                    1 -> setIconType(IconType.CHEVRON)
                    else -> setIconDrawable(null)
                }
            }
            getDrawable(R.styleable.BalanceCardView_iconDrawable)?.let {
                setIconDrawable(it)
            }
            getInt(R.styleable.BalanceCardView_roundedCornerMode, 0).let {
                view.root.setupRoundedCardCornersMode(it)
            }
            getBoolean(R.styleable.BalanceCardView_isSurfaceClickable, false).let {
                setIsSurfaceClickable(it)
            }
            getBoolean(R.styleable.BalanceCardView_isIconClickable, true).let {
                setIsIconClickable(it)
            }
            recycle()
        }
    }

    fun setIsSurfaceClickable(isClickable: Boolean) {
        view.root.isClickable = isClickable
        view.root.isFocusable = isClickable
        view.root.foreground = when (isClickable) {
            true -> AppCompatResources.getDrawable(context, R.drawable.chili_ripple_rounded_corner_foreground)
            else -> null
        }
    }

    fun setTitleText(text: String) {
        view.title.text = text
    }

    fun setTitleTextRes(@StringRes textResId: Int) {
        view.title.setText(textResId)
    }


    fun setLogoDrawable(drawable: Drawable?) {
        view.logo.setImageDrawable(drawable)
    }

    fun setLogoDrawableRes(@DrawableRes resId: Int) {
        view.logo.setImageResource(resId)
    }

    fun setIconDrawable(drawable: Drawable?) {
        view.icon.setImageDrawable(drawable)
    }

    fun setIconDrawableRes(@DrawableRes resId: Int) {
        view.icon.setImageResource(resId)
    }

    fun setIconType(type: IconType) {
        when (type) {
            IconType.PLUS -> setIconDrawableRes(R.drawable.chili_ic_magenta_plus)
            IconType.CHEVRON -> setIconDrawableRes(R.drawable.chili_ic_chevron_light)
        }
    }

    fun setOnCardClickListener(onClick: () -> Unit) {
        view.root.setOnClickListener { onClick.invoke() }
    }

    fun setOnIconClickListener(onClick: () -> Unit) {
        view.icon.setOnClickListener { onClick.invoke() }
    }

    fun setActionIconVisibility(isVisible: Boolean){
        view.icon.isVisible = isVisible
    }

    fun setIsIconClickable(isClickable: Boolean) {
        view.icon.isClickable = isClickable
        view.icon.isFocusable = isClickable
        if (isClickable) {
            view.icon.setBackgroundResource(R.drawable.chili_card_circle_ripple)
        }
    }
}

private data class TitleLogoCardViewVariables(
    var root: FrameLayout,
    var title: TextView,
    var logo: ImageView,
    var icon: ImageView
)