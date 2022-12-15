package com.design.chili.view.cells

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.core.view.isVisible
import com.design.chili.R
import com.design.chili.extensions.setIsSurfaceClickable
import com.design.chili.extensions.setupRoundedCellCornersMode
import com.design.chili.extensions.visible
import com.design.chili.util.RoundedCornerMode

class TitledInfoCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.infoCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_InfoCellView
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    lateinit var view: TitledInfoCellViewVariables

    init {
        initView(context)
        obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_titled_info_cell, this)
        this.view = TitledInfoCellViewVariables(
            rootView = view.findViewById(R.id.rootView),
            tvTitle = view.findViewById(R.id.tv_title),
            tvSubtitle = view.findViewById(R.id.tv_subtitle),
            infoBtn = view.findViewById(R.id.iv_info),
            divider = view.findViewById(R.id.divider)
        )
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.InfoCellView, defStyleAttr, defStyleRes).run {
            getString(R.styleable.InfoCellView_title)?.let { setTitle(it) }
            getBoolean(R.styleable.InfoCellView_isDividerVisible, false).let { setDividerVisibility(it) }
            recycle()
        }
    }

    fun setTitle(@StringRes resId: Int) {
        view.tvSubtitle.setText(resId)
    }

    fun setSubtitle(@StringRes resId: Int) {
        view.tvSubtitle.setText(resId)
    }

    fun setTitle(text: String) {
        view.tvTitle.text = text
    }

    fun setSubtitle(subtitle: String) {
        view.tvSubtitle.visible()
        view.tvSubtitle.text = subtitle
    }

    fun setDividerVisibility(isVisible: Boolean) {
        view.divider.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun setIsInfoButtonVisible(isVisible: Boolean) {
        view.infoBtn.isVisible = isVisible
    }

    fun setInfoButtonClickListener(onClick: () -> Unit) {
        view.infoBtn.setOnClickListener {
            onClick.invoke()
        }
    }
}

data class TitledInfoCellViewVariables(
    val rootView: ConstraintLayout,
    val tvTitle: TextView,
    val tvSubtitle: TextView,
    val infoBtn: ImageView,
    val divider: View
)
