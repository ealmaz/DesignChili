package com.design.chili.view.card

import android.content.Context
import android.text.Spanned
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import com.design.chili.R
import com.design.chili.extensions.getColorFromAttr
import com.design.chili.extensions.visible
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class PeriodPieChartView : CardView {

    private lateinit var view: PeriodPieChartVariables

    constructor(context: Context) : super(context) {
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        setupView()
    }

    private fun setupView() {
        val view = inflate(context, R.layout.chili_view_period_pie_chart, this)
        this.view = PeriodPieChartVariables(
            tvDate = view.findViewById(R.id.tv_chart_date),
            ivPreviousPeriod = view.findViewById(R.id.iv_previous_period),
            pieChart = view.findViewById(R.id.pie_chart),
            ivNextPeriod = view.findViewById(R.id.iv_next_period)
        )
        cardElevation = 0.0f
        radius = resources.getDimension(R.dimen.radius_8dp)
        setCardBackgroundColor(context.getColorFromAttr(R.attr.ChiliCardViewBackground))
        setupPieChart()
        this.view.pieChart.setCenterTextTypeface(ResourcesCompat.getFont(context, R.font.roboto_medium))
    }

    fun setTitle(value: String) {
        view.tvDate.text = value
    }

    fun switchButtonVisibility(isVisible: Boolean){
        view.ivNextPeriod.visibility = if (isVisible) View.VISIBLE else View.GONE
        view.ivPreviousPeriod.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    var isNextEnable: Boolean
        set(value) {
            view.ivNextPeriod.isEnabled = value
            view.ivNextPeriod.alpha = if (value) 1f else 0.5f
        }
        get() = view.ivNextPeriod.isEnabled

    fun setAmount(value: String) {
        view.pieChart.centerText = value
    }

    fun setAmount(value: Spanned) {
        view.pieChart.centerText = value
    }

    private fun setupPieChart() {
        view.pieChart.run {
            setCenterTextColor(context.getColorFromAttr(R.attr.ChiliPrimaryTextColor))
            setCenterTextSize(16f)
            isDrawHoleEnabled = true
            setHoleColor(context.getColorFromAttr(R.attr.ChiliCardViewBackground))
            holeRadius = 70f
            description.isEnabled = false
            legend.isEnabled = false
            setTouchEnabled(false)
            isRotationEnabled = false
        }
    }

    fun setPieChartData(entryList: MutableList<Float>, colorList: MutableList<Int>) {
        view.pieChart.visible()
        val pieDataSet: PieDataSet
        val list = entryList.map { PieEntry(it) }
        pieDataSet = PieDataSet(list, "")
        pieDataSet.colors = colorList
        pieDataSet.sliceSpace = 1F
        val data = PieData(pieDataSet)
        data.setDrawValues(false)
        view.pieChart.data = data
        view.pieChart.invalidate()
    }

    fun onClickNext(onClick: () -> Unit) {
        view.ivNextPeriod.setOnClickListener { onClick() }
    }

    fun onClickPrevious(onClick: () -> Unit) {
        view.ivPreviousPeriod.setOnClickListener { onClick() }
    }
}

private data class PeriodPieChartVariables(
    var tvDate: TextView,
    var ivPreviousPeriod: ImageView,
    var pieChart: PieChart,
    var ivNextPeriod: ImageView
)