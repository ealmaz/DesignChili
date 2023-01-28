package com.design.chili.view.cells

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.design.chili.R
import com.design.chili.extensions.setIsSurfaceClickable
import com.design.chili.extensions.setTextOrHide
import com.design.chili.extensions.setupRoundedCellCornersMode
import com.design.chili.util.RoundedCornerMode
import com.design.chili.view.image.SquircleView

class DetailCellView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = R.attr.detailCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_DetailCellView
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private lateinit var view: DetailCellViewVariables

    init {
        initView(context)
        obtainAttributes(context, attributeSet, defStyleAttr, defStyleRes)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_detail_cell, this)
        this.view = DetailCellViewVariables(
            root = view.findViewById(R.id.root),
            tvTitle = view.findViewById(R.id.tv_title),
            tvSubtitle = view.findViewById(R.id.tv_subtitle),
            tvValue = view.findViewById(R.id.tv_value),
            tvStatus = view.findViewById(R.id.tv_status),
            svIcon = view.findViewById(R.id.sv_icon)
        )
    }

    private fun obtainAttributes(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        context.obtainStyledAttributes(attributeSet, R.styleable.DetailCellView, defStyleAttr, defStyleRes).run {
            setTitle(getString(R.styleable.DetailCellView_title))
            setSubtitle(getString(R.styleable.DetailCellView_subtitle))
            setValue(getString(R.styleable.DetailCellView_value))
            setStatus(getString(R.styleable.DetailCellView_status))
            setIcon(getDrawable(R.styleable.DetailCellView_icon))
            setupIsSurfaceClickable(getBoolean(R.styleable.DetailCellView_isSurfaceClickable, true))
            getInteger(R.styleable.DetailCellView_roundedCornerMode, RoundedCornerMode.SINGLE.value).let {
                view.root.setupRoundedCellCornersMode(it)
            }
            recycle()
        }
    }

    fun setTitle(text: String?) {
        view.tvTitle.text = text
    }

    fun setTitle(@StringRes textResId: Int) {
        view.tvTitle.setText(textResId)
    }

    fun setTitleTextAppearance(@StyleRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.tvTitle.setTextAppearance(resId)
        } else {
            view.tvTitle.setTextAppearance(context, resId)
        }
    }

    fun setSubtitle(text: String?) {
        view.tvSubtitle.setTextOrHide(text)
    }

    fun setSubtitle(@StringRes textResId: Int) {
        view.tvSubtitle.setTextOrHide(textResId)
    }

    fun setSubtitleTextAppearance(@StyleRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.tvSubtitle.setTextAppearance(resId)
        } else {
            view.tvSubtitle.setTextAppearance(context, resId)
        }
    }

    fun setValue(text: String?) {
        view.tvValue.setTextOrHide(text)
    }

    fun setValue(@StringRes textResId: Int?) {
        view.tvValue.setTextOrHide(textResId)
    }

    fun setValue(text: Spanned?) {
        view.tvValue.setTextOrHide(text)
    }

    fun setValueTextColor(@ColorInt colorInt: Int) {
        view.tvValue.setTextColor(colorInt)
    }

    fun setValueTextAppearance(@StyleRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.tvValue.setTextAppearance(resId)
        } else {
            view.tvValue.setTextAppearance(context, resId)
        }
    }

    fun setStatus(text: String?) {
        view.tvStatus.setTextOrHide(text)
    }

    fun setStatus(@StringRes textResId: Int) {
        view.tvStatus.setTextOrHide(textResId)
    }

    fun setStatusTextAppearance(@StyleRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.tvStatus.setTextAppearance(resId)
        } else {
            view.tvStatus.setTextAppearance(context, resId)
        }
    }

    fun setupIsSurfaceClickable(isSurfaceClickable: Boolean) {
        this.setIsSurfaceClickable(isSurfaceClickable)
    }

    fun setupCornerRoundedMode(mode: RoundedCornerMode) {
        view.root.setupRoundedCellCornersMode(mode.value)
    }

    fun setIcon(@DrawableRes drawableResId: Int) {
        view.svIcon.setImageResource(drawableResId)
    }

    fun setIcon(drawable: Drawable?) {
        view.svIcon.setImageDrawable(drawable)
    }

    fun setIconUrl(url: String?) {
        if (url == null) return
        Glide.with(view.svIcon)
            .load(url)
            .dontTransform()
            .into(view.svIcon)
    }
}

data class DetailCellViewVariables(
    val root: ConstraintLayout,
    val tvTitle: TextView,
    val tvSubtitle: TextView,
    val tvValue: TextView,
    val tvStatus: TextView,
    val svIcon: SquircleView
)