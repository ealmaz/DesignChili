package com.design.app.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.design.app.MainActivity
import com.design.app.R
import com.design.app.base.BaseFragment
import com.design.app.databinding.FragmentSnackbarsBinding
import com.design.chili.extensions.showInfinitiveLoaderSnackbar
import com.design.chili.extensions.showSimpleSnackbar
import com.design.chili.extensions.showTimerActionBeforeSuccessCnackbar
import com.design.chili.view.snackbar.ChiliSnackBar

class SnackbarFragment : BaseFragment<FragmentSnackbarsBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setUpHomeEnabled(true)
        vb.loadSnackbar.setOnClickListener {
            (requireActivity() as AppCompatActivity).showInfinitiveLoaderSnackbar(vb.root, "Snackbar meesage")
        }
        vb.timerActionSnackbar.setOnClickListener {
            (requireActivity() as AppCompatActivity).showTimerActionBeforeSuccessCnackbar(vb.root, "Tiner message bla bla bla", "onSuccess", "click", {}, {}, 5000)
        }

        vb.simple.setOnClickListener {
            (requireActivity() as AppCompatActivity).showSimpleSnackbar(vb.root, "Hello")
        }
        vb.customWarining.setOnClickListener {
            ChiliSnackBar.Builder(vb.root)
                .setSnackbarIcon(R.drawable.ic_cat)
                .setSnackbarDurationMills(2000)
                .setSnackbarMessage("Warning message")
                .build()
                .show()

        }

    }

    override fun inflateViewBinging(): FragmentSnackbarsBinding {
        return FragmentSnackbarsBinding.inflate(layoutInflater)
    }

}