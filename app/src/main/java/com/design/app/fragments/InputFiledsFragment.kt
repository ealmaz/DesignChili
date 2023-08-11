package com.design.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.design.app.MainActivity
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentInputFieldsBinding
import com.design.chili.view.input.PasteListener

class InputFields : BaseFragment<FragmentInputFieldsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setUpHomeEnabled(true)
        vb.field1.setupClearTextButton()
        vb.field2Mask.setupNewMask("12313123123XXXXXXXXX")
        vb.field2Mask.requestFocus()
        vb.field0.setMaxLength(3)

        vb.field123.setPasteTextListener(object : PasteListener {
            override fun onPasteText(fieldText: String, newText: String, selectionPosition: Int): String? {
                return when {
                    newText.trim().length == 13 && newText.startsWith("+996") -> newText.removePrefix("+996")
                    newText.trim().length == 12 && newText.startsWith("996") -> newText.removePrefix("996")
                    newText.trim().length == 10 && newText.startsWith("0") -> newText.removePrefix("0")
                    else -> null
                }
            }

        })
    }

    override fun inflateViewBinging(): FragmentInputFieldsBinding {
        return FragmentInputFieldsBinding.inflate(layoutInflater)
    }
}