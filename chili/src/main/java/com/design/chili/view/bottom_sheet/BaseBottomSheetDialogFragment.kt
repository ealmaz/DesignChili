package com.design.chili.view.bottom_sheet

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
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

    protected open val topDrawableVisible: Boolean = false
    protected open val hasCloseIcon: Boolean = false
    protected open val isHideable: Boolean = true
    protected open val isBackButtonEnabled: Boolean = true

    private lateinit var topDrawableView: View
    private lateinit var closeIconView: View

    @Nullable
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.view_base_bottom_sheet, container, false)
        val contentView = view.findViewById<LinearLayout>(R.id.ll_content)
        contentView.addView(createContentView(inflater, container))
        initViewVariables(view)
        return view
    }

    private fun initViewVariables(view: View) {
        topDrawableView = view.findViewById(R.id.iv_top_drawable)
        closeIconView = view.findViewById(R.id.iv_close)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view.parent as View).setBackgroundColor(Color.TRANSPARENT)
        setupBottomSheetCloseIcon()
        setupBottomSheetHideable()
        setupTopDrawableVisibility()
    }

    private fun setupBottomSheetCloseIcon() {
        closeIconView.apply {
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
            true -> topDrawableView.visible()
            else -> topDrawableView.gone()
        }
    }

    private fun setupBottomSheetHideable() {
        if (!isHideable) {
            val touchOutsideView = dialog!!.window?.decorView?.findViewById<View>(com.google.android.material.R.id.touch_outside)
            touchOutsideView?.setOnClickListener(null)
        }
    }

    private fun setupBottomSheetBehavior() {
        val bottomSheet: FrameLayout? = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.run {
            val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<View>(this)
            if (!isHideable) {
                behavior.isHideable = isHideable
                behavior.peekHeight = getWindowHeight() * 20 / 100
            }
            behavior.skipCollapsed = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun setupBottomSheetBackButton() {
        if (!isBackButtonEnabled) {
            dialog?.setOnKeyListener { _, keyCode, _ ->
                keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.ACTION_DOWN
            }
        }
    }

    abstract fun createContentView(inflater: LayoutInflater, @Nullable container: ViewGroup?): View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) updateNavbarColor(dialog)
        dialog.run {
            setOnShowListener { setupBottomSheetBehavior() }
            setupBottomSheetBackButton()
        }
        return dialog
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun updateNavbarColor(dialog: Dialog) {
        dialog.window?.updateNavigationBarColor()
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun show(fragmentManager: FragmentManager) {
        this.show(fragmentManager, null)
    }
}