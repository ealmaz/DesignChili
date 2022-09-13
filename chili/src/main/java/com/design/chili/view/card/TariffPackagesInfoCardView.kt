package com.design.chili.view.card

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.design.chili.R
import com.design.chili.extensions.*
import com.design.chili.extensions.gone
import com.design.chili.extensions.setTextOrHide
import com.design.chili.extensions.visible
import com.design.chili.view.common.AnimatedArcProgressView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TariffPackagesInfoCardView : ConstraintLayout {

    private lateinit var view: TariffPackagesInfoCardViewVariables

    constructor(context: Context) : super(context) {
        inflateViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflateViews()
        obtainAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context,
        attrs,
        defStyle) {
        inflateViews()
        obtainAttributes(attrs, defStyle)
    }

    private fun inflateViews() {
        val view =
            LayoutInflater.from(context).inflate(R.layout.chili_view_tariff_packages_info_card, this)
        this.view = TariffPackagesInfoCardViewVariables(
            rootView = view.findViewById(R.id.rootView),
            tvTitle = view.findViewById(R.id.tv_title),
            internetArc = view.findViewById(R.id.internet_arc),
            internetTitle = view.findViewById(R.id.internet_arc_title),
            internetSubtitle = view.findViewById(R.id.internet_arc_subtitle),
            internetIcon = view.findViewById(R.id.iv_internet_arc_icon),
            internetCaption = view.findViewById(R.id.tv_internet_arc_caption),
            internetFab = view.findViewById(R.id.fab_internet_arc),
            callArc = view.findViewById(R.id.call_arc),
            callTitle = view.findViewById(R.id.call_arc_title),
            callSubtitle = view.findViewById(R.id.call_arc_subtitle),
            callIcon = view.findViewById(R.id.iv_call_arc_icon),
            callCaption = view.findViewById(R.id.tv_call_arc_caption),
            callFab = view.findViewById(R.id.fab_call_arc),
            verticalDivider = view.findViewById(R.id.vertical_divider),
            divider = view.findViewById(R.id.divider),
        )
    }

    private fun obtainAttributes(
        attrs: AttributeSet,
        defStyle: Int = R.style.Chili_CardViewStyle_TariffPackagesInfoCardViewStyle
    ) {
        context?.obtainStyledAttributes(attrs, R.styleable.TariffPackagesInfoCardView, R.attr.tariffPackagesInfoCardViewDefaultStyle, defStyle)?.run {

            getString(R.styleable.TariffPackagesInfoCardView_title)?.let {
                setTitle(it)
            }

            getBoolean(R.styleable.TariffPackagesInfoCardView_isDividerVisible, true).let {
                setIsDividerVisible(it)
            }

            getInteger(R.styleable.TariffPackagesInfoCardView_roundedCornerMode, 0).let {
                view.rootView.setupRoundedCardCornersMode(it)
            }

            getString(R.styleable.TariffPackagesInfoCardView_internetArcTitle).let {
                setInternetTitle(it)
            }

            getString(R.styleable.TariffPackagesInfoCardView_internetArcSubtitle).let {
                setInternetSubtitle(it)
            }

            getString(R.styleable.TariffPackagesInfoCardView_internetArcCaption).let {
                setInternetCaptionText(it)
            }

            getDrawable(R.styleable.TariffPackagesInfoCardView_internetArcIcon).let {
                setInternetIcon(it)
            }

            getBoolean(R.styleable.TariffPackagesInfoCardView_internetArcFabVisibility, true).let {
                setInternetFabVisibility(it)
            }

            getString(R.styleable.TariffPackagesInfoCardView_callArcTitle).let {
                setCallTitle(it)
            }

            getString(R.styleable.TariffPackagesInfoCardView_callArcSubtitle).let {
                setCallSubtitle(it)
            }

            getString(R.styleable.TariffPackagesInfoCardView_callArcCaption).let {
                setCallCaptionText(it)
            }

            getDrawable(R.styleable.TariffPackagesInfoCardView_callArcIcon).let {
                setCallIcon(it)
            }

            getBoolean(R.styleable.TariffPackagesInfoCardView_callArcFabVisibility, true).let {
                setCallFabVisibility(it)
            }

            recycle()
        }

    }

    fun setTitle(text: String?) {
        view.tvTitle.text = text
    }

    fun setTitle(@StringRes resId: Int) {
        view.tvTitle.setText(resId)
    }

    fun setInternetTitle(@StringRes resId: Int?) {
        view.internetTitle.setTextOrHide(resId)
    }

    fun setInternetTitle(title: String?) {
        view.internetTitle.setTextOrHide(title)
    }

    fun setInternetSubtitle(@StringRes resId: Int?) {
        view.internetSubtitle.setTextOrHide(resId)
    }

    fun setInternetSubtitle(title: String?) {
        view.internetSubtitle.setTextOrHide(title)
    }

    fun setInternetIcon(drawable: Drawable?) {
        view.internetIcon.apply {
            setImageDrawable(drawable)
            when (drawable) {
                null -> gone()
                else -> visible()
            }
        }
    }

    fun setInternetIcon(@DrawableRes drawableRes: Int?) {
        view.internetIcon.apply {
            when (drawableRes) {
                null -> gone()
                else -> {
                    visible()
                    setImageResource(drawableRes)
                }
            }
        }
    }

    fun setInternetCaptionText(text: String?) {
        view.internetCaption.setTextOrHide(text)
    }

    fun setInternetCaptionText(@StringRes resId: Int?) {
        view.internetCaption.setTextOrHide(resId)
    }

    fun setOnInternetFabClickListener(onClick: () -> Unit) {
        view.internetFab.setOnClickListener { onClick() }
    }

    fun setInternetFabVisibility(isVisible: Boolean) {
        view.internetFab.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    fun setCallTitle(@StringRes resId: Int?) {
        view.callTitle.setTextOrHide(resId)
    }

    fun setCallTitle(title: String?) {
        view.callTitle.setTextOrHide(title)
    }

    fun setCallSubtitle(@StringRes resId: Int?) {
        view.callSubtitle.setTextOrHide(resId)
    }

    fun setCallSubtitle(title: String?) {
        view.callSubtitle.setTextOrHide(title)
    }

    fun setCallIcon(drawable: Drawable?) {
        view.callIcon.apply {
            setImageDrawable(drawable)
            when (drawable) {
                null -> gone()
                else -> visible()
            }
        }
    }

    fun setCallIcon(@DrawableRes drawableRes: Int?) {
        view.callIcon.apply {
            when (drawableRes) {
                null -> gone()
                else -> {
                    visible()
                    setImageResource(drawableRes)
                }
            }
        }
    }

    fun setCallCaptionText(text: String?) {
        view.callCaption.setTextOrHide(text)
    }

    fun setCallCaptionText(@StringRes resId: Int?) {
        view.callCaption.setTextOrHide(resId)
    }

    fun setOnCallFabClickListener(onClick: () -> Unit) {
        view.callFab.setOnClickListener { onClick() }
    }

    fun setCallFabVisibility(isVisible: Boolean) {
        view.callFab.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    fun setInternetRemain(limit: Long, remain: Long) {
        view.internetArc.setArcProgress(limit, remain, true)
    }

    fun setCallRemain(limit: Long, remain: Long) {
        view.callArc.setArcProgress(limit, remain)
    }

    fun setIsVerticalDividerVisible(isVisible: Boolean) {
        view.verticalDivider.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun setIsDividerVisible(isVisible: Boolean) {
        view.divider.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

}

private data class TariffPackagesInfoCardViewVariables(
    var rootView: ConstraintLayout,
    var tvTitle: TextView,
    var internetArc: AnimatedArcProgressView,
    var internetTitle: TextView,
    var internetSubtitle: TextView,
    var internetIcon: ImageView,
    var internetCaption: TextView,
    var internetFab: FloatingActionButton,
    var callArc: AnimatedArcProgressView,
    var callTitle: TextView,
    var callSubtitle: TextView,
    var callIcon: ImageView,
    var callCaption: TextView,
    var callFab: FloatingActionButton,
    var verticalDivider: View,
    var divider: View,
)