package com.design.chili.view.common

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StyleRes
import com.design.chili.R

class AdditionalTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private val tvText: TextView

    init {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.chili_view_additional_text, this)
        tvText = view.findViewById(R.id.tv_text)
        obtainAttributes(context, attrs)
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.AdditionalTextView).run {
            setText(getString(R.styleable.AdditionalTextView_android_text))
            getResourceId(R.styleable.AdditionalTextView_android_textAppearance, -1).takeIf { it != -1 }?.let {
                setTextAppearance(it)
            }
            recycle()
        }
    }

    fun setText(text: String?) {
        tvText.text = text
    }

    fun setText(textResId: Int) {
        tvText.setText(textResId)
    }

    fun setTextAppearance(@StyleRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvText.setTextAppearance(resId)
        } else {
            tvText.setTextAppearance(context, resId)
        }
    }
}

