package com.design.chili.view.input.text_watchers

import android.text.Editable
import android.text.TextWatcher

class SimpleTextWatcher(
    private val beforeTextChanged: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null,
    private val onTextChanged: ((s: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null,
    private val afterTextChanged: ((s: Editable?) -> Unit)? = null,
    private val onTextChangedListener: ((String?) -> Unit)? = null
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged?.invoke(s, start, before, count)
        onTextChangedListener?.invoke(s?.toString())
    }

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged?.invoke(s)
    }
}