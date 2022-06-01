package com.design.chili.view.input

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import com.design.chili.R
import com.design.chili.view.input.text_watchers.MaskedTextWatcher
import java.lang.Exception

class MaskedInputView : BaseInputView {

    private val maskTextWatcher: MaskedTextWatcher by lazy {
        MaskedTextWatcher.Builder()
            .setFieldHintColor(hintTextColor)
            .build(this.getInputField())
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        obtainAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context,
        attrs,
        defStyle) {
        obtainAttributes(attrs)
    }

    private fun obtainAttributes(attrs: AttributeSet) {
        context?.obtainStyledAttributes(attrs, R.styleable.MaskedInputView)?.run {
            getString(R.styleable.MaskedInputView_mask)?.let {
                maskTextWatcher.setupNewMask(newMask = it)
            }
            getString(R.styleable.MaskedInputView_allowedInputSymbols).let {
                setInputAllowedSymbols(it ?: "*")
            }
            recycle()
        }
        setupFiled()
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var superState: Parcelable? = null
        (state as? Bundle)?.let { bundle ->
            maskTextWatcher.setupNewMask(newMask = bundle.getString(MASK_STATE) ?: "*")
            (bundle.getCharArray(MASK_SYMBOLS_STATE)?.toList())?.let {
                maskTextWatcher.setupNewMask(newMaskSymbols = it)
            }
            superState = bundle.getParcelable(BASE_STATE)
        }
        super.onRestoreInstanceState(superState)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val supperState = super.onSaveInstanceState()
        return Bundle().apply {
            putString(MASK_STATE, maskTextWatcher.mask)
            putCharArray(MASK_SYMBOLS_STATE, maskTextWatcher.maskSymbols.toCharArray())
            putParcelable(BASE_STATE, supperState)
        }
    }

    private fun setupFiled() {
        clearAndSetFilters(arrayOf())
        addTextWatcher(maskTextWatcher)
        maskTextWatcher.setupField()
    }

    fun setupNewMask(mask: String) {
        maskTextWatcher.setupNewMask(newMask = mask)
    }

    fun setupNewMaskSymbols(maskSymbols: List<Char>) {
        maskTextWatcher.setupNewMask(newMaskSymbols = maskSymbols)
    }

    fun changeRepresentationChar(char: Char) {
        maskTextWatcher.representation = char
        maskTextWatcher.setupField()
    }

    fun setInputAllowedSymbols(symbols: String) {
        maskTextWatcher.allowedInputSymbols = symbols
        maskTextWatcher.setupField()
    }


    override fun isInputEmpty(): Boolean {
        return try {
            maskTextWatcher.isInputEmpty()
        } catch (e: Exception) {
            return super.isInputEmpty()
        }
    }

    companion object {
        const val BASE_STATE = "baseState"
        const val MASK_STATE = "maskState"
        const val MASK_SYMBOLS_STATE = "maskSymbolsState"
    }

}