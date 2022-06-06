package com.design.chili.view.card

import android.content.Context
import android.text.Spanned
import android.util.AttributeSet
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.design.chili.R
import com.design.chili.extensions.getColorFromAttr
import com.design.chili.extensions.visible
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class PieChartTitledView : CardView {

    private lateinit var view: PieChartViewVariables

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
        val view = inflate(context, R.layout.view_titled_pie_chart, this)
        this.view = PieChartViewVariables(
            title = view.findViewById(R.id.tv_title),
            amount = view.findViewById(R.id.tv_amount),
            pieChart = view.findViewById(R.id.pie_chart)
        )
        cardElevation = 0.0f
        radius = resources.getDimension(R.dimen.radius_4dp)
        setCardBackgroundColor(context.getColorFromAttr(R.attr.CardViewBackground))
        setupPieChart()
    }

    fun setTitle(value: String) {
        view.title.text = value
    }

    fun setAmount(value: String) {
        view.amount.text = value
    }

    fun setAmount(value: Spanned) {
        view.amount.text = value
    }

    private fun setupPieChart() {
        view.pieChart.run {
            isDrawHoleEnabled = true
            setHoleColor(context.getColorFromAttr(R.attr.CardViewBackground))
            holeRadius = 70f
            description.isEnabled = false
            legend.isEnabled = false
            setTouchEnabled(false)
            isRotationEnabled = false
        }
    }

    fun setPieData(entryList: List<Float>, colorList: List<Int>) {
        view.pieChart.visible()
        val list = entryList.map { PieEntry(it) }
        val pieDataSet = PieDataSet(list, "")
        pieDataSet.colors = colorList
        pieDataSet.sliceSpace = 1f
        val data = PieData(pieDataSet)
        data.setDrawValues(false)
        view.pieChart.data = data
        view.pieChart.invalidate()
    }
}

data class PieChartViewVariables(
    var title: TextView,
    var amount: TextView,
    var pieChart: PieChart
)