package com.design.chili.view.cells

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.design.chili.R
import com.design.chili.extensions.drawable
import com.design.chili.extensions.setImageByUrl
import com.design.chili.extensions.setOnSingleClickListener

class EndIconCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.endIconCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_BaseCellViewStyle_EndIcon
) : BaseCellView(context, attrs, defStyleAttr, defStyleRes) {

    private var endIcon: ImageView? = null

    override fun inflateView(context: Context) {
        super.inflateView(context)
        inflateEndIcon()
    }

    override fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        super.obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
        context.obtainStyledAttributes(attrs, R.styleable.EndIconCellView, defStyleAttr, defStyleRes)
            .run {
                getResourceId(R.styleable.EndIconCellView_endIcon, -1).let {
                    if (it > -1) setEndIcon(it)
                }
                getBoolean(R.styleable.EndIconCellView_isEndIconVisible, true).let {
                    setIsEndIconVisible(it)
                }
                getBoolean(R.styleable.EndIconCellView_isEndIconClickable, true).let {
                    setIsEndIconClickable(it)
                }
                getResourceId(R.styleable.EndIconCellView_endIconSize, R.dimen.view_24dp).let {
                    setEndIconSize(it, it)
                }
                getResourceId(R.styleable.EndIconCellView_endIconEndMargin, R.dimen.view_8dp).let {
                    setEndIconEndMargin(it)
                }
                recycle()
            }
    }

    private fun inflateEndIcon() {
        this.endIcon = ImageView(context)
        view.flEndPlaceholder.addView(endIcon)
    }

    fun setEndIcon(endIcon: Any?) {
        when (endIcon) {
            is String -> setEndIcon(endIcon)
            is Int -> setEndIcon(endIcon)
            is Drawable -> setEndIcon(endIcon)
        }
    }

    fun setEndIcon(@DrawableRes drawableRes: Int?) {
        drawableRes?.let {
            endIcon?.setImageDrawable(context.drawable(drawableRes))
        }
    }

    fun setEndIcon(drawable: Drawable?) {
        drawable?.let {
            endIcon?.setImageDrawable(drawable)
        }
    }

    fun setEndIcon(url: String?) {
        url?.let {
            endIcon?.setImageByUrl(url)
        }
    }

    fun setIsEndIconVisible(isVisible: Boolean) {
        endIcon?.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun setIsEndIconClickable(isClickable: Boolean) {
        //TODO check animation
        endIcon?.isFocusable = isClickable
        endIcon?.isClickable = isClickable
    }

    fun setEndIconClickListener(action: () -> Unit) {
        endIcon?.setOnSingleClickListener(action)
    }

    fun setEndIconSize(@DimenRes widthDimenRes: Int, @DimenRes heightDimenRes: Int) {
        val widthPx = resources.getDimensionPixelSize(widthDimenRes)
        val heightPx = resources.getDimensionPixelSize(heightDimenRes)
        setupEndIconSize(widthPx, heightPx)
    }

    private fun setupEndIconSize(widthPx: Int, heightPx: Int) {
        val params = endIcon?.layoutParams
        params?.height = heightPx
        params?.width = widthPx
        endIcon?.layoutParams = params
    }

    fun setEndIconEndMargin(@DimenRes endMarginRes: Int) {
        val endMarginPx = resources.getDimensionPixelSize(endMarginRes)
        val param = view.flEndPlaceholder.layoutParams as? MarginLayoutParams ?: return
        param.marginEnd = endMarginPx
        view.flEndPlaceholder.layoutParams = param
    }
}