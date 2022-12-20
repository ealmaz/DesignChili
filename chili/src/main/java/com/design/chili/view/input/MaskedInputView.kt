package com.design.chili.view.input

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import com.design.chili.R
import com.design.chili.view.input.text_watchers.MaskedTextWatcher

class MaskedInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.maskedInputDefaultStyle,
    defStyle: Int = R.style.Chili_InputViewStyle_Masked
) : BaseInputView(context, attrs, defStyleAttr, defStyle) {

    private val maskTextWatcher: MaskedTextWatcher by lazy {
        MaskedTextWatcher.Builder()
            .setFieldHintColor(hintTextColor)
            .build(this.getInputField())
    }

    init {
        obtainAttributes(attrs, defStyleAttr, defStyle)
    }

    private fun obtainAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyle: Int) {
        context?.obtainStyledAttributes(attrs, R.styleable.MaskedInputView, defStyleAttr, defStyle)?.run {
            getString(R.styleable.MaskedInputView_mask).let {
                maskTextWatcher.setupNewMask(newMask = it.takeIf { !it.isNullOrEmpty() } ?: "*")
            }
            getString(R.styleable.MaskedInputView_allowedInputSymbols).let {
                setInputAllowedSymbols(it.takeIf { !it.isNullOrEmpty() } ?: "*")
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
        getInputField().addTextChangedListener(maskTextWatcher)
        maskTextWatcher.setupField()
    }

    override fun clearInput() {
        if (maskTextWatcher.mask.isNotBlank() && maskTextWatcher.mask != "*")
            setText(maskTextWatcher.mask)
        else super.clearInput()
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

    fun isInputMaskFilled(): Boolean {
        return !(getInputText().contains(maskTextWatcher.representation))
    }

    fun getTextWithoutMaskSymbols(): String {
        return maskTextWatcher.getTextWithoutMaskSymbols()
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