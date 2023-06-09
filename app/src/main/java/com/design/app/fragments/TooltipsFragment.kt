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
        with(vb.inputField) {
            setOnClickListener { showTooltip(it) }
            performClick()
        }
    }

    private fun showTooltip(view: View) {
        Tooltip.Builder(requireContext())
            .anchorView(view)
            .gravity(Tooltip.GRAVITY_BOTTOM)
            .arrowAlign(Tooltip.ALIGN_START)
            .title("Получи бонус 10 ГБ! (22)")
            .subtitle("При пополнении баланса на 120 с (32)")
            .margin(com.design.chili.R.dimen.padding_6dp)
            .build()
            .show()
    }

    override fun inflateViewBinging(): FragmentTooltipsBinding {
        return FragmentTooltipsBinding.inflate(layoutInflater)
    }
}