package com.design.chili.view.cells

import android.content.Context
import android.os.Build
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.design.chili.R
import com.design.chili.extensions.setIsSurfaceClickable
import com.design.chili.extensions.setupRoundedCellCornersMode
import com.design.chili.util.RoundedCornerMode

class InfoCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.infoCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_InfoCellView
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    lateinit var view: InfoCellViewVariables

    init {
        initView(context)
        obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_info_cell, this)
        this.view = InfoCellViewVariables(
            rootView = view.findViewById(R.id.rootView),
            tvTitle = view.findViewById(R.id.tv_title),
            tvSubtitle = view.findViewById(R.id.tv_subtitle),
            divider = view.findViewById(R.id.divider)
        )
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.InfoCellView, defStyleAttr, defStyleRes).run {
            getString(R.styleable.InfoCellView_title)?.let { setTitle(it) }
            getString(R.styleable.InfoCellView_subtitle)?.let { setSubtitle(it) }
            getBoolean(R.styleable.InfoCellView_isDividerVisible, false).let { setDividerVisibility(it) }
            getBoolean(R.styleable.InfoCellView_isSurfaceClickable, true).let { setIsSurfaceClickable(it) }
            getInteger(R.styleable.InfoCellView_roundedCornerMode, -1).takeIf { it != -1 }?.let {
                view.rootView.setupRoundedCellCornersMode(it)
            }
            getResourceId(R.styleable.InfoCellView_titleTextAppearance, -1).takeIf { it != -1 }?.let {
                setTitleTextAppearance(it)
            }
            getResourceId(R.styleable.InfoCellView_subtitleTextAppearance, -1).takeIf { it != -1 }?.let {
                setSubtitleTextAppearance(it)
            }
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

    fun setTitle(text: Spanned) {
        view.tvTitle.text = text
    }

    fun setSubtitle(text: String) {
        view.tvSubtitle.text = text
    }

    fun setSubtitle(text: Spanned) {
        view.tvSubtitle.text = text
    }

    fun setDividerVisibility(isVisible: Boolean) {
        view.divider.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun setRoundedMode(mode: RoundedCornerMode) {
        view.rootView.setupRoundedCellCornersMode(mode.value)
    }

    fun setupIsSurfaceClickable(isSurfaceClickable: Boolean) {
        this.setIsSurfaceClickable(isSurfaceClickable)
    }

    fun setupCornerRoundedMode(mode: RoundedCornerMode) {
        view.rootView.setupRoundedCellCornersMode(mode.value)
    }

    fun setTitleTextAppearance(@StyleRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.tvTitle.setTextAppearance(resId)
        } else {
            view.tvTitle.setTextAppearance(context, resId)
        }
    }

    fun setSubtitleTextAppearance(@StyleRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.tvSubtitle.setTextAppearance(resId)
        } else {
            view.tvSubtitle.setTextAppearance(context, resId)
        }
    }

}

data class InfoCellViewVariables(
    val rootView: ConstraintLayout,
    val tvTitle: TextView,
    val tvSubtitle: TextView,
    val divider: View
)
