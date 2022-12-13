package com.design.chili.view.card

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.design.chili.R
import com.design.chili.extensions.*
import com.design.chili.extensions.getColorFromAttr
import com.design.chili.extensions.invisible
import com.design.chili.extensions.visible
import com.design.chili.util.IconStatus

class SingleSelectedCardView : FrameLayout {

    private lateinit var view: SingleSelectedCardViewVariables

    var isChecked: Boolean = false
    var isActive = false
    private var color: String? = ""

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
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_single_selected_card, this)
        this.view = SingleSelectedCardViewVariables(
            root = view.findViewById(R.id.root_view),
            innerLayout = view.findViewById(R.id.cl_view),
            title = view.findViewById(R.id.tv_title),
            value = view.findViewById(R.id.tv_value),
            icon = view.findViewById(R.id.iv_action_icon),
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
        view.title.text = text
    }

    fun setValue(value: String) {
        view.value.visible()
        view.value.text = value
    }

    fun setValue(value: Spanned?) {
        value?.let {
            view.value.visible()
            view.value.text = value
        }
    }

    fun setupColor(color: String) {
        this.color = color
    }

    private fun setupBorder(color: String) {
        val borderBackground = view.root.background as? GradientDrawable
        borderBackground?.mutate()
        borderBackground?.setStroke(context.resources.getDimension(R.dimen.view_2dp).toInt(), Color.parseColor(color))
    }

    fun setupBackground(color: String) {
        val background = view.innerLayout.background as? GradientDrawable
        background?.mutate()
        background?.setColor(Color.parseColor(color))
        background?.alpha = 51
    }

    private fun setupBorder(color: Int) {
        val borderBackground = view.root.background as? GradientDrawable
        borderBackground?.mutate()
        borderBackground?.setStroke(context.resources.getDimension(R.dimen.view_2dp).toInt(), color)
    }

    private fun setupBackground(color: Int) {
        val background = view.innerLayout.background as? GradientDrawable
        background?.mutate()
        background?.setColor(color)
        background?.alpha = 51
    }

    fun setSelected() {
        color?.let {
            setupBorder(it)
            setupBackground(it)
        }
        isChecked = true
    }

    fun setActive() {
        setStatus(IconStatus.ACTIVE)
        color?.let { setupBorder(it) }
        isActive = true
    }

    fun reset() {
        setupBorder(context.getColorFromAttr(R.attr.ChiliCardViewBackground))
        setupBackground(context.getColorFromAttr(R.attr.ChiliCardViewBackground))
        if (isActive) setActive()
        else setStatus(IconStatus.UNSELECTED)
        isChecked = false
    }

    fun setValueTextRes(@StringRes textResId: Int) {
        view.value.setText(textResId)
    }

    private fun setIconDrawableRes(@DrawableRes resId: Int) {
        view.icon.visible()
        view.icon.setImageResource(resId)
    }

    fun setStatus(status: IconStatus) {
        when (status) {
            IconStatus.UNAVAILABLE -> setUnavailable(true)
            IconStatus.SELECTED -> setIconDrawableRes(R.drawable.chili_ic_reset)
            IconStatus.ACTIVE -> setIconDrawableRes(R.drawable.chili_ic_done)
            else -> view.icon.invisible()
        }
        setIsIconClickable(status)
    }

    private fun setUnavailable(isUnavailable: Boolean) {
        view.root.isClickable = !isUnavailable
        view.icon.invisible()
        if (isUnavailable) {
            view.title.setTextColor(context.color(R.color.gray_1))
            view.value.invisible()
        }
        else {
            view.title.setTextColor(context.color(R.color.black_1))
            view.value.visible()
        }
    }

    private fun setIsIconClickable(status: IconStatus) {
        when (status) {
            IconStatus.SELECTED -> view.icon.isClickable = true
            else -> view.icon.isClickable = false
        }
    }

    fun setOnClickListener(onClick: () -> Unit) {
        view.root.setOnClickListener {
            if (isChecked) reset()
            else {
                setStatus(IconStatus.SELECTED)
                setSelected()
            }
            onClick.invoke()
        }
    }

    fun setOnIconClickListener(onClick: () -> Unit) {
        view.icon.setOnClickListener {
            reset()
            onClick.invoke()
        }
    }

    fun setActionIconVisibility(isVisible: Boolean){
        view.icon.isVisible = isVisible
    }
}

private data class SingleSelectedCardViewVariables(
    var root: FrameLayout,
    var innerLayout: ConstraintLayout,
    var title: TextView,
    var value: TextView,
    var icon: ImageView
)