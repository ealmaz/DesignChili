package com.design.chili.view.input.text_watchers

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView

class ClearTextIconTextWatcher(
    private val icon: ImageView,
    private val clearText: () -> Unit,
    private val isTextEmpty: (() -> Boolean) = { false },
    private val isFieldEnabled: (() -> Boolean) = { true },
) : TextWatcher {

    init {
        icon.setOnClickListener { clearText() }
        setupClearIconVisibility()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        setupClearIconVisibility()
    }

    private fun setupClearIconVisibility() {
        icon.visibility = when (!isTextEmpty() && isFieldEnabled()) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }
}