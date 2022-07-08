package com.design.app.fragments

import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentCommonBinding

class CommonViews : BaseFragment<FragmentCommonBinding>() {

    override fun inflateViewBinging(): FragmentCommonBinding {
        return FragmentCommonBinding.inflate(layoutInflater)
    }
}