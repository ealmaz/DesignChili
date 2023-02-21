package com.design.chili.view.input

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.design.chili.R
import com.design.chili.extensions.getColorFromAttr

class FilledSingleSelectorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.inputViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_InputViewStyle
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes)  {

    lateinit var view: FilledSingleSelectorViewVariables

    init {
        initView(context)
        obtainAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_filled_single_selector, this)
        this.view = FilledSingleSelectorViewVariables(
            rootView = view.findViewById(R.id.rl_container),
            tvInput = view.findViewById(R.id.tv_input)
        )
    }

    private fun obtainAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyle: Int) {
        context?.obtainStyledAttributes(attrs, R.styleable.FilledSingleSelectorView, defStyleAttr, defStyle)?.run {
            getString(R.styleable.FilledSingleSelectorView_android_hint)?.let { hint ->
                setHint(hint)
            }
            getString(R.styleable.FilledSingleSelectorView_android_text)?.let { text ->
                setText(text)
            }
            recycle()
        }
    }

    fun getInputText(): String {
        return when {
            !isInputEmpty() -> view.tvInput.text.toString()
            else -> ""
        }
    }

    fun isInputEmpty() = view.tvInput.currentTextColor == context.getColorFromAttr(R.attr.ChiliInputViewHintTextColor)

    fun setHint(hint: String?) {
        this.tag = hint
        view.tvInput.hint = hint
        view.tvInput.setHintTextColor(context.getColorFromAttr(R.attr.ChiliInputViewHintTextColor))
    }

    fun setText(label: String?) {
        view.tvInput.run {
            text = label
            setTextColor(context.getColorFromAttr(R.attr.ChiliPrimaryTextColor))
        }
    }

    fun resetText() {
        setText("")
        setHint(tag.toString())
    }
}

data class FilledSingleSelectorViewVariables(
    val rootView: RelativeLayout,
    val tvInput: AppCompatTextView
)