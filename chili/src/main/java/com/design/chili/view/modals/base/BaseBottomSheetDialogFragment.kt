package com.design.chili.view.modals.base

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.design.chili.R
import com.design.chili.extensions.gone
import com.design.chili.extensions.setOnSingleClickListener
import com.design.chili.extensions.updateNavigationBarColor
import com.design.chili.extensions.visible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    protected open var topDrawableVisible: Boolean = false
    protected open var hasCloseIcon: Boolean = false
    protected open var isHideable: Boolean = true
    protected open var isBackButtonEnabled: Boolean = true

    abstract var topDrawableView: View?
    abstract var closeIconView: View?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Chili_BottomSheetStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) updateNavbarColor(dialog)
        dialog.run {
            setOnShowListener { onShowDialog(this) }
            setupBottomSheetBackButtonEnabled(dialog)
        }
        return dialog
    }

    private fun onShowDialog(dialog: Dialog) {
        val bottomSheet: FrameLayout? = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = bottomSheet?.let { BottomSheetBehavior.from<View>(bottomSheet) }
        setupBottomSheetBehavior(behavior)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view.parent as View).setBackgroundColor(Color.TRANSPARENT)
        setupBottomSheetCloseIcon()
        setupBottomSheetHideable()
        setupTopDrawableVisibility()
    }


    private fun setupBottomSheetCloseIcon() {
        closeIconView?.apply {
            when (hasCloseIcon) {
                true -> {
                    visible()
                    setOnSingleClickListener { dismiss() }
                }
                else -> gone()
            }
        }
    }

    private fun setupTopDrawableVisibility() {
        when (topDrawableVisible) {
            true -> topDrawableView?.visible()
            else -> topDrawableView?.gone()
        }
    }

    private fun setupBottomSheetHideable() {
        if (!isHideable) {
            val touchOutsideView = dialog!!.window?.decorView?.findViewById<View>(com.google.android.material.R.id.touch_outside)
            touchOutsideView?.setOnClickListener(null)
        }
    }

    open fun setupBottomSheetBehavior(behavior: BottomSheetBehavior<*>?) {
        behavior?.run {
            if (!this@BaseBottomSheetDialogFragment.isHideable) {
                isHideable = this@BaseBottomSheetDialogFragment.isHideable
                peekHeight = getWindowHeight() * 20 / 100
            }
            skipCollapsed = true
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun setupBottomSheetBackButtonEnabled(dialog: BottomSheetDialog) {
        if (!isBackButtonEnabled) {
            dialog.setOnKeyListener { _, keyCode, _ ->
                keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.ACTION_DOWN
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun updateNavbarColor(dialog: Dialog) {
        dialog.window?.updateNavigationBarColor()
    }

    protected fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun show(fragmentManager: FragmentManager) {
        this.show(fragmentManager, null)
    }
}