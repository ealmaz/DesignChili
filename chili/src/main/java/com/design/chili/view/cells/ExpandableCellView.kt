package com.design.chili.view.cells

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import com.design.chili.R
import com.design.chili.extensions.gone
import com.design.chili.extensions.visible

class ExpandableCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.expandableCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_ExpandableCellView
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    lateinit var view: ExpandableCellViewVariables

    private var isCellViewExpanded = false

    init {
        setupView(context)
        obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
    }


    private fun setupView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_expandable_cell, this)
        this.view = ExpandableCellViewVariables(
            tvTitle = view.findViewById(R.id.tv_title),
            tvDescription = view.findViewById(R.id.tv_description),
            ivChevron = view.findViewById(R.id.iv_chevron),
            divider = view.findViewById(R.id.divider))
        this.setOnClickListener {
            setIsCellExpanded(!isCellViewExpanded)
        }
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int, defStyleRes: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.ExpandableCellView, defStyleAttr, defStyleRes).run {
            setIsCellExpanded(getBoolean(R.styleable.ExpandableCellView_isExpanded, false))
            setTitle(getString(R.styleable.ExpandableCellView_title))
            setDescription(getString(R.styleable.ExpandableCellView_description))
            recycle()
        }
    }

    fun setTitle(text: String?) {
        view.tvTitle.text = text
    }


    fun setTitle(@StringRes textResId: Int) {
        view.tvTitle.setText(textResId)
    }

    fun setDescription(text: String?) {
        view.tvDescription.text = text
    }

    fun setDescription(@StringRes textResId: Int) {
        view.tvDescription.setText(textResId)
    }

    fun setIsCellExpanded(isExpanded: Boolean) {
        when (isExpanded) {
            true -> expandCellView()
            else -> reduceCellView()
        }

    }

    fun expandCellView() {
        isCellViewExpanded = true
        view.tvDescription.animate()
            .translationY(0f).alpha(1.0f)
            .setDuration(100)
            .setListener( object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation : Animator) {
                    super.onAnimationStart(animation)
                    view.tvDescription.visible()
                    view.tvDescription.alpha = 0.0f
                }
            })

        view.ivChevron.animate().rotation(0F)
        view.divider.visible()
    }

    fun reduceCellView() {
        isCellViewExpanded = false
        view.divider.gone()
        view.tvDescription.animate()
            .translationY(-view.tvDescription.height.toFloat()+50)
            .setDuration(100)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation : Animator) {
                    super.onAnimationEnd(animation)
                    view.tvDescription.alpha = 0.0f
                    view.tvDescription.gone()
                }
            })
        view.ivChevron.animate().rotation(180F)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        return bundleOf(
            SUPER_STATE to superState,
            EXPANDED_STATE to isCellViewExpanded
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var superState: Parcelable? = null
        (state as? Bundle)?.let {
            superState = state.getParcelable(SUPER_STATE)
            setIsCellExpanded(state.getBoolean(EXPANDED_STATE))
        }
        return super.onRestoreInstanceState(superState)
    }

    companion object {
        const val SUPER_STATE = "super_state"
        const val EXPANDED_STATE = "expanded_state"
    }
}

data class ExpandableCellViewVariables(
    val tvTitle: TextView,
    val tvDescription: TextView,
    val ivChevron: ImageView,
    val divider: View
)