package com.design.chili.view.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.design.chili.R
import com.design.chili.extensions.gone
import com.design.chili.extensions.setTextOrHide
import com.design.chili.extensions.visible

class ExpandableInfoCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.expandableInfoCardViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CardViewStyle_ExpandableInfoCardView
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var view: ExpandableInfoCardViewVariables

    private var isExpandable: Boolean = false

    private var isExpanded: Boolean = false

    init {
        inflateView(context)
        obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
        setupViews()
    }

    private fun inflateView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_expandable_card_info, this, true)
        this.view = ExpandableInfoCardViewVariables(
            tvTitle = view.findViewById(R.id.tv_title),
            tvSubtitle = view.findViewById(R.id.tv_subtitle),
            tvValue = view.findViewById(R.id.tv_value),
            ivArrow = view.findViewById(R.id.iv_arrow),
            flExpandableContent = view.findViewById(R.id.fl_expandable_content)
        )
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.ExpandableInfoCardView, defStyleAttr, defStyleRes).run {
            getString(R.styleable.ExpandableInfoCardView_title).run { setTitle(this) }
            getString(R.styleable.ExpandableInfoCardView_subtitle).run { setSubtitle(this) }
            getString(R.styleable.ExpandableInfoCardView_value).run { setValue(this) }
            isExpandable = getBoolean(R.styleable.ExpandableInfoCardView_isExpandable, false)
            isExpanded = getBoolean(R.styleable.ExpandableInfoCardView_isExpanded, false)
            recycle()
        }
    }


    fun setTitle(charSequence: CharSequence?) {
        view.tvTitle.text = charSequence
    }

    fun setTitle(resId: Int) {
        view.tvTitle.setText(resId)
    }

    fun setSubtitle(charSequence: CharSequence?) {
        view.tvSubtitle.setTextOrHide(charSequence)
    }

    fun setSubtitle(resId: Int?) {
        view.tvSubtitle.setTextOrHide(resId)
    }

    fun setValue(charSequence: CharSequence?) {
        view.tvValue.setTextOrHide(charSequence)
    }

    fun setValue(resId: Int) {
        view.tvValue.setText(resId)
    }

    fun setIsExpandable(isExpandable: Boolean?) {
        this.isExpandable = isExpandable ?: false
    }

    fun setIsExpanded(isExpanded: Boolean?) {
        this.isExpanded = isExpanded ?: false
        if (this.isExpanded) {
            rotateChevron(0f)
            view.flExpandableContent.visible()
        } else {
            rotateChevron(180f)
            view.flExpandableContent.gone()
        }
    }

    private fun rotateChevron(rotation: Float = 0f) {
        view.ivArrow.animate().rotation(rotation)
    }

    fun setItems(items: List<ExpandableItemData>) {
        val column = LinearLayout(context).apply { orientation = LinearLayout.VERTICAL }
        items.forEach {
            val row = createExpandableRow()
            setupExpandableRow(it, row)
            column.addView(row)
        }
        view.flExpandableContent.removeAllViews()
        view.flExpandableContent.addView(column)
        setIsExpanded(isExpanded)
    }

    private fun createExpandableRow(): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.chili_view_expandable_card_info_item, this, false)
    }

    private fun setupExpandableRow(item: ExpandableItemData, rowView: View) {
        rowView.apply {
            findViewById<TextView>(R.id.tv_title).text = item.title
            findViewById<TextView>(R.id.tv_subtitle).setTextOrHide(item.subTitle)
            findViewById<TextView>(R.id.tv_title_value).setTextOrHide(item.titleValue)
            findViewById<TextView>(R.id.tv_subtitle_value).setTextOrHide(item.subtitleValue)
        }
    }

    private fun setupViews() {
        view.ivArrow.apply {
            isVisible = isExpandable
            setOnClickListener { setIsExpanded(!isExpanded) }
        }
    }
}

data class ExpandableInfoCardViewVariables(
    val tvTitle: TextView,
    val tvSubtitle: TextView,
    val tvValue: TextView,
    val ivArrow: ImageView,
    val flExpandableContent: FrameLayout
)

data class ExpandableItemData(
    val title: CharSequence? = null,
    val subTitle: CharSequence? = null,
    val titleValue: CharSequence? = null,
    val subtitleValue: CharSequence? = null
)