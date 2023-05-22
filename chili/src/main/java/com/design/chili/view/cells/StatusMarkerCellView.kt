package com.design.chili.view.cells

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.design.chili.R
import com.design.chili.extensions.gone
import com.design.chili.extensions.visible

class StatusMarkerCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    private val defStyleAttr: Int = R.attr.statusMarkerCellViewDefaultStyle,
    private val defStyleRes: Int = R.style.Chili_CellViewStyle_StatusMarkerCellView
): BaseCellView(context, attrs, R.attr.cellViewDefaultStyle, R.style.Chili_CellViewStyle_BaseCellViewStyle) {

    private lateinit var statusView: StatusMarkerViewViewVariables

    override fun inflateView(context: Context) {
        super.inflateView(context)
        val statusView = LayoutInflater.from(context).inflate(R.layout.chili_view_cell_status_marker_marker_bg, view.flEndPlaceholder, false)
        this.statusView = StatusMarkerViewViewVariables(
            tvStatus = statusView.findViewById(R.id.tv_status),
            ivIcon = statusView.findViewById(R.id.iv_icon),
            rootView = statusView.findViewById(R.id.marker_root_view)
        )
        view.flEndPlaceholder.addView(statusView)
    }

    override fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttrParent: Int, defStyleResParet: Int) {
        super.obtainAttributes(context, attrs, defStyleAttrParent, defStyleResParet)
        context.obtainStyledAttributes(attrs, R.styleable.StatusMarkerCellView, defStyleAttr, defStyleRes).run {
            setStatusText(getText(R.styleable.StatusMarkerCellView_statusText))
            getResourceId(R.styleable.StatusMarkerCellView_statusTextTextAppearance, -1).takeIf { it != -1 }?.let {
                setStatusTextTextAppearance(it)
            }
            getResourceId(R.styleable.StatusMarkerCellView_statusIcon, -1).takeIf { it != -1 }?.let {
                setStatusIcon(it)
            }
            getColor(R.styleable.StatusMarkerCellView_statusBackgroundColor, -1).takeIf { it != -1 }?.let {
                setStatusBackgroundColor(it)
            }
            getColor(R.styleable.StatusMarkerCellView_statusTextColor, Int.MIN_VALUE).takeIf { it != Int.MIN_VALUE }?.let {
                setStatusTextColor(it)
            }
            recycle()
        }
    }

    fun setupStatus(
        status: CharSequence,
        iconResId: Int?,
        @ColorInt textColorInt: Int? = null,
        @ColorInt backgroundColorInt: Int? = null
    ) {
        setStatusText(status)
        setStatusIcon(iconResId)
        textColorInt?.let { setStatusTextColor(it) }
        backgroundColorInt?.let { setStatusBackgroundColor(it) }
    }

    fun setStatusText(charSequence: CharSequence?) {
        view.flEndPlaceholder.isVisible = charSequence != null
        statusView.tvStatus.text = charSequence
    }

    fun setStatusText(resId: Int) {
        statusView.tvStatus.setText(resId)
        view.flEndPlaceholder.visible()
    }

    fun setStatusTextTextAppearance(resId: Int) {
        statusView.tvStatus.setTextAppearance(resId)
    }

    fun setStatusTextColor(@ColorInt color: Int) {
        statusView.tvStatus.setTextColor(color)
    }

    fun setStatusIcon(resId: Int?) = with(statusView.ivIcon) {
        if (resId == null) gone()
        else {
            visible()
            setImageResource(resId)
        }
    }

    fun setStatusIcon(iconDrawable: Drawable?) = with(statusView.ivIcon) {
        if (iconDrawable == null) gone()
        else {
            visible()
            setImageDrawable(drawable)
        }
    }

    fun setStatusBackgroundColor(@ColorInt color: Int) {
        statusView.rootView.setCardBackgroundColor(color)
    }
}

data class StatusMarkerViewViewVariables(
    val tvStatus: TextView,
    val ivIcon: ImageView,
    val rootView: CardView
)
