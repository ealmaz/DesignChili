package com.design.chili.view.cells

import android.content.Context
import android.text.Spanned
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import com.design.chili.R
import com.design.chili.view.common.AnimatedProgressLine

class ProgressCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.progressCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_ProgressCellView
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var view: ProgressCellViewVariables

    init {
        initView(context)
        obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_progress_cell, this)
        this.view = ProgressCellViewVariables(
            tvTitle = view.findViewById(R.id.tv_title),
            tvDescription = view.findViewById(R.id.tv_description),
            progressLine = view.findViewById(R.id.apl_progress)
        )
    }


    private fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.ProgressCellView, defStyleAttr, defStyleRes).run {
            getBoolean(R.styleable.ProgressCellView_animateProgress, false).let {
                view.progressLine.setIsProgressAnimated(it)
            }
            getColor(R.styleable.ProgressCellView_progressColor, -1).takeIf { it != -1 }?.let {
                view.progressLine.setProgressColor(it)
            }
            getColor(R.styleable.ProgressCellView_progressBackgroundColor, -1).takeIf { it != -1 }?.let {
                view.progressLine.setProgressBackgroundColor(it)
            }
            getInteger(R.styleable.ProgressCellView_progressPercent, 50).let {
                setProgressPercent(it)
            }
            getString(R.styleable.ProgressCellView_title)?.let {
                setTitle(it)
            }
            getString(R.styleable.ProgressCellView_description)?.let {
                setDescriptionText(it)
            }

            recycle()
        }
    }

    fun setProgressPercent(progress: Int) {
        view.progressLine.setProgress(progress)
    }

    fun setTitle(text: String) {
        view.tvTitle.text = text
    }

    fun setTitle(@StringRes textResId: Int) {
        view.tvTitle.setText(textResId)
    }

    fun setTitle(spanned: Spanned) {
        view.tvTitle.text = spanned
    }

    fun setDescriptionText(description: String) {
        view.tvDescription.text = description
    }

    fun setDescriptionText(@StringRes descriptionResId: Int) {
        view.tvDescription.setText(descriptionResId)
    }

    fun setDescriptionText(spanned: Spanned) {
        view.tvDescription.text = spanned
    }
}

data class ProgressCellViewVariables(
    val tvTitle: TextView,
    val tvDescription: TextView,
    val progressLine: AnimatedProgressLine
)