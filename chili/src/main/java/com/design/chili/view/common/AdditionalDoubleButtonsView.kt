package com.design.chili.view.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.StringRes
import com.design.chili.R

class AdditionalDoubleButtonsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private lateinit var view: AdditionalDoubleButtonsCellViewVariables

    init {
        initView(context)
        obtainAttributes(context, attrs)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_additional_double_buttons, this)
        this.view = AdditionalDoubleButtonsCellViewVariables(
            btnFirst = view.findViewById(R.id.btn_first),
            btnSecond = view.findViewById(R.id.btn_second)
        )
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.AdditionalDoubleButtonsView).run {
            getString(R.styleable.AdditionalDoubleButtonsView_firstButtonText)?.let {
                setFirstButtonText(it)
            }
            getString(R.styleable.AdditionalDoubleButtonsView_secondButtonText)?.let {
                setSecondButtonText(it)
            }
            recycle()
        }
    }

    fun setFirstButtonText(text: String) {
        view.btnFirst.text = text
    }
    fun setFirstButtonText(@StringRes textResId: Int) {
        view.btnFirst.setText(textResId)
    }

    fun setSecondButtonText(text: String) {
        view.btnSecond.text = text
    }
    fun setSecondButtonText(@StringRes textResId: Int) {
        view.btnSecond.setText(textResId)
    }

    fun setFirstButtonOnClickListener(onClick: () -> Unit) {
        view.btnFirst.setOnClickListener { onClick.invoke() }
    }

    fun setSecondButtonOnClickListener(onClick: () -> Unit) {
        view.btnSecond.setOnClickListener { onClick.invoke() }
    }

    fun setIsButtonsEnabled(isEnabled: Boolean){
        view.btnFirst.isEnabled = isEnabled
        view.btnSecond.isEnabled = isEnabled
    }

}

data class AdditionalDoubleButtonsCellViewVariables(
    val btnFirst: Button,
    val btnSecond: Button
)