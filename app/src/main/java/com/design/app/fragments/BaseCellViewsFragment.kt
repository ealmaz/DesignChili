package com.design.app.fragments

import android.os.Bundle
import android.view.View
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentBaseCellBinding


class BaseCellViewsFragment : BaseFragment<FragmentBaseCellBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun inflateViewBinging(): FragmentBaseCellBinding {
        return FragmentBaseCellBinding.inflate(layoutInflater)
    }
}