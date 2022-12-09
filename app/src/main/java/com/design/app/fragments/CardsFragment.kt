package com.design.app.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentCardsBinding
import com.design.chili.model.Option
import com.design.chili.util.IconStatus

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
        vb.singleSelectedCardView.apply {
            setOnClickListener {
                setupBorder("#FBA82B")
                setupBackground("#FBA82B")
            }
            setOnIconClickListener {
                reset()
            }
        }
        val icons = arrayListOf<String>()
        icons.add("https://minio.o.kg/catalog/logos/elsom.png")
        icons.add("https://minio.o.kg/catalog/logos/kbkyrgyzstan.png")
        icons.add("https://minio.o.kg/catalog/logos/demirbank.png")
        icons.add("https://minio.o.kg/catalog/logos/mbank.png")
        icons.add("https://minio.o.kg/catalog/logos/elcart.png")
        icons.add("https://minio.o.kg/catalog/logos/odengi_w.png")
        vb.multiIconedTitleCellView.apply {
            setIcons(icons)
            setIsInfoButtonVisible(true)
        }

        vb.titledToggleCardView.apply {
            setIcons(icons)
            setIsInfoButtonVisible(true)
            setInfoButtonClickListener { Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show() }
            setValue("148 с / 2 неделя")
            setOnCheckChangeListener { compoundButton, b ->
                if (b) Toast.makeText(context, "Checked", Toast.LENGTH_SHORT).show()
            }
        }

        val items = arrayListOf<Option<*>>()
        items.add(Option(String, "Видео", "148 с / 1 неделя", true, icons))
        items.add(Option(String, "Соцсети", "148 с / 1 неделя", true, icons))
        items.add(Option(String, "Мессенджеры", "148 с / 1 неделя", true, icons))
        items.add(Option(String, "Видеомессенджеры", "148 с / 1 неделя", true, icons))
        items.add(Option(String, "Музыка", "148 с / 1 неделя", true, icons))
        vb.configToggledCardView.apply {
            setTitleText("Безлимит")
            setInfoBtnVisibilty(true)
            setToggles(items)
        }


        vb.configSelectorCardView.apply {
            setTitleText("Длительность")
            setIcon("https://minio.o.kg/catalog/logos/elsom.png")
            setSelectors(items)
        }
    }

    override fun inflateViewBinging(): FragmentCardsBinding {
        return FragmentCardsBinding.inflate(layoutInflater)
    }
}