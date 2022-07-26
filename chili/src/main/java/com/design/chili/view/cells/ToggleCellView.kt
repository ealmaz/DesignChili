package com.design.chili.view.cells

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.widget.SwitchCompat
import com.design.chili.R

open class ToggleCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.cellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_BaseCellViewStyle
) : BaseCellView(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var switch: SwitchCompat

    override fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        super.obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
        setupViews()
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
                recycle()
        }
    }

    private fun setupViews() {
        addSwitch()
        setIsChevronVisible(false)
    }

    private fun addSwitch() {
        val switch = SwitchCompat(context)
        switch.tag = "SWITCH"
        view.flEndPlaceholder.addView(switch)
        this.switch = view.flEndPlaceholder.findViewWithTag(switch.tag)
    }

    fun setIsSwitchVisible(isVisible: Boolean) {
        switch.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun setIsSwitchChecked(isChecked: Boolean) {
        switch.isChecked = isChecked
    }

    fun setOnCheckChangeListener(listener: (CompoundButton, Boolean) -> Unit) {
        switch.setOnCheckedChangeListener(listener)
    }

    fun setSwitchText(text: String) {
        switch.text = text
    }

    fun setSwitchTextOn(text: String) {
        switch.showText = true
        switch.textOn = text
    }

    fun setSwitchTextOff(text: String) {
        switch.showText = true
        switch.textOff = text
    }
}