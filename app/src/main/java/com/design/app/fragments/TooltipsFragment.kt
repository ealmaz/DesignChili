package com.design.app.fragments

import android.os.Bundle
import android.view.View
import com.design.app.MainActivity
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentTooltipsBinding
import com.design.chili.view.tooltip.Tooltip

class TooltipsFragment : BaseFragment<FragmentTooltipsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setUpHomeEnabled(true)
        vb.btnBottom.setOnClickListener {
            showTooltip(it, Tooltip.GRAVITY_BOTTOM, Tooltip.ALIGN_START)
        }
        vb.btnCenter.setOnClickListener {
            showTooltip(it, Tooltip.GRAVITY_BOTTOM, Tooltip.ALIGN_CENTER)
        }
        vb.btnTop.setOnClickListener {
            showTooltip(it, Tooltip.GRAVITY_TOP, Tooltip.ALIGN_END)
        }
        vb.arrowEnd.setOnClickListener {
            showSmallTooltip(it, Tooltip.GRAVITY_END)
        }
        vb.arrowStart.setOnClickListener {
            showSmallTooltip(it, Tooltip.GRAVITY_START)
        }
    }

    private fun showTooltip(view: View, gravity: Int, align: Int) {
        Tooltip.Builder(requireContext())
            .anchorView(view)
            .gravity(gravity)
            .arrowAlign(align)
            .title("Получи бонус 10 ГБ! (22)")
            .subtitle("При пополнении баланса на 120 с (32)")
            .build()
            .show()
    }

    private fun showSmallTooltip(view: View, gravity: Int) {
        Tooltip.Builder(requireContext())
            .anchorView(view)
            .gravity(gravity)
            .title("Получи бонус")
            .subtitle("При пополнении баланса")
            .build()
            .show()
    }

    override fun inflateViewBinging(): FragmentTooltipsBinding {
        return FragmentTooltipsBinding.inflate(layoutInflater)
    }
}