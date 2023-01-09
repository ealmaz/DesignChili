package com.design.chili.view.modals.bottom_sheet

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.design.chili.R
import com.design.chili.view.modals.base.BaseFragmentBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior

class FragmentBottomSheet : BaseFragmentBottomSheetDialogFragment() {

    private var contentFragment: Fragment? = null

    override var topDrawableVisible: Boolean = true
    override var hasCloseIcon: Boolean = true
    override var isHideable: Boolean = false
    override var isBackButtonEnabled: Boolean = false

    override fun setupBottomSheetBehavior(behavior: BottomSheetBehavior<*>?) {
        behavior?.peekHeight = getWindowHeight() * 30 / 100
        behavior?.isHideable = isHideable
        behavior?.halfExpandedRatio = 0.3f
        behavior?.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    override fun createFragment(): Fragment {
        return contentFragment ?: Fragment()
    }

    class Builder {

        private var contentFragment: Fragment? = null
        private var topDrawableVisible: Boolean = true
        private var hasCloseIcon: Boolean = true
        private var isHideable: Boolean = false
        private var isBackButtonEnabled: Boolean = false
        @DrawableRes private var backgroundDrawable: Int = R.drawable.chili_bg_rounded_bottom_sheet

        fun setContentFragment(contentFragment: Fragment): Builder {
            this.contentFragment = contentFragment
            return this
        }

        fun setDrawableVisible(topDrawableVisible: Boolean): Builder {
            this.topDrawableVisible = topDrawableVisible
            return this
        }

        fun setHasCloseIcon(hasCloseIcon: Boolean): Builder {
            this.hasCloseIcon = hasCloseIcon
            return this
        }

        fun setIsHideable(isHideable: Boolean): Builder {
            this.isHideable = isHideable
            return this
        }

        fun setBackgroundDrawable(@DrawableRes backgroundDrawable: Int): Builder {
            this.backgroundDrawable = backgroundDrawable
            return this
        }

        fun setIsBackButtonEnabled(isBackButtonEnabled: Boolean): Builder {
            this.isBackButtonEnabled = isBackButtonEnabled
            return this
        }

        fun build(): FragmentBottomSheet {
            return FragmentBottomSheet().apply {
                contentFragment = this@Builder.contentFragment
                topDrawableVisible = this@Builder.topDrawableVisible
                hasCloseIcon = this@Builder.hasCloseIcon
                isHideable = this@Builder.isHideable
                isBackButtonEnabled = this@Builder.isBackButtonEnabled
                backgroundDrawable = this@Builder.backgroundDrawable
            }
        }

    }

}