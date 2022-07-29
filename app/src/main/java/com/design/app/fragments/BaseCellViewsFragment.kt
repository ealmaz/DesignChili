package com.design.app.fragments

import android.os.Bundle
import android.view.View
import com.design.app.R
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentBaseCellBinding


class BaseCellViewsFragment : BaseFragment<FragmentBaseCellBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb.endIconCell.setEndIconClickListener {
            vb.endIconCell.setEndIcon(com.design.chili.R.drawable.chili_ic_success)
        }
    }

    override fun inflateViewBinging(): FragmentBaseCellBinding {
        return FragmentBaseCellBinding.inflate(layoutInflater)
    }
}