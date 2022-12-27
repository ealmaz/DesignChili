package com.design.app.fragments

import android.os.Bundle
import android.view.View
import com.design.app.MainActivity
import com.design.app.R
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentInteractiveBottomSheetBinding

class InteractiveBottomSheetFragment : BaseFragment<FragmentInteractiveBottomSheetBinding>() {

    override fun inflateViewBinging(): FragmentInteractiveBottomSheetBinding {
        return FragmentInteractiveBottomSheetBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setUpHomeEnabled(true)
        childFragmentManager.beginTransaction()
            .replace(R.id.container_2, CommonViewsFragment())
            .commit()
    }
}