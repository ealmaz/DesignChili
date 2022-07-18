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
import androidx.constraintlayout.widget.ConstraintLayout
import com.design.chili.R

class NavigatorCardView : ConstraintLayout {

    private lateinit var view: NavigatorCardViewVariables

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
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_navigator_card, this)
        this.view = NavigatorCardViewVariables(
            root = view.findViewById(R.id.root_view),
            ivIcon = view.findViewById(R.id.iv_icon),
            tvTitle = view.findViewById(R.id.tv_title),
            ivCount = view.findViewById(R.id.iv_count)
        )
    }

    private fun obtainAttributes(
        attrs: AttributeSet,
        defStyle: Int = R.style.Chili_CardViewStyle_NavigatorCardView,
    ) {
        context?.obtainStyledAttributes(attrs, R.styleable.NavigatorCardView, R.attr.navigatorCardViewDefaultStyle, defStyle)?.run {

            getString(R.styleable.NavigatorCardView_title)?.let {
                setTitle(it)
            }

            getDrawable(R.styleable.NavigatorCardView_cardIcon).let {
                setIconDrawable(it)
            }

            getDrawable(R.styleable.NavigatorCardView_countDrawable).let {
                setCountDrawable(it)
            }
            getBoolean(R.styleable.NavigatorCardView_isSurfaceClickable, true).let {
                setIsSurfaceClickable(it)
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


    fun setTitle(text: String) {
        view.tvTitle.text = text
    }

    fun setTitle(@StringRes textResId: Int) {
        view.tvTitle.setText(textResId)
    }

    fun setIconDrawable(drawable: Drawable?) {
        view.ivIcon.setImageDrawable(drawable)
    }

    fun setIconDrawable(@DrawableRes resId: Int) {
        view.ivIcon.setImageResource(resId)
    }

    fun setCountDrawable(drawable: Drawable?) {
        view.ivCount.setImageDrawable(drawable)
    }

    fun setCountDrawable(@DrawableRes resId: Int) {
        view.ivCount.setImageResource(resId)
    }

    fun getIconImageView(): ImageView {
        return view.ivIcon
    }

    fun getCountImageView(): ImageView {
        return view.ivCount
    }
}

private data class NavigatorCardViewVariables(
    var root: FrameLayout,
    var ivIcon: ImageView,
    var tvTitle: TextView,
    var ivCount: ImageView,
)