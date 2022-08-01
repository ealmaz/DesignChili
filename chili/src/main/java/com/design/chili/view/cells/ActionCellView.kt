package com.design.chili.view.cells

import android.content.Context
import android.os.Build
import android.text.Spanned
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.design.chili.R
import com.design.chili.extensions.setOnSingleClickListener

class ActionCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.actionCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_BaseCellViewStyle_Action
) : BaseCellView(context, attrs, defStyleAttr, defStyleRes) {

    private var tvAction: TextView? = null

    override fun inflateView(context: Context) {
        super.inflateView(context)
        inflateAction()
    }

    override fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        super.obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
        context.obtainStyledAttributes(attrs, R.styleable.ActionCellView, defStyleAttr, defStyleRes)
            .run {
                getString(R.styleable.ActionCellView_label)?.let {
                    setActionLabel(it)
                }
                getResourceId(R.styleable.ActionCellView_actionTextAppearance, -1).takeIf { it != -1 }?.let {
                    setActionTextAppearance(it)
                }
                getBoolean(R.styleable.ActionCellView_isActionVisible, true).let {
                    setIsActionVisible(it)
                }
                getBoolean(R.styleable.ActionCellView_isActionClickable, true).let {
                    setIsActionClickable(it)
                }
                recycle()
            }
    }

    private fun inflateAction() {
        tvAction = TextView(context).apply {
            setPadding(0, 0, resources.getDimensionPixelSize(R.dimen.padding_8dp), 0)
        }
        view.flEndPlaceholder.addView(tvAction)
    }

    fun setActionLabel(text: String?) {
        tvAction?.text = text
    }

    fun setActionLabel(spanned: Spanned) {
        tvAction?.text = spanned
    }

    fun setActionLabel(@StringRes textResId: Int) {
        tvAction?.setText(textResId)
    }

    override fun setIsChevronVisible(isVisible: Boolean) {
        super.setIsChevronVisible(isVisible)
        if (isVisible) tvAction?.setPadding(0, 0,0, 0)
    }

    fun setActionTextAppearance(@StyleRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvAction?.setTextAppearance(resId)
        } else {
            tvAction?.setTextAppearance(context, resId)
        }
    }

    fun setActionClickListener(action: () -> Unit) {
        tvAction?.setOnSingleClickListener(action)
    }

    fun setIsActionVisible(isVisible: Boolean) {
        tvAction?.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun setIsActionClickable(isClickable: Boolean) {
        tvAction?.isFocusable = isClickable
        tvAction?.isClickable = isClickable
    }
}