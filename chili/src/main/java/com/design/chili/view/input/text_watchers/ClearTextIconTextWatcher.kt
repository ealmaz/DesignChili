package com.design.chili.view.input.text_watchers

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import com.design.chili.extensions.gone
import com.design.chili.extensions.visible

class ClearTextIconTextWatcher(
    private val icon: ImageView,
    private val clearText: () -> Unit,
    private val isTextEmpty: (() -> Boolean)? = null,
) : TextWatcher {

    init {
        icon.apply {
            visibility = if (isTextEmpty?.invoke() != false) View.GONE else View.VISIBLE
            setOnClickListener { clearText() }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        when (isTextEmpty?.invoke() ?: s.isNullOrEmpty()) {
            true -> icon.gone()
            false -> icon.visible()
        }
    }
}