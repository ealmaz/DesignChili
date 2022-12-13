package com.design.app.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentCardsBinding

class CardsFragment : BaseFragment<FragmentCardsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        vb.pnpcvNumbers.setupView(listOf("+996 700 000 001", "+996 700 000 001", "+996 700 000 001", "+996 700 000 001", "+996 700 000 001", "+996 700 000 001", "+996 700 000 001", "+996 700 000 001"), "+996 700 000 001")
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

    }

    override fun inflateViewBinging(): FragmentCardsBinding {
        return FragmentCardsBinding.inflate(layoutInflater)
    }
}