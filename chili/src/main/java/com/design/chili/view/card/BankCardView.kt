package com.design.chili.view.card

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.design.chili.R
import com.design.chili.extensions.invisible
import com.design.chili.extensions.visible
import java.lang.Exception


class BankCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.bankCardViewDefaultStyle,
    defStyleRes: Int = R.style.Chili_CardViewStyle_BankCard
) : CardView(context, attrs, defStyleAttr) {

    private var pan: String = ""
    private var panToggleState = CardFieldToggleState.ICON_NONE
    private var cardPanHideDelegate = {pan: CharSequence, isHidden: Boolean ->
        if (isHidden) {
            try { pan.replaceRange(6..11, " • • •  • •") }
            catch (_: Exception) {}
            pan

        } else pan
    }

    private var cvvToggleState = CardFieldToggleState.ICON_NONE
    private var cvv: String = ""
    private var cardCvvHideDelegate = {pan: CharSequence, isHidden: Boolean ->
        if (isHidden) "• • •"
        else pan
    }

    private lateinit var view: BankCardViewVariables

    init {
        inflateView(context)
        obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
        setupView()
    }

    private fun inflateView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.chili_view_bank_card, this, true)
        this.view = BankCardViewVariables(
            tvCardHolderName = view.findViewById(R.id.tv_card_holder_name),
            tvCardPan = view.findViewById(R.id.tv_card_pan),
            tvCvv = view.findViewById(R.id.tv_cvv),
            tvDueDate = view.findViewById(R.id.tv_due_date),
            ivIcon = view.findViewById(R.id.iv_card_icon),
            rootContainer = view.findViewById(R.id.root_view),
            ivPanToggle = view.findViewById(R.id.iv_pan_toggle),
            ivCvvToggle = view.findViewById(R.id.iv_cvv_toggle),
            llPan = view.findViewById(R.id.ll_card_pan),
            llCvv = view.findViewById(R.id.ll_cvv)
        )
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.BankCardView, defStyleAttr, defStyleRes).run {
            getBoolean(R.styleable.BankCardView_isCardPanToggleEnabled, false).let {
                if (it) setupCardPanToggle()
            }
            getBoolean(R.styleable.BankCardView_isCvvToggleEnabled, false).let {
                if (it) setupCvvToggle()
            }
            radius = getDimension(R.styleable.BankCardView_cardCornerRadius, 0f)
            cardElevation = getDimension(R.styleable.BankCardView_cardElevation, 0f)
            getResourceId(R.styleable.BankCardView_cardBackground, -1)
                .takeIf { it != -1 }?.let { setCardBackground(it) }
            setCardPan(getText(R.styleable.BankCardView_cardPan))
            setCardCvv(getText(R.styleable.BankCardView_cvv))
            setCardHolderName(getText(R.styleable.BankCardView_cardHolderName))
            setCardDueDate(getText(R.styleable.BankCardView_dueDate))
            setCardIcon(getResourceId(R.styleable.BankCardView_cardIcon, -1).takeIf { it != -1 })
            recycle()
        }
    }

    private fun setupView() {
        setCardBackgroundColor(Color.TRANSPARENT)
    }

    fun setCardBackground(resId: Int) {
        view.rootContainer.setBackgroundResource(resId)
    }

    fun setCardBackground(drawable: Drawable) {
        view.rootContainer.background = drawable
    }

    fun setCardPan(charSequence: CharSequence?) {
        if (charSequence == null) return
        this.pan = charSequence.toString()
        cardPanHideDelegate(charSequence, panToggleState == CardFieldToggleState.ICON_SHOW).let {
            view.tvCardPan.text = it
        }
    }

    fun setCardCvv(charSequence: CharSequence?) {
        if (charSequence == null) return
        this.cvv = charSequence.toString()
       cardCvvHideDelegate(charSequence, cvvToggleState == CardFieldToggleState.ICON_SHOW).let {
           view.tvCvv.text = it
       }
    }

    fun setCardDueDate(charSequence: CharSequence?) {
        view.tvDueDate.text = charSequence
    }

    fun setCardHolderName(charSequence: CharSequence?) {
        view.tvCardHolderName.text = charSequence
    }

    fun setCardPanTextAppearance(textAppearance: Int) {
        view.tvCardPan.setTextAppearance(textAppearance)
    }

    fun setCardCvvTextAppearance(textAppearance: Int) {
        view.tvCvv.setTextAppearance(textAppearance)
    }

    fun setCardDueDateTextAppearance(textAppearance: Int) {
        view.tvDueDate.setTextAppearance(textAppearance)
    }

    fun setCardHolderNameTextAppearance(textAppearance: Int) {
        view.tvCardHolderName.setTextAppearance(textAppearance)
    }

    fun setCardIcon(drawable: Drawable?) {
        view.ivIcon.apply {
            if (drawable != null) visible()
            else invisible()
            setImageDrawable(drawable)
        }
    }

    fun setCardIcon(resId: Int?) {
        view.ivIcon.apply {
            if (resId != null) {
                visible()
                setImageResource(resId)
            }
            else invisible()
        }
    }

    fun setCardPanHidingDelegate(delegate :((CharSequence?, Boolean) -> CharSequence)) {
        this.cardPanHideDelegate = delegate
    }

    fun setCvvHidingDelegate(delegate :((CharSequence?, Boolean) -> CharSequence)) {
        this.cardCvvHideDelegate = delegate
    }

    private fun setupCardPanToggle() = with(view) {
        ivPanToggle.setImageResource(R.drawable.chili_password_toggle_drawable)
        panToggleState = CardFieldToggleState.ICON_SHOW
        llPan.isClickable = true
        llPan.isFocusable = true
        llPan.setOnClickListener {
            if (panToggleState == CardFieldToggleState.ICON_SHOW) {
                panToggleState = CardFieldToggleState.ICON_COPY
                ivPanToggle.setImageResource(R.drawable.chili_ic_copy)
                tvCardPan.text = pan
            } else {
                copyText(pan)
            }
        }
    }

    private fun setupCvvToggle() = with(view) {
        ivCvvToggle.setImageResource(R.drawable.chili_password_toggle_drawable)
        cvvToggleState = CardFieldToggleState.ICON_SHOW
        llCvv.isClickable = true
        llCvv.isFocusable = true
        llCvv.setOnClickListener {
            if (cvvToggleState == CardFieldToggleState.ICON_SHOW) {
                cvvToggleState = CardFieldToggleState.ICON_COPY
                ivCvvToggle.setImageResource(R.drawable.chili_ic_copy)
                tvCvv.text = cvv
            } else {
                copyText(cvv)
            }
        }
    }

    private fun copyText(text: String) {
        val clipboard: ClipboardManager? = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText(text, text)
        clipboard?.setPrimaryClip(clip)
    }

}

data class BankCardViewVariables(
    val tvCardPan: TextView,
    val tvDueDate: TextView,
    val tvCvv: TextView,
    val tvCardHolderName: TextView,
    val ivIcon: ImageView,
    val ivCvvToggle: ImageView,
    val ivPanToggle: ImageView,
    val llCvv: LinearLayout,
    val llPan: LinearLayout,
    val rootContainer: ConstraintLayout
)

enum class CardFieldToggleState {
    ICON_SHOW, ICON_COPY, ICON_NONE
}