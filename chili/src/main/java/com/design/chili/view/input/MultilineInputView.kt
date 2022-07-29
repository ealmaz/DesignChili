package com.design.chili.view.input

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import com.design.chili.R
import com.design.chili.view.input.text_watchers.LimitedInputLinesWatcher

class MultilineInputView : BaseInputView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        obtainAttributes(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        obtainAttributes(attrs)
    }

    private fun obtainAttributes(attrs: AttributeSet) {
        context?.obtainStyledAttributes(attrs, R.styleable.MultilineInputView, R.attr.multilineInputDefaultStyle, R.style.Chili_InputViewStyle_Simple)?.run {
            getInteger(R.styleable.MultilineInputView_maxLength, -1)
                .takeIf { it != -1 }?.let { setMaxLength(it) }
            getInteger(R.styleable.MultilineInputView_maxInputLines, -1)
                .takeIf { it != -1 }?.let { setMaxInputLines(it) }
            getInteger(R.styleable.MultilineInputView_android_maxLines, -1)
                .takeIf { it != -1 }?.let { setMaxLines(it) }
            getInteger(R.styleable.MultilineInputView_minLines, -1)
                .takeIf { it != -1 }?.let { setMinLines(it) }
            getBoolean(R.styleable.MultilineInputView_android_singleLine, false).let {
                setSingleLine(it)
            }
            setupViews()
            recycle()
        }
    }

    private fun setupViews() {
        setGravity(Gravity.LEFT)
        hideAction()
        hideMessage()
    }

    fun setMaxLines(linesCount: Int) {
        view.inputField.maxLines = linesCount
    }

    fun setMaxInputLines(linesCount: Int) {
        view.inputField.addTextChangedListener(
            LimitedInputLinesWatcher(view.inputField, linesCount)
        )
    }

    fun setMinLines(linesCount: Int) {
        view.inputField.minLines = linesCount
    }
}