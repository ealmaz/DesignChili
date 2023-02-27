package com.design.chili.view.cells

import android.content.Context
import android.os.Build
import android.text.Spanned
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.design.chili.R
import com.design.chili.view.shimmer.FacebookShimmering
import com.facebook.shimmer.ShimmerFrameLayout

class AdditionalTextCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.additionalTextCellViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CellViewStyle_BaseCellViewStyle_AdditionalText
) : BaseCellView(context, attrs, defStyleAttr, defStyleRes), FacebookShimmering {

    private var additionalText: TextView? = null

    private val mutableShimmeringViewMap = mutableMapOf<View, View?>()

    private val shimmerViewGroup: List<ShimmerFrameLayout> by lazy {
        listOf(
            findViewById(R.id.view_title_shimmer),
            findViewById(R.id.view_end_placeholder_shimmer)
        )
    }

    init {
        setupViews()
    }

    override fun inflateView(context: Context) {
        super.inflateView(context)
        inflateAdditionalText()
    }

    override fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        super.obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
        context.obtainStyledAttributes(attrs, R.styleable.AdditionalTextCellView, defStyleAttr, defStyleRes)
            .run {
                getString(R.styleable.AdditionalTextCellView_additionalText)?.let {
                    setAdditionalText(it)
                }
                getResourceId(R.styleable.AdditionalTextCellView_additionalTextTextAppearance, -1).takeIf { it != -1 }?.let {
                    setAdditionalTextTextAppearance(it)
                }
                recycle()
            }
    }

    override fun onStartShimmer() {}

    override fun onStopShimmer() {}

    override fun getShimmerLayouts() = shimmerViewGroup

    override fun getShimmeribleViewsPair() = mutableShimmeringViewMap

    private fun setupViews(){
        mutableShimmeringViewMap[view.tvTitle] = view.tvTitleShimmer
        mutableShimmeringViewMap[view.flEndPlaceholder] = view.endPlaceholderShimmer
    }

    private fun inflateAdditionalText() {
        this.additionalText = TextView(context).apply {
            setPadding(0, 0, resources.getDimensionPixelSize(R.dimen.padding_8dp), 0)
            textAlignment = TEXT_ALIGNMENT_TEXT_END
        }
        view.flEndPlaceholder.addView(additionalText)
    }

    fun setAdditionalText(text: String?) {
        additionalText?.text = text
    }

    fun setAdditionalText(spanned: Spanned) {
        additionalText?.text = spanned
    }

    fun setAdditionalText(@StringRes textResId: Int) {
        additionalText?.setText(textResId)
    }

    override fun setIsChevronVisible(isVisible: Boolean) {
        super.setIsChevronVisible(isVisible)
        if (isVisible) additionalText?.setPadding(0, 0,0, 0)
        else additionalText?.setPadding(0, 0, resources.getDimensionPixelSize(R.dimen.padding_8dp), 0)
    }

    fun setAdditionalTextTextAppearance(@StyleRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            additionalText?.setTextAppearance(resId)
        } else {
            additionalText?.setTextAppearance(context, resId)
        }
    }
}