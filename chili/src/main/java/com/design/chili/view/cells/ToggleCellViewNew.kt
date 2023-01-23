package com.design.chili.view.cells

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.widget.SwitchCompat
import com.design.chili.R

class ToggleCellViewNew @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.toggleCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_BaseCellViewStyle_Toggle
) : BaseCellView(context, attrs, defStyleAttr, defStyleRes) {

    private var switch: SwitchCompat? = null

    override fun inflateView(context: Context) {
        super.inflateView(context)
        inflateSwitch()
    }

    override fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        super.obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
        context.obtainStyledAttributes(attrs, R.styleable.ToggleCellView, defStyleAttr, defStyleRes)
            .run {
                getString(R.styleable.ToggleCellView_switch_text)?.let { setSwitchText(it) }
                getString(R.styleable.ToggleCellView_switch_textOn)?.let { setSwitchTextOn(it) }
                getString(R.styleable.ToggleCellView_switch_textOff)?.let { setSwitchTextOff(it) }
                getBoolean(R.styleable.ToggleCellView_isSwitchChecked, false).let {
                    setIsSwitchChecked(it)
                }
                getBoolean(R.styleable.ToggleCellView_isSwitchVisible, true).let {
                    setIsSwitchVisible(it)
                }
                view.rootView.setBackgroundResource(R.drawable.chili_card_rounded_border)
                recycle()
        }
    }

    private fun inflateSwitch() {
        this.switch = SwitchCompat(context)
        view.flEndPlaceholder.addView(switch)
    }

    fun setIsSwitchVisible(isVisible: Boolean) {
        switch?.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun setIsSwitchChecked(isChecked: Boolean) {
        switch?.isChecked = isChecked
    }

    fun setOnCheckChangeListener(listener: (CompoundButton, Boolean) -> Unit) {
        switch?.setOnCheckedChangeListener(listener)
    }

    fun setSwitchText(text: String) {
        switch?.text = text
    }

    fun setSwitchTextOn(text: String) {
        switch?.apply {
            showText = true
            textOn = text
        }
    }

    fun setSwitchTextOff(text: String) {
        switch?.apply {
            showText = true
            textOff = text
        }
    }

    fun setIsEnabled(isEnabled: Boolean) {
        switch?.isEnabled = isEnabled
    }
}