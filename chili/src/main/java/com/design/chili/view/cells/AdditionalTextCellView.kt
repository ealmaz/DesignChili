package com.design.chili.view.cells

import android.content.Context
import android.os.Build
import android.text.Spanned
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.design.chili.R

class AdditionalTextCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.additionalTextCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_BaseCellViewStyle_AdditionalText
) : BaseCellView(context, attrs, defStyleAttr, defStyleRes) {

    private var additionalText: TextView? = null

    override fun inflateView(context: Context) {
        super.inflateView(context)
        inflateAdditionalText()
    }

    override fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        super.obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
        context.obtainStyledAttributes(attrs, R.styleable.AdditionalTextCellView, defStyleAttr, defStyleRes)
            .run {
                getString(R.styleable.AdditionalTextCellView_additionalText)?.let {
                    setAdditionalText(it)
                }
                getResourceId(R.styleable.AdditionalTextCellView_additionalTextTextAppearance, -1).takeIf { it != -1 }?.let {
                    setAdditionalTextTextAppearance(it)
                }
                recycle()
            }
    }

    private fun inflateAdditionalText() {
        this.additionalText = TextView(context).apply {
            setPadding(0, 0, resources.getDimensionPixelSize(R.dimen.padding_8dp), 0)
        }
        view.flEndPlaceholder.addView(additionalText)
    }

    fun setAdditionalText(text: String?) {
        additionalText?.text = text
    }

    fun setAdditionalText(spanned: Spanned) {
        additionalText?.text = spanned
    }

    fun setAdditionalText(@StringRes textResId: Int) {
        additionalText?.setText(textResId)
    }

    override fun setIsChevronVisible(isVisible: Boolean) {
        super.setIsChevronVisible(isVisible)
        if (isVisible) additionalText?.setPadding(0, 0,0, 0)
    }

    fun setAdditionalTextTextAppearance(@StyleRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            additionalText?.setTextAppearance(resId)
        } else {
            additionalText?.setTextAppearance(context, resId)
        }
    }
}