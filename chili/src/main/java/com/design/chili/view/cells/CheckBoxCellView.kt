package com.design.chili.view.cells

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.CompoundButton
import androidx.annotation.DimenRes
import androidx.appcompat.widget.AppCompatCheckBox
import com.design.chili.R

class CheckBoxCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.checkBoxCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_BaseCellViewStyle_CheckBox
) : BaseCellView(context, attrs, defStyleAttr, defStyleRes) {

    private var checkBox: AppCompatCheckBox? = null

    override fun inflateView(context: Context) {
        super.inflateView(context)
        inflateCheckBox()
    }

    override fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        super.obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
        context.obtainStyledAttributes(attrs, R.styleable.CheckBoxCellView, defStyleAttr, defStyleRes)
            .run {
                getBoolean(R.styleable.CheckBoxCellView_isCheckBoxVisible, true).let {
                    setIsCheckBoxVisible(it)
                }
                getBoolean(R.styleable.CheckBoxCellView_isCheckBoxChecked, false).let {
                    setIsCheckBoxChecked(it)
                }
                getResourceId(R.styleable.CheckBoxCellView_checkBoxEndMargin, R.dimen.view_8dp).let {
                    setCheckBoxEndMargin(it)
                }
                recycle()
            }
    }

    private fun inflateCheckBox() {
        checkBox = AppCompatCheckBox(context)
        view.flEndPlaceholder.addView(checkBox)
    }

    fun setIsCheckBoxVisible(isVisible: Boolean) {
        checkBox?.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun setIsCheckBoxChecked(isChecked: Boolean) {
        checkBox?.isChecked = isChecked
    }

    fun setOnCheckChangeListener(listener: (CompoundButton, Boolean) -> Unit) {
        checkBox?.setOnCheckedChangeListener(listener)
    }

    fun setCheckBoxEndMargin(@DimenRes endMarginRes: Int) {
        val endMarginPx = resources.getDimensionPixelSize(endMarginRes)
        val param = view.flEndPlaceholder.layoutParams as? MarginLayoutParams ?: return
        param.marginEnd = endMarginPx
        view.flEndPlaceholder.layoutParams = param
    }
}