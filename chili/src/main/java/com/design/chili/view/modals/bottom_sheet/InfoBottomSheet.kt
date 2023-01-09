package com.design.chili.view.modals.bottom_sheet

import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.design.chili.R
import com.design.chili.extensions.setOnSingleClickListener
import com.design.chili.extensions.visible
import com.design.chili.view.modals.base.BaseViewBottomSheetDialogFragment

class InfoBottomSheet private constructor(): BaseViewBottomSheetDialogFragment() {

    private lateinit var tvText: TextView
    private lateinit var ivIcon: ImageView
    private lateinit var btnPrimary: Button
    private lateinit var btnSecondary: Button

    private var text: String? = null
    private var textSpanned: Spanned? = null
    private var textResId: Int? = null

    private var iconRes: Int? = null

    private var primaryButton: Pair<String, (InfoBottomSheet.() -> Unit)>? = null
    private var secondaryButton: Pair<String, (InfoBottomSheet.() -> Unit)>? = null
    private var primaryButtonRes: Pair<Int, (InfoBottomSheet.() -> Unit)>? = null
    private var secondaryButtonRes: Pair<Int, (InfoBottomSheet.() -> Unit)>? = null

    override var hasCloseIcon: Boolean = true

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.chili_view_info_bottom_sheet, container, false)
        tvText = view.findViewById(R.id.tv_text)
        ivIcon = view.findViewById(R.id.iv_icon)
        btnPrimary = view.findViewById(R.id.btn_primary)
        btnSecondary = view.findViewById(R.id.btn_secondary)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        text?.let { setMessage(it) }
        textSpanned?.let { setMessage(it) }
        textResId?.let { setMessage(it)}

        iconRes?.let { setIcon(it) }

        primaryButton?.let { setPrimaryButton(it.first, it.second) }
        secondaryButton?.let { setSecondaryButton(it.first, it.second) }
        primaryButtonRes?.let { setPrimaryButton(it.first, it.second) }
        secondaryButtonRes?.let { setSecondaryButton(it.first, it.second) }
    }

    private fun setIcon(@DrawableRes resId: Int) {
        ivIcon.apply {
            setImageResource(resId)
            visible()
        }
    }

    private fun setMessage(@StringRes resId: Int) {
        tvText.apply {
            visible()
            setText(resId)
        }
    }

    private fun setMessage(message: String) {
        tvText.apply {
            visible()
            text = message
        }
    }

    private fun setMessage(spanned: Spanned?) {
        tvText.apply {
            text = spanned
            visible()
        }
    }

    private fun setPrimaryButton(@StringRes resId: Int, action: (InfoBottomSheet.() -> Unit)? = null) {
        btnPrimary.apply {
            visible()
            setText(resId)
            setOnSingleClickListener { action?.invoke(this@InfoBottomSheet) }
        }
    }

    private fun setSecondaryButton(@StringRes resId: Int, action: (InfoBottomSheet.() -> Unit)? = null) {
        btnSecondary.apply {
            visible()
            setText(resId)
            setOnSingleClickListener { action?.invoke(this@InfoBottomSheet) }
        }
    }

    private fun setPrimaryButton(text: String, action: (InfoBottomSheet.() -> Unit)? = null) {
        btnPrimary.apply {
            visible()
            setText(text)
            setOnSingleClickListener { action?.invoke(this@InfoBottomSheet) }
        }
    }

    private fun setSecondaryButton(text: String, action: (InfoBottomSheet.() -> Unit)? = null) {
        btnSecondary.apply {
            visible()
            setText(text)
            setOnSingleClickListener { action?.invoke(this@InfoBottomSheet) }
        }
    }

    class Builder {
        private var text: String? = null
        private var textSpanned: Spanned? = null
        private var textResId: Int? = null

        private var iconRes: Int? = null

        private var primaryButton: Pair<String, (InfoBottomSheet.() -> Unit)>? = null
        private var secondaryButton: Pair<String, (InfoBottomSheet.() -> Unit)>? = null
        private var primaryButtonRes: Pair<Int, (InfoBottomSheet.() -> Unit)>? = null
        private var secondaryButtonRes: Pair<Int, (InfoBottomSheet.() -> Unit)>? = null

        private var isHideable: Boolean = true

        fun setMessage(text: String): Builder {
            this.text = text
            return this
        }

        fun setMessage(textSpanned: Spanned): Builder {
            this.textSpanned = textSpanned
            return this
        }

        fun setMessage(textResId: Int): Builder {
            this.textResId = textResId
            return this
        }

        fun setIcon(iconRes: Int): Builder {
            this.iconRes = iconRes
            return this
        }

        fun setPrimaryButton(primaryButton: Pair<String, (InfoBottomSheet.() -> Unit)>): Builder {
            this.primaryButton = primaryButton
            return this
        }

        fun setSecondaryButton(secondaryButton: Pair<String, (InfoBottomSheet.() -> Unit)>): Builder {
            this.secondaryButton = secondaryButton
            return this
        }

        fun setPrimaryButtonRes(primaryButtonRes: Pair<Int, (InfoBottomSheet.() -> Unit)>): Builder {
            this.primaryButtonRes = primaryButtonRes
            return this
        }

        fun setSecondaryButtonRes(secondaryButtonRes: Pair<Int, (InfoBottomSheet.() -> Unit)>): Builder {
            this.secondaryButtonRes = secondaryButtonRes
            return this
        }

        fun setIsHideable(isHideable: Boolean): Builder {
            this.isHideable = isHideable
            return this
        }

        fun build(): InfoBottomSheet {
            return InfoBottomSheet().apply {
                this.text = this@Builder.text
                this.textSpanned = this@Builder.textSpanned
                this.textResId = this@Builder.textResId
                this.iconRes = this@Builder.iconRes
                this.primaryButton = this@Builder.primaryButton
                this.secondaryButton = this@Builder.secondaryButton
                this.primaryButtonRes = this@Builder.primaryButtonRes
                this.secondaryButtonRes = this@Builder.secondaryButtonRes
                this.isHideable = this@Builder.isHideable
            }
        }
    }
}