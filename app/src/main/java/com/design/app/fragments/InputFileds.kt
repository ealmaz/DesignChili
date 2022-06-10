package com.design.app.fragments

import android.os.Bundle
import android.view.View
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentInputFieldsBinding

class InputFields : BaseFragment<FragmentInputFieldsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.field1.setupClearTextButton()
    }

    override fun inflateViewBinging(): FragmentInputFieldsBinding {
        return FragmentInputFieldsBinding.inflate(layoutInflater)
    }
}