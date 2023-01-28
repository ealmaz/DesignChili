package com.design.app.fragments

import android.os.Bundle
import android.view.View
import androidx.core.text.parseAsHtml
import com.design.app.MainActivity
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentCellBinding


class CellViewsFragment : BaseFragment<FragmentCellBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setUpHomeEnabled(true)
        vb.btnP1.setOnClickListener {
            vb.progress.setProgressPercent(100)
        }
        vb.btnP2.setOnClickListener {
            vb.progress.setProgressPercent(0)
        }
        vb.btnP4.setOnClickListener {
            vb.progress4.setProgressPercent(80)
        }
        vb.btnP5.setOnClickListener {
            vb.progress4.setProgressPercent(20)
        }
        vb.dcv1.setValueTextColor(resources.getColor(com.design.chili.R.color.red_1))

        vb.dcv1.setValue("100 <u>c</u>".parseAsHtml())
        vb.dcv1.setIconUrl("https://minio.o.kg/call-center/telega.png")
        vb.testAdditionalTextCellView.apply {
            setOnClickListener {
                setAdditionalText("0")
            }
        }
    }

    override fun inflateViewBinging(): FragmentCellBinding {
        return FragmentCellBinding.inflate(layoutInflater)
    }
}