package com.design.app.fragments

import android.os.Bundle
import android.view.View
import com.design.app.MainActivity
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentButtonsBinding

class ButtonsFragment : BaseFragment<FragmentButtonsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setUpHomeEnabled(true)
        vb.loader.setOnClickListener {
            vb.loader.setIsLoading(true)
        }
        vb.stopLoader.setOnClickListener {
            vb.loader.setIsLoading(false)
        }
    }

    override fun inflateViewBinging(): FragmentButtonsBinding {
        return FragmentButtonsBinding.inflate(layoutInflater)
    }
}