package com.design.chili.view.bottom_sheet.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.design.chili.R

abstract class BaseFragmentBottomSheetDialogFragment : BaseBottomSheetDialogFragment() {

    override var topDrawableView: View? = null
    override var closeIconView: View? = null

    @Nullable
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.view_base_fragment_bottom_sheet, container, false)
        initViewVariables(view)
        childFragmentManager.beginTransaction()
            .replace(R.id.bottom_sheet_container, createFragment())
            .commit()
        return view
    }

    private fun initViewVariables(view: View) {
        topDrawableView = view.findViewById(R.id.iv_top_drawable)
        closeIconView = view.findViewById(R.id.iv_close)
    }

    abstract fun createFragment(): Fragment
}