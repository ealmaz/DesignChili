package com.design.chili.view.cells

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.design.chili.R
import com.design.chili.extensions.*
import com.design.chili.util.IconSize
import com.design.chili.util.RoundedCornerMode
import com.design.chili.view.image.SquircleView

open class BaseCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.cellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_BaseCellViewStyle
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    lateinit var view: BaseCellViewVariables

    init {
        inflateView(context)
        obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
    }

    protected open fun inflateView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_base_cell, this)
        this.view = BaseCellViewVariables(
            flStartPlaceholder = view.findViewById(R.id.fl_start_place_holder),
            flEndPlaceholder = view.findViewById(R.id.fl_end_place_holder),
            ivIcon = view.findViewById(R.id.iv_icon),
            tvTitle = view.findViewById(R.id.tv_title),
            tvSubtitle = view.findViewById(R.id.tv_subtitle),
            divider = view.findViewById(R.id.divider),
            rootView = view.findViewById(R.id.root_view),
            chevron = view.findViewById(R.id.iv_chevron)
        )
    }

    protected open fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.BaseCellView, defStyleAttr, defStyleRes)
            .run {
                getResourceId(R.styleable.BaseCellView_android_icon, -1).takeIf { it != -1 }?.let {
                    setIcon(it)
                }
                getString(R.styleable.BaseCellView_title)?.let { setTitle(it) }
                getString(R.styleable.BaseCellView_subtitle)?.let { setSubtitle(it) }
                getInteger(R.styleable.BaseCellView_roundedCornerMode, 0).let {
                    view.rootView.setupRoundedCellCornersMode(it)
                }
                getBoolean(R.styleable.BaseCellView_isDividerVisible, true).let {
                    setDividerVisibility(it)
                }
                getLayoutDimension(R.styleable.BaseCellView_cellIconSize, IconSize.SMALL.value).let {
                    when (it) {
                        IconSize.SMALL.value -> setIconSize(IconSize.SMALL)
                        IconSize.MEDIUM.value -> setIconSize(IconSize.MEDIUM)
                        IconSize.LARGE.value -> setIconSize(IconSize.LARGE)
                        else -> setupIconSize(it,it)
                    }
                }
                getBoolean(R.styleable.BaseCellView_isSurfaceClickable, true).let {
                    setIsSurfaceClickable(it)
                }
                getDimensionPixelSize(R.styleable.BaseCellView_cellIconVerticalMargin, -1).takeIf { it != -1 }?.let {
                    updateIconMargin(topMarginPx = it, bottomMarginPx = it)
                }
                getColor(R.styleable.BaseCellView_squircleIconBackgroundColor, -1).takeIf { it != -1 }?.let {
                    setSquircleIconBackgroundColor(it)
                }
                getBoolean(R.styleable.BaseCellView_isChevronVisible, true).let {
                    setIsChevronVisible(it)
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

    fun setSubtitle(text: String?) {
        view.tvSubtitle.setTextOrHide(text)
    }

    fun setSubtitle(@StringRes resId: Int) {
        view.tvSubtitle.setTextOrHide(resId)
    }

    fun setIcon(@DrawableRes drawableRes: Int) {
        view.ivIcon.apply {
            visible()
            setImageResource(drawableRes)
        }
    }

    fun setIcon(drawable: Drawable) {
        view.ivIcon.apply {
            visible()
            setImageDrawable(drawable)
        }
    }

    fun getIconView(): SquircleView {
        return view.ivIcon
    }

    fun setSquircleIconBackgroundColor(@ColorInt color: Int) {
        view.ivIcon.squircleBackgroundColor = color
    }

    fun setDividerVisibility(isVisible: Boolean) {
        view.divider.apply {
            when(isVisible) {
                true -> visible()
                else -> gone()
            }
        }
    }

    fun getDividerView(): View {
        return view.divider
    }

    fun loadIcon(url: String?) {
        url?.let {
            view.ivIcon.run {
                setImageByUrl(url)
                visible()
            }
        }
    }

    fun updateIconMargin(startMarginPx: Int? = null, topMarginPx: Int? = null, endMarginPx: Int? = null, bottomMarginPx: Int? = null) {
        val param = view.ivIcon.layoutParams as? MarginLayoutParams ?: return
        param.apply {
            leftMargin = startMarginPx ?: leftMargin
            topMargin = topMarginPx ?: topMargin
            rightMargin = endMarginPx ?: rightMargin
            bottomMargin = bottomMarginPx ?: bottomMargin
        }
        view.ivIcon.layoutParams = param
    }

    fun setIconSize(iconSize: IconSize) {
        val margin14px = resources.getDimensionPixelSize(R.dimen.padding_14dp)
        val margin8px = resources.getDimensionPixelSize(R.dimen.padding_8dp)
        val size = when(iconSize) {
            IconSize.LARGE -> {
                updateIconMargin(topMarginPx = margin14px, bottomMarginPx = margin14px)
                R.dimen.view_48dp
            }
            IconSize.MEDIUM -> {
                updateIconMargin(topMarginPx = margin14px, bottomMarginPx = margin14px)
                R.dimen.view_46dp
            }
            IconSize.SMALL -> {
                updateIconMargin(topMarginPx = margin8px, bottomMarginPx = margin8px)
                R.dimen.view_32dp
            }
        }
        setIconSize(size, size)
    }


    fun setIconSize(@DimenRes widthDimenRes: Int, @DimenRes heightDimenRes: Int) {
        val widthPx = resources.getDimensionPixelSize(widthDimenRes)
        val heightPx = resources.getDimensionPixelSize(heightDimenRes)
        setupIconSize(widthPx, heightPx)
    }

    private fun setupIconSize(widthPx: Int, heightPx: Int) {
        val params = view.ivIcon.layoutParams
        params.height = heightPx
        params.width = widthPx
        view.ivIcon.layoutParams = params
    }

    fun setRoundedMode(mode: RoundedCornerMode) {
        view.rootView.setupRoundedCellCornersMode(mode.value)
    }

    fun setIsSurfaceClickable(isSurfaceClickable: Boolean) {
        view.rootView.setIsSurfaceClickable(isSurfaceClickable)
    }

    open fun setIsChevronVisible(isVisible: Boolean) {
        view.chevron.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }
}

data class BaseCellViewVariables(
    var flStartPlaceholder: FrameLayout,
    var flEndPlaceholder: FrameLayout,
    var ivIcon: SquircleView,
    var tvTitle: TextView,
    var tvSubtitle: TextView,
    var divider: View,
    var rootView: ConstraintLayout,
    var chevron: ImageView
)