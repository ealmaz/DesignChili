package com.design.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        vb.av1.onUrlClick { Toast.makeText(context, "Вы нажали на ссылку $it", Toast.LENGTH_SHORT).show() }
    }
}