package com.design.chili.view.card

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.design.chili.R
import com.design.chili.extensions.gone
import com.design.chili.extensions.visible

class PhoneNumberPickerCardView : ConstraintLayout {

    private lateinit var view: PhoneNumberPickerCardViewVariables

    constructor(context: Context) : super(context) {
        inflateViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflateViews()
        obtainAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        inflateViews()
        obtainAttributes(attrs, defStyle)
    }

    private fun inflateViews() {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_phone_number_picker_card, this)
        this.view = PhoneNumberPickerCardViewVariables(
            tvTitle = view.findViewById(R.id.tv_title),
            ivTitleImage = view.findViewById(R.id.iv_title_icon),
            npPhonePicker = view.findViewById(R.id.np_phone_number_picker),
            btnPrimary = view.findViewById(R.id.btn_primary),
            btnSecondary = view.findViewById(R.id.btn_secondary),
        )
    }

    private fun obtainAttributes(attrs: AttributeSet, defStyle: Int = R.style.Chili_CardViewStyle_PhoneNumberPickerCardViewStyle) {
        context?.obtainStyledAttributes(attrs, R.styleable.PhoneNumberPickerCardView, R.attr.phoneNumberPickerCardViewDefaultStyle, defStyle)?.run {

            getString(R.styleable.PhoneNumberPickerCardView_title)?.let {
                setTitle(it)
            }

            getDrawable(R.styleable.PhoneNumberPickerCardView_titleIcon).let {
                setTitleImage(it)
            }

            getString(R.styleable.PhoneNumberPickerCardView_primaryButtonText)?.let {
                setPickNumberButtonText(it)
            }

            getString(R.styleable.PhoneNumberPickerCardView_secondaryButtonText)?.let {
                setSecondaryButtonText(it)
            }

            recycle()
        }
    }

    fun setupView(displayedItems: List<String>, onEmptyListSecondaryButtonText: String) {
        when (displayedItems.isEmpty()) {
            true -> {
                view.npPhonePicker.gone()
                view.btnPrimary.gone()
                setSecondaryButtonText(onEmptyListSecondaryButtonText)
            }
            else -> {
                view.npPhonePicker.visible()
                view.btnPrimary.visible()
                view.npPhonePicker.apply {
                    minValue = 0
                    maxValue = displayedItems.lastIndex
                    displayedValues = displayedItems.toTypedArray()
                }
            }
        }
    }


    fun setTitle(text: String) {
        view.tvTitle.text = text
    }

    fun setTitle(@StringRes resId: Int) {
        view.tvTitle.setText(resId)
    }

    fun setTitleImage(image: Drawable?) {
        view.ivTitleImage.setImageDrawable(image)
    }

    fun setTitleImage(@DrawableRes drawableResId: Int) {
        view.ivTitleImage.setImageResource(drawableResId)
    }

    fun setPickNumberButtonText(text: String) {
        view.btnPrimary.text = text
    }

    fun setPickNumberButtonText(@StringRes resId: Int) {
        view.btnPrimary.setText(resId)
    }

    fun setSecondaryButtonText(text: String) {
        view.btnSecondary.text = text
    }

    fun setSecondaryButtonText(@StringRes resId: Int) {
        view.btnSecondary.setText(resId)
    }

    fun setOnNumberPicked(onPicked: (String) -> Unit) {
        view.btnPrimary.setOnClickListener {
            onPicked.invoke(getSelectedPhone())
        }
    }

    fun setOnSecondaryButtonClick(onClick: () -> Unit) {
        view.btnSecondary.setOnClickListener {
            onClick.invoke()
        }
    }

    private fun getSelectedPhone(): String {
        return view.npPhonePicker.run {
            displayedValues[value]
        }
    }
}

private data class PhoneNumberPickerCardViewVariables(
    var tvTitle: TextView,
    var ivTitleImage: ImageView,
    var npPhonePicker: NumberPicker,
    var btnPrimary: Button,
    var btnSecondary: Button,
)