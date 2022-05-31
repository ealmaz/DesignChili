package com.design.chili.view.input

import android.content.Context
import android.util.AttributeSet

class SelectionEditText(context: Context, attributeSet: AttributeSet) : androidx.appcompat.widget.AppCompatEditText(context, attributeSet) {

    var startSelectionLimit = 0
    var endSelectionLimit = -1

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        when {
            selStart == selEnd && selStart < startSelectionLimit -> {
                setSelection(startSelectionLimit)
            }
            selStart == selEnd && endSelectionLimit > -1 && selEnd > endSelectionLimit -> {
                setSelection(endSelectionLimit)
            }
            else -> super.onSelectionChanged(selStart, selEnd)
        }
    }

    override fun setSelection(index: Int) {
        super.setSelection(index.takeIf { it <= length() && it >= 0} ?: length())
    }
}