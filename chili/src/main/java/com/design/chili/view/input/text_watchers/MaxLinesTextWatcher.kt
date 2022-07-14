package com.design.chili.view.input.text_watchers

import android.text.Editable
import android.text.TextWatcher
import com.design.chili.view.input.SelectionEditText

class MaxLinesTextWatcher(
    private val field: SelectionEditText,
    private val NumberOfMaxLines: Int
) : TextWatcher {

    private var text: String? = null
    private var beforeCursorPosition = 0

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        text = s.toString()
        beforeCursorPosition = after
    }

    override fun afterTextChanged(s: Editable) {
        if (field.lineCount > NumberOfMaxLines) {
            field.setText(text)
            field.setSelection(beforeCursorPosition)
        }
    }
}