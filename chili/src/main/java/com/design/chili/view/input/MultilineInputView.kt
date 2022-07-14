package com.design.chili.view.input

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import com.design.chili.R
import com.design.chili.view.input.text_watchers.LimitedInputLinesWatcher

class MultilineInputView : BaseInputView {

    private val limitedInputLinesWatcher: LimitedInputLinesWatcher by lazy {
        LimitedInputLinesWatcher(this.getInputField())
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        obtainAttributes(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        obtainAttributes(attrs)
    }

    private fun obtainAttributes(attrs: AttributeSet) {
        context?.obtainStyledAttributes(attrs, R.styleable.MultilineInputView, R.attr.multilineInputDefaultStyle, R.style.InputViewStyle_Simple)?.run {
            getInteger(R.styleable.MultilineInputView_maxLength, -1)
                .takeIf { it != -1 }?.let { setMaxLength(it) }
            getInteger(R.styleable.MultilineInputView_maxLines, -1)
                .takeIf { it != -1 }?.let { limitedInputLinesWatcher.setLimit(it) }
            getInteger(R.styleable.MultilineInputView_minLines, -1)
                .takeIf { it != -1 }?.let { setMinLines(it) }

            setupViews()
            recycle()
        }
    }

    private fun setupViews() {
        setGravity(Gravity.LEFT)
        hideAction()
        hideMessage()
        enableMultiline()
        view.inputField.addTextChangedListener(limitedInputLinesWatcher)
    }

    private fun enableMultiline() {
        view.inputField.isSingleLine = false
    }

    private fun setMinLines(linesCount: Int) {
        view.inputField.minLines = linesCount
    }
}