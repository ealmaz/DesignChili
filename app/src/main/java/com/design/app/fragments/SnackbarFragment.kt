package com.design.app.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentSnackbarsBinding
import com.design.chili.extensions.showInfinitiveLoaderSnackbar
import com.design.chili.extensions.showTimerActionBeforeSuccessCnackbar

class SnackbarFragment : BaseFragment<FragmentSnackbarsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.loadSnackbar.setOnClickListener {
            (requireActivity() as AppCompatActivity).showInfinitiveLoaderSnackbar(vb.root, "Snackbar meesage")
        }
        vb.timerActionSnackbar.setOnClickListener {
            (requireActivity() as AppCompatActivity).showTimerActionBeforeSuccessCnackbar(vb.root, "Tiner message bla bla bla", "onSuccess", "click", {}, {}, 5000)
        }

    }

    override fun inflateViewBinging(): FragmentSnackbarsBinding {
        return FragmentSnackbarsBinding.inflate(layoutInflater)
    }

}