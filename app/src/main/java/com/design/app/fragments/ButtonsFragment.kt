package com.design.app.fragments

import android.os.Bundle
import android.view.View
import com.design.app.base.BaseFragment
import com.design.app.base.ViewInfo
import com.design.app.databinding.FragmentButtonsBinding

class ButtonsFragment : BaseFragment<FragmentButtonsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.primaryButtonMore.setOnClickListener {
            openFragment(MoreInfoFragment.create(ViewInfo("View", "contne  fwefewv scwecw https://confluence.o.kg/pages/viewpage.action?pageId=64404639")))
        }
    }

    override fun inflateViewBinging(): FragmentButtonsBinding {
        return FragmentButtonsBinding.inflate(layoutInflater)
    }
}