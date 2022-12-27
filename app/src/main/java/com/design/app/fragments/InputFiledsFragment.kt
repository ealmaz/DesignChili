package com.design.app.fragments

import android.os.Bundle
import android.view.View
import com.design.app.MainActivity
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentInputFieldsBinding

class InputFields : BaseFragment<FragmentInputFieldsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setUpHomeEnabled(true)
        vb.field1.setupClearTextButton()
        vb.field2Mask.setupNewMask("12313123123XXXXXXXXX")
        vb.field2Mask.requestFocus()
        vb.field0.setMaxLength(3)
    }

    override fun inflateViewBinging(): FragmentInputFieldsBinding {
        return FragmentInputFieldsBinding.inflate(layoutInflater)
    }
}