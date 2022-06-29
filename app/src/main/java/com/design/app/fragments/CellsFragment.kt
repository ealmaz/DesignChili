package com.design.app.fragments

import android.os.Bundle
import android.view.View
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentCellsBinding

class CellsFragment : BaseFragment<FragmentCellsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun inflateViewBinging(): FragmentCellsBinding {
        return FragmentCellsBinding.inflate(layoutInflater)
    }
}