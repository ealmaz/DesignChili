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
import com.design.chili.R

class BalanceCardView : FrameLayout {

    private lateinit var view: BalanceCardViewVariables

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
        val view = LayoutInflater.from(context).inflate(R.layout.view_balance_card, this)
        this.view = BalanceCardViewVariables(
            root = view.findViewById(R.id.root_view),
            title = view.findViewById(R.id.tv_title),
            value = view.findViewById(R.id.tv_value),
            icon = view.findViewById(R.id.iv_action_icon),
        )
    }

    private fun obtainAttributes(attrs: AttributeSet, defStyle: Int = R.style.CardViewStyle_BalanceCard) {
        context?.obtainStyledAttributes(attrs, R.styleable.BalanceCardView, R.attr.balanceCardViewDefaultStyle, defStyle)?.run {
            getString(R.styleable.BalanceCardView_title)?.let {
                setTitleText(it)
            }
            getString(R.styleable.BalanceCardView_value)?.let {
                setValue(it)
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
                when (it) {
                    0 -> setupRoundedCornersMode(RoundedCornerMode.SINGLE)
                    1 -> setupRoundedCornersMode(RoundedCornerMode.TOP)
                    2 -> setupRoundedCornersMode(RoundedCornerMode.MIDDLE)
                    3 -> setupRoundedCornersMode(RoundedCornerMode.BOTTOM)
                }
            }
            getBoolean(R.styleable.BalanceCardView_isSurfaceClickable, false).let {
                setIsSurfaceClickable(it)
            }
            recycle()
        }
    }

    fun setIsSurfaceClickable(isClickable: Boolean) {
        view.root.isClickable = isClickable
        view.root.isFocusable = isClickable
        view.root.foreground = when (isClickable) {
            true -> AppCompatResources.getDrawable(context, R.drawable.ripple_rounded_corner_foreground)
            else -> null
        }
    }

    fun setupRoundedCornersMode(mode: RoundedCornerMode) {
        view.root.setBackgroundResource(
            when (mode) {
                RoundedCornerMode.TOP -> R.drawable.card_rounded_top_background
                RoundedCornerMode.MIDDLE -> R.drawable.card_rounded_middle_background
                RoundedCornerMode.BOTTOM -> R.drawable.card_rounded_bottom_background
                else -> R.drawable.card_rounded_background
            }
        )
    }

    fun setTitleText(text: String) {
        view.title.text = text
    }

    fun setTitleTextRes(@StringRes textResId: Int) {
        view.title.setText(textResId)
    }

    fun setValue(value: String) {
        view.value.text = value
    }

    fun setValueTextRes(@StringRes textResId: Int) {
        view.value.setText(textResId)
    }

    fun setIconDrawable(drawable: Drawable?) {
        view.icon.setImageDrawable(drawable)
    }

    fun setIconDrawableRes(@DrawableRes resId: Int) {
        view.icon.setImageResource(resId)
    }

    fun setIconType(type: IconType) {
        when (type) {
            IconType.PLUS -> setIconDrawableRes(R.drawable.ic_magenta_plus)
            IconType.CHEVRON -> setIconDrawableRes(R.drawable.ic_chevron)
        }
    }

    fun setOnCardClickListener(onClick: () -> Unit) {
        view.root.setOnClickListener { onClick.invoke() }
    }

    fun setOnIconClickListener(onClick: () -> Unit) {
        view.icon.setOnClickListener { onClick.invoke() }
    }

}

enum class IconType {
    PLUS, CHEVRON
}

enum class RoundedCornerMode {
    SINGLE, TOP, MIDDLE, BOTTOM
}

data class BalanceCardViewVariables(
    var root: FrameLayout,
    var title: TextView,
    var value: TextView,
    var icon: ImageView
)