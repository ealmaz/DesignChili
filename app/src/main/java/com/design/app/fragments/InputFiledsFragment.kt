package com.design.app.fragments

import android.os.Bundle
import android.view.View
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentInputFieldsBinding
import com.design.chili.view.input.PasteListener

class InputFields : BaseFragment<FragmentInputFieldsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.field1.setupClearTextButton()
        vb.field0.setPasteTextListener(object : PasteListener {
            override fun onPasteText(
                fieldText: String,
                newText: String,
                selectionPosition: Int,
            ): String? {
                if (newText.length > 9) {
                    return newText.parsePhoneNumber
                }
                return null
            }

        })
    }

    override fun inflateViewBinging(): FragmentInputFieldsBinding {
        return FragmentInputFieldsBinding.inflate(layoutInflater)
    }
}

val String.parsePhoneNumber: String
    get() {
        val number = this.replace("[\\-\\+\\s()]+".toRegex(), "")
        return when {
            number.startsWith("996") -> number
            number.startsWith("+") -> number.replace("+", "")
            number.startsWith("0") -> "996${number.trimStart('0')}"
            else -> "996$number"
        }
    }