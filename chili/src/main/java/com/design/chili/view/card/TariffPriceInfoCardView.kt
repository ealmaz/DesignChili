package com.design.chili.view.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.design.chili.R
import com.design.chili.extensions.setupRoundedCardCornersMode

class TariffPriceInfoCardView : ConstraintLayout {

    private lateinit var view: TariffPriceInfoCardViewVariables

    constructor(context: Context) : super(context) {
        inflateViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflateViews()
        obtainAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        inflateViews()
        obtainAttributes(attrs, defStyle)
    }

    private fun inflateViews() {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_tariff_price_info_card, this)
        this.view = TariffPriceInfoCardViewVariables(
            tvTitle = view.findViewById(R.id.tv_title),
            iicvVoiceOutInfo = view.findViewById(R.id.iicv_out_call),
            iicvVoiceInInfo = view.findViewById(R.id.iicv_in_call),
            iicvInternetInfo = view.findViewById(R.id.iicv_internet),
            iicvSmsInfo = view.findViewById(R.id.iicv_sms),
            divider = view.findViewById(R.id.divider),
            rootView = view.findViewById(R.id.rootView),
        )
    }

    private fun obtainAttributes(attrs: AttributeSet, defStyle: Int = R.style.Chili_CardViewStyle_TariffPriceInfoCardView) {
        context?.obtainStyledAttributes(attrs, R.styleable.TariffPriceInfoCardView, R.attr.tariffPriceInfoCardViewDefaultStyle, defStyle)?.run {
            getString(R.styleable.TariffPriceInfoCardView_title)?.let {
                setTitle(it)
            }

            getString(R.styleable.TariffPriceInfoCardView_voiceOutCost)?.let {
                setVoiceOutCost(it)
            }

            getString(R.styleable.TariffPriceInfoCardView_voiceInCost)?.let {
                setVoiceInCost(it)
            }
            getString(R.styleable.TariffPriceInfoCardView_internetCost)?.let {
                setInternetCost(it)
            }
            getString(R.styleable.TariffPriceInfoCardView_smsCost)?.let {
                setSmsCost(it)
            }
            getBoolean(R.styleable.TariffPriceInfoCardView_isDividerVisible, true).let {
                setDividerVisibility(it)
            }
            getInteger(R.styleable.TariffPriceInfoCardView_roundedCornerMode, 0).let {
                view.rootView.setupRoundedCardCornersMode(it)
            }

            recycle()
        }
    }

    fun setTitle(text: String) {
        view.tvTitle.text = text
    }

    fun setTitle(@StringRes resId: Int) {
        view.tvTitle.setText(resId)
    }

    fun setDividerVisibility(isVisible: Boolean) {
        view.divider.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun setupCosts(voiceOut: String, voiceIn: String, internet: String, sms: String) {
        setVoiceOutCost(voiceOut)
        setVoiceInCost(voiceIn)
        setInternetCost(internet)
        setSmsCost(sms)
    }

    fun setVoiceOutCost(cost: String) {
        view.iicvVoiceOutInfo.setValueText(cost)
    }

    fun setVoiceInCost(cost: String) {
        view.iicvVoiceInInfo.setValueText(cost)
    }

    fun setInternetCost(cost: String) {
        view.iicvInternetInfo.setValueText(cost)
    }

    fun setSmsCost(cost: String) {
        view.iicvSmsInfo.setValueText(cost)
    }

    fun setVoiceOutCost(@StringRes costResId: Int) {
        view.iicvVoiceOutInfo.setValueText(costResId)
    }

    fun setVoiceInCost(@StringRes costResId: Int) {
        view.iicvVoiceInInfo.setValueText(costResId)
    }

    fun setInternetCost(@StringRes costResId: Int) {
        view.iicvInternetInfo.setValueText(costResId)
    }

    fun setSmsCost(@StringRes costResId: Int) {
        view.iicvSmsInfo.setValueText(costResId)
    }

}

private data class TariffPriceInfoCardViewVariables(
    var tvTitle: TextView,
    var iicvVoiceOutInfo: ItemInfoCardView,
    var iicvVoiceInInfo: ItemInfoCardView,
    var iicvInternetInfo: ItemInfoCardView,
    var iicvSmsInfo: ItemInfoCardView,
    var divider: View,
    var rootView: ConstraintLayout,
)