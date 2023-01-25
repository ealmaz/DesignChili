package com.design.chili.view.card

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.FrameLayout
import androidx.core.text.parseAsHtml
import com.design.chili.R
import com.design.chili.view.cells.MultiIconedTitleCellView
import com.design.chili.view.cells.ToggleCellViewNew

class TitledToggleCardView : FrameLayout {

    private lateinit var view: TitledToggleCardViewVariables

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

    @SuppressLint("MissingInflatedId")
    private fun inflateViews() {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_titled_toggle_card, this)
        this.view = TitledToggleCardViewVariables(
            root = view.findViewById(R.id.root_view),
            multiIconedTitleView = view.findViewById(R.id.mitcv_view),
            toggleView = view.findViewById(R.id.tcv_toggle)
        )
    }

    private fun obtainAttributes(attrs: AttributeSet, defStyle: Int = R.style.Chili_CardViewStyle_SingleSelectedCard) {
        context?.obtainStyledAttributes(attrs, R.styleable.SingleSelectedCardView, R.attr.singleSelectedCardViewDefaultStyle, defStyle)?.run {
            getString(R.styleable.SingleSelectedCardView_title)?.let {
                setTitleText(it)
            }
            getString(R.styleable.SingleSelectedCardView_value)?.let {
                setValue(it)
            }
            recycle()
        }
    }

    fun setTitleText(text: String) {
        view.multiIconedTitleView.setTitle(text)
    }

    fun setValue(value: String) {
        view.toggleView.setTitle(value)
    }

    fun setValueHtml(value: String) {
        view.toggleView.setTitle(value.parseAsHtml())
    }

    fun setIcons(icons: ArrayList<String>) {
        view.multiIconedTitleView.setIcons(icons)
    }

    fun setIsInfoButtonVisible(isVisible: Boolean) {
        view.multiIconedTitleView.setIsInfoButtonVisible(isVisible)
    }

    fun setInfoButtonClickListener(onClick: () -> Unit) {
        view.multiIconedTitleView.setInfoButtonClickListener(onClick)
    }

    fun setOnCheckChangeListener(listener: (CompoundButton, Boolean) -> Unit) {
        view.toggleView.setOnCheckChangeListener(listener)
    }

    fun setUnavailable(isUnavailable: Boolean) {
        view.toggleView.setIsEnabled(!isUnavailable)
    }
}

private data class TitledToggleCardViewVariables(
    var root: FrameLayout,
    var multiIconedTitleView: MultiIconedTitleCellView,
    var toggleView: ToggleCellViewNew
)