package com.design.app.fragments

import android.os.Bundle
import android.view.View
import com.design.app.MainActivity
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentCommonBinding

class CommonViewsFragment : BaseFragment<FragmentCommonBinding>() {

    override fun inflateViewBinging(): FragmentCommonBinding {
        return FragmentCommonBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setUpHomeEnabled(true)
    }
}