package com.design.chili.view.input.text_watchers

import android.graphics.Color
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import com.design.chili.view.input.SelectionEditText

class MaskedTextWatcher private constructor(
    val field: SelectionEditText,
    val hintTextColor: Int,
    val representation: Char,
    val mask: String,
    val maskSymbols: List<Char>) : TextWatcher {

    init {
        field.startSelectionLimit = mask.indexOfFirst { it == representation }
    }

    private var isEditing = false

    private var selectionPosition = mask.indexOf(representation)


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        if (isEditing) return
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (isEditing) return
        isEditing = true
        selectionPosition = field.selectionStart
        val inputText = StringBuilder(field.text.toString())
        val clearedText = clearMaskSymbols(inputText.toString())
        val maskedText = mergeStrings(clearedText, before > count)
        val ssb = SpannableStringBuilder(maskedText)
        val lastMaskSym = maskedText.indexOfFirst { it == representation }.takeIf { it != -1 } ?: maskedText.length
        ssb.setSpan(ForegroundColorSpan(hintTextColor), lastMaskSym, maskedText.length, 0)
        field.text = ssb
        field.endSelectionLimit = lastMaskSym
        field.setSelection(selectionPosition)
        isEditing = false
    }

    override fun afterTextChanged(s: Editable?) {
        if (isEditing) return
    }

    fun clearMaskSymbols(text: String): String {
        return text.filter { !maskSymbols.contains(it) && it != representation }
    }

    fun mergeStrings(inputText: String, isDelete: Boolean): String {
        val maskedText = StringBuilder()
        var maskIndex = 0
        var textIndex = 0
        while (true) {
            when {
                textIndex >= inputText.length && maskIndex >= mask.length -> break
                textIndex < inputText.length && maskIndex >= mask.length -> break
                textIndex >= inputText.length && maskIndex < mask.length -> {
                    maskedText.append(mask.subSequence(maskIndex, mask.length))
                    break
                }
            }
            when {
                mask[maskIndex] == representation -> {
                    maskedText.append(inputText[textIndex])
                    textIndex++
                    maskIndex++
                }
                mask[maskIndex] == inputText[textIndex] -> {
                    maskedText.append(inputText[textIndex])
                    textIndex++
                    maskIndex++
                }
                else -> {
                    val s = mask[maskIndex]
                    maskedText.append(s)
                    if (maskIndex + 1 == selectionPosition && !isDelete) {
                        selectionPosition++
                    }
                    maskIndex++
                }
            }
        }
        return maskedText.toString()
    }

    fun setupField() {
        field.setText(mask)
    }

    fun isInputEmpty(): Boolean {
        field.text.toString().forEachIndexed { index, c ->
            if (c != mask[index]) return false
        }
        return true
    }

    class Builder {

        private var representation = 'X'

        private var mask = "+996 XXX XXX - XXX"

        private var maskSymbols = listOf('-', ' ')

        private var hintTextColor: Int = Color.GRAY

        fun setRepresentationChar(representation: Char): Builder {
            this.representation = representation
            return this
        }

        fun setInputMask(mask: String): Builder {
            this.mask = mask
            return this
        }

        fun setInputMaskSymbols(maskSymbols: List<Char>): Builder {
            this.maskSymbols = maskSymbols
            return this
        }

        fun setFieldHintColor(@ColorInt color: Int): Builder {
            this.hintTextColor = color
            return this
        }

        fun build(field: SelectionEditText): MaskedTextWatcher {
            return MaskedTextWatcher(field, hintTextColor, representation, mask, maskSymbols)
        }
    }
}
