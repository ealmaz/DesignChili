package com.design.chili.view.modals.in_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.design.chili.R
import com.design.chili.extensions.*
import com.design.chili.view.modals.base.BaseBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior

class InAppPushBottomSheet private constructor() : BaseBottomSheetDialogFragment() {

    override var hasCloseIcon: Boolean = true

    var rootView: View? = null
    var tvTitle: TextView? = null
    var tvDescription: TextView? = null
    var btnMore: Button? = null
    var ivBanner: ImageView? = null
    override var topDrawableView: View? = null
    override var closeIconView: View? = null

    private var bannerUrl: String? = null
    private var onBannerClick: (InAppPushBottomSheet.() -> Unit)? = null
    private var title: String? = null
    private var description: String? = null
    private var btnMoreInfo: Pair<String, InAppPushBottomSheet.() -> Unit>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Chili_InAppPushStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.chili_view_in_app_push, container, false)
        initViewVariables(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBanner()
        setupButton()
        setupTitle()
        setupDescription()
    }

    override fun setupBottomSheetBehavior(behavior: BottomSheetBehavior<*>?) {
        behavior?.run {
            isHideable = false
            peekHeight = getWindowHeight()
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun initViewVariables(view: View) {
        closeIconView = view.findViewById(R.id.btn_close)
        rootView = view.findViewById(R.id.fl_root_view)
        tvTitle = view.findViewById(R.id.tv_title)
        tvDescription = view.findViewById(R.id.tv_description)
        btnMore = view.findViewById(R.id.btn_more)
        ivBanner = view.findViewById(R.id.iv_banner)
    }

    private fun setupTitle() {
        tvTitle?.setTextOrHide(title)
    }

    private fun setupDescription() {
        tvDescription?.setTextOrHide(description)
    }

    private fun setupButton() {
        btnMore?.apply {
            when (btnMoreInfo == null) {
                true -> gone()
                else -> {
                    visible()
                    text = btnMoreInfo?.first
                    setOnSingleClickListener { btnMoreInfo?.second?.invoke(this@InAppPushBottomSheet) }
                }
            }
        }
    }

    private fun setupBanner() {
        ivBanner?.let { bannerView ->
            bannerView.setImageByUrlWithListener(
                imageUrl = bannerUrl,
                onSuccess = { resource ->
                    bannerView.visible()
                    bannerView.setImageDrawable(resource) },
                onError = { bannerView.gone() }
            )
        }
        ivBanner?.setOnSingleClickListener { onBannerClick?.invoke(this) }
    }

    class Builder {
        private var bannerUrl: String? = null
        private var onBannerClick: (InAppPushBottomSheet.() -> Unit)? = null
        private var title: String? = null
        private var description: String? = null
        private var btnMoreInfo: Pair<String, InAppPushBottomSheet.() -> Unit>? = null
        private var isHideable: Boolean = false

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setOnBannerClick(onBannerClick: (InAppPushBottomSheet.() -> Unit)?): Builder {
            this.onBannerClick = onBannerClick
            return this
        }

        fun setBannerUrl(bannerUrl: String): Builder {
            this.bannerUrl = bannerUrl
            return this
        }

        fun setDescription(description: String): Builder {
            this.description = description
            return this
        }

        fun setBtnMoreInfo(btnMoreInfo: Pair<String, InAppPushBottomSheet.() -> Unit>?): Builder {
            this.btnMoreInfo = btnMoreInfo
            return this
        }

        fun setIsHideable(isHideable: Boolean): Builder {
            this.isHideable = isHideable
            return this
        }

        fun build(): InAppPushBottomSheet {
            return InAppPushBottomSheet().apply {
                bannerUrl = this@Builder.bannerUrl
                onBannerClick = this@Builder.onBannerClick
                title = this@Builder.title
                description = this@Builder.description
                btnMoreInfo = this@Builder.btnMoreInfo
                isHideable = this@Builder.isHideable
            }
        }
    }
}