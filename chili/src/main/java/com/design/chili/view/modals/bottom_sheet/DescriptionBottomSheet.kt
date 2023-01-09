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

class DescriptionBottomSheet : BaseViewBottomSheetDialogFragment() {

    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvDescriptionSecondary: TextView
    private lateinit var ivIcon: ImageView
    private lateinit var btnSecondary: Button

    private var title: String? = null
    private var titleSpanned: Spanned? = null
    private var titleResId: Int? = null

    private var description: String? = null
    private var descriptionSpanned: Spanned? = null
    private var descriptionResId: Int? = null

    private var descriptionSecondary: String? = null
    private var descriptionSecondarySpanned: Spanned? = null
    private var descriptionSecondaryResId: Int? = null

    private var iconRes: Int? = null

    private var secondaryButton: Pair<String, (DescriptionBottomSheet.() -> Unit)>? = null
    private var secondaryButtonRes: Pair<Int, (DescriptionBottomSheet.() -> Unit)>? = null

    override var topDrawableVisible = true

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        val view = inflater.inflate(R.layout.chili_view_description_bottom_sheet, container, false)
        tvTitle = view.findViewById(R.id.tv_title)
        tvDescription = view.findViewById(R.id.tv_desc)
        tvDescriptionSecondary = view.findViewById(R.id.tv_desc_secondary)
        ivIcon = view.findViewById(R.id.iv_icon)
        btnSecondary = view.findViewById(R.id.btn_secondary)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        title?.let { setTitle(it) }
        titleSpanned?.let { setTitle(it) }
        titleResId?.let { setTitle(it)}

        description?.let { setDescription(it) }
        descriptionSpanned?.let { setDescription(it) }
        descriptionResId?.let { setDescription(it)}

        descriptionSecondary?.let { setDescriptionSecondary(it) }
        descriptionSecondarySpanned?.let { setDescriptionSecondary(it) }
        descriptionSecondaryResId?.let { setDescriptionSecondary(it)}

        iconRes?.let { setIcon(it) }

        secondaryButton?.let { setSecondaryButton(it.first, it.second) }
        secondaryButtonRes?.let { setSecondaryButton(it.first, it.second) }
    }

    private fun setIcon(@DrawableRes resId: Int) {
        ivIcon.apply {
            setImageResource(resId)
            visible()
        }
    }

    private fun setTitle(@StringRes resId: Int) {
        tvTitle.apply {
            visible()
            setText(resId)
        }
    }

    private fun setTitle(message: String) {
        tvTitle.apply {
            visible()
            text = message
        }
    }

    private fun setTitle(spanned: Spanned?) {
        tvTitle.apply {
            text = spanned
            visible()
        }
    }

    private fun setDescription(@StringRes resId: Int) {
        tvDescription.apply {
            visible()
            setText(resId)
        }
    }

    private fun setDescription(message: String) {
        tvDescription.apply {
            visible()
            text = message
        }
    }

    private fun setDescription(spanned: Spanned?) {
        tvDescription.apply {
            text = spanned
            visible()
        }
    }

    private fun setDescriptionSecondary(@StringRes resId: Int) {
        tvDescriptionSecondary.apply {
            visible()
            setText(resId)
        }
    }

    private fun setDescriptionSecondary(message: String) {
        tvDescriptionSecondary.apply {
            visible()
            text = message
        }
    }

    private fun setDescriptionSecondary(spanned: Spanned?) {
        tvDescriptionSecondary.apply {
            text = spanned
            visible()
        }
    }

    private fun setSecondaryButton(@StringRes resId: Int, action: (DescriptionBottomSheet.() -> Unit)? = null) {
        btnSecondary.apply {
            visible()
            setText(resId)
            setOnSingleClickListener { action?.invoke(this@DescriptionBottomSheet) }
        }
    }

    private fun setSecondaryButton(text: String, action: (DescriptionBottomSheet.() -> Unit)? = null) {
        btnSecondary.apply {
            visible()
            setText(text)
            setOnSingleClickListener { action?.invoke(this@DescriptionBottomSheet) }
        }
    }

    class Builder {
        private var title: String? = null
        private var titleSpanned: Spanned? = null
        private var titleResId: Int? = null

        private var description: String? = null
        private var descriptionSpanned: Spanned? = null
        private var descriptionResId: Int? = null

        private var descriptionSecondary: String? = null
        private var descriptionSecondarySpanned: Spanned? = null
        private var descriptionSecondaryResId: Int? = null

        private var iconRes: Int? = null

        private var secondaryButton: Pair<String, (DescriptionBottomSheet.() -> Unit)>? = null
        private var secondaryButtonRes: Pair<Int, (DescriptionBottomSheet.() -> Unit)>? = null

        private var isHideable: Boolean = true

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setTitle(titleSpanned: Spanned): Builder {
            this.titleSpanned = titleSpanned
            return this
        }

        fun setTitle(titleResId: Int): Builder {
            this.titleResId = titleResId
            return this
        }

        fun setDescription(description: String): Builder {
            this.description = description
            return this
        }

        fun setDescription(descriptionSpanned: Spanned): Builder {
            this.descriptionSpanned = descriptionSpanned
            return this
        }

        fun setDescription(descriptionResId: Int): Builder {
            this.descriptionResId = descriptionResId
            return this
        }

        fun setDescriptionSecondary(descriptionSecondary: String): Builder {
            this.descriptionSecondary = descriptionSecondary
            return this
        }

        fun setDescriptionSecondary(descriptionSecondarySpanned: Spanned): Builder {
            this.descriptionSecondarySpanned = descriptionSecondarySpanned
            return this
        }

        fun setDescriptionSecondary(descriptionSecondaryResId: Int): Builder {
            this.descriptionSecondaryResId = descriptionSecondaryResId
            return this
        }

        fun setIcon(iconRes: Int): Builder {
            this.iconRes = iconRes
            return this
        }

        fun setSecondaryButton(secondaryButton: Pair<String, (DescriptionBottomSheet.() -> Unit)>): Builder {
            this.secondaryButton = secondaryButton
            return this
        }

        fun setSecondaryButtonRes(secondaryButtonRes: Pair<Int, (DescriptionBottomSheet.() -> Unit)>): Builder {
            this.secondaryButtonRes = secondaryButtonRes
            return this
        }

        fun setIsHideable(isHideable: Boolean): Builder {
            this.isHideable = isHideable
            return this
        }

        fun build(): DescriptionBottomSheet {
            return DescriptionBottomSheet().apply {
                this.title = this@Builder.title
                this.titleSpanned = this@Builder.titleSpanned
                this.titleResId = this@Builder.titleResId
                this.description = this@Builder.description
                this.descriptionSpanned = this@Builder.descriptionSpanned
                this.descriptionResId = this@Builder.descriptionResId
                this.descriptionSecondary = this@Builder.descriptionSecondary
                this.descriptionSecondarySpanned = this@Builder.descriptionSecondarySpanned
                this.descriptionSecondaryResId = this@Builder.descriptionSecondaryResId
                this.iconRes = this@Builder.iconRes
                this.secondaryButton = this@Builder.secondaryButton
                this.secondaryButtonRes = this@Builder.secondaryButtonRes
                this.isHideable = this@Builder.isHideable
            }
        }
    }

}