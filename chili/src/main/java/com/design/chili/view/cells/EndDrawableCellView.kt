package com.design.chili.view.cells

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import com.design.chili.R
import com.design.chili.extensions.drawable
import com.design.chili.extensions.setImageByUrl
import com.design.chili.extensions.setOnSingleClickListener

class EndDrawableCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.endDrawableCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_BaseCellViewStyle_EndDrawable
) : BaseCellView(context, attrs, defStyleAttr, defStyleRes) {

    private var endDrawable: ImageView? = null

    override fun inflateView(context: Context) {
        super.inflateView(context)
        inflateEndDrawable()
    }

    override fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        super.obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
        context.obtainStyledAttributes(attrs, R.styleable.EndDrawableCellView, defStyleAttr, defStyleRes)
            .run {
                getResourceId(R.styleable.EndDrawableCellView_endDrawable, -1).let {
                    if (it > -1) setEndDrawable(it)
                }
                getBoolean(R.styleable.EndDrawableCellView_isEndDrawableVisible, true).let {
                    setIsEndDrawableVisible(it)
                }
                getBoolean(R.styleable.EndDrawableCellView_isEndDrawableClickable, true).let {
                    setIsEndDrawableClickable(it)
                }
                getResourceId(R.styleable.EndDrawableCellView_endDrawableSize, R.dimen.view_24dp).let {
                    setEndDrawableSize(it, it)
                }
                getDimensionPixelSize(R.styleable.EndDrawableCellView_endDrawableEndMargin, 20).let {
                    updateEndMargin(it)
                }
                recycle()
            }
    }

    private fun inflateEndDrawable() {
        this.endDrawable = ImageView(context)
        view.flEndPlaceholder.addView(endDrawable)
    }

    fun setEndDrawable(endDrawable: Any?) {
        when (endDrawable) {
            is String -> setEndDrawable(endDrawable)
            is Int -> setEndDrawable(endDrawable)
        }
    }

    fun setEndDrawable(@DrawableRes drawableRes: Int?) {
        drawableRes?.let {
            endDrawable?.setImageDrawable(context.drawable(drawableRes))
        }
    }

    fun setEndDrawable(url: String?) {
        url?.let {
            endDrawable?.setImageByUrl(url)
        }
    }

    fun setIsEndDrawableVisible(isVisible: Boolean) {
        endDrawable?.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun setIsEndDrawableClickable(isClickable: Boolean) {
        endDrawable?.isClickable = isClickable
    }

    fun setEndDrawableClickListener(action: () -> Unit) {
        endDrawable?.setOnSingleClickListener(action)
    }

    fun setEndDrawableSize(@DimenRes widthDimenRes: Int, @DimenRes heightDimenRes: Int) {
        val widthPx = resources.getDimensionPixelSize(widthDimenRes)
        val heightPx = resources.getDimensionPixelSize(heightDimenRes)
        setupEndDrawableSize(widthPx, heightPx)
    }

    private fun setupEndDrawableSize(widthPx: Int, heightPx: Int) {
        val params = endDrawable?.layoutParams
        params?.height = heightPx
        params?.width = widthPx
        endDrawable?.layoutParams = params
    }

    fun updateEndMargin(endMarginPx: Int) {
        val param = view.flEndPlaceholder.layoutParams as? MarginLayoutParams ?: return
        param.marginEnd = endMarginPx
        view.flEndPlaceholder.layoutParams = param
    }
}