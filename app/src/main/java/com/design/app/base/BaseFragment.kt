package com.design.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.design.app.MainActivity

abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    protected lateinit var vb: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        vb = inflateViewBinging()
        return vb.root
    }

    abstract fun inflateViewBinging(): VB

    fun openFragment(fragment: Fragment) {
        (requireActivity() as MainActivity).openFragment(fragment)
    }
}