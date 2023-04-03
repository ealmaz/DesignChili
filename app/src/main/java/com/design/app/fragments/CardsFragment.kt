package com.design.app.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.design.app.MainActivity
import com.design.app.R
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentCardsBinding
import com.design.chili.view.shimmer.hideShimmer
import com.design.chili.view.shimmer.showShimmer
import com.design.chili.view.shimmer.startShimmering
import com.design.chili.view.shimmer.stopShimmering

class CardsFragment : BaseFragment<FragmentCardsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setUpHomeEnabled(true)

        vb.balanceView.run {
            showShimmer()
            postDelayed({ hideShimmer() }, 5000)
        }

        vb.pieChart.apply {
            setAmount("550 c")
            setTitle("Траты за июль")
            setPieData(mutableListOf(12F, 21F, 32F), mutableListOf(Color.BLUE))
        }
        vb.chart.apply {
            setTitle("Траты за июль")
            isNextEnable = false
            setAmount("5000 c")
            setPieChartData(mutableListOf(12F, 21F, 32F), mutableListOf(Color.BLUE))
        }
        vb.nvn2.setupView(listOf(), "Выбрать номер")
        vb.pnpcvNumbers.setupView(
            listOf(
                "+996 700 000 001",
                "+996 700 000 001",
                "+996 700 000 001",
                "+996 700 000 001",
                "+996 700 000 001",
                "+996 700 000 001",
                "+996 700 000 001",
                "+996 700 000 001"
            ), "+996 700 000 001"
        )
        vb.tpiv.apply {
            setOnCallFabClickListener { setCallRemain(0, 100) }
            setOnInternetFabClickListener { setInternetRemain(100, 83) }
            setInternetRemain(100, 73)
            setCallRemain(0, 100)
        }
        vb.tpiv2.apply {
            setInternetRemain(0, 73)
            setCallRemain(0, 100)
        }

//        vb.expandable.setItems(listOf(
//            ExpandableItemData("Сумма к зачислению:", "Из нее - комиссии и сборы:", "8 000,00 <u>с</u>".parseAsHtml(), "150,00 <u>с</u>".parseAsHtml()),
//            ExpandableItemData("Сумма к зачислению:", "Из нее - комиссии и сборы:", "8 000,00 <u>с</u>".parseAsHtml(), "150,00 <u>с</u>".parseAsHtml()),
//            ExpandableItemData("Сумма к зачислению:", null, "8 000,00 <u>с</u>".parseAsHtml(), null),
//            ExpandableItemData("Сумма к зачислению:", null, "150,00 <u>с</u>".parseAsHtml()),
//            ExpandableItemData("Сумма к зачислению:", "Из нее - комиссии и сборы:", "8 000,00 <u>с</u>".parseAsHtml(), null),
//        ))


//        vb.expandable.showShimmer()
//        vb.expandable2.showShimmer()
//        vb.expandable3.showShimmer()
//        vb.expandable4.showShimmer()

        vb.startShimmer.setOnClickListener {
            vb.expandable.startShimmering()
        }

        vb.stopShimmer.setOnClickListener {
            vb.expandable.stopShimmering()
        }

        vb.notifiedBalance.apply {
            setNotificationText("Подарок")
            setNotificationDrawableRes(R.drawable.ic_gift)
            showNotification()
        }
    }

    override fun inflateViewBinging(): FragmentCardsBinding {
        return FragmentCardsBinding.inflate(layoutInflater)
    }
}