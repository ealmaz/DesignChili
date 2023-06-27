package com.design.chili.view.tooltip

import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.design.chili.R
import com.design.chili.extensions.gone
import com.design.chili.extensions.visible

class TooltipView(builder: Builder) : PopupWindow.OnDismissListener {

    private lateinit var view: TooltipVariables

    private var mDefaultPopupWindowStyleRes = android.R.attr.popupWindowStyle
    private var mOnShowListener: OnShowListener? = null
    private var mOnDismissListener: OnDismissListener? = null
    private var mOnCloseBtnListener: OnCloseBtnListener? = null
    private var mOnClickListener: OnClickListener? = null

    private var mPopupWindow: PopupWindow? = null
    private var mContext: Context
    private var mTitle: CharSequence
    private var mSubtitle: CharSequence
    private var mRootView: ViewGroup?
    private var mAnchorView: View?
    private var mContentLayout: View? = null
    private var mGravity: Int
    private var mArrowAlign: Int
    private var mFocusable: Boolean
    private var mMargin = 0f

    init {
        mContext = builder.context
        mTitle = builder.title
        mSubtitle = builder.subtitle
        mAnchorView = builder.anchorView
        mRootView = TooltipUtils.findFrameLayout(mAnchorView)
        mGravity = builder.gravity
        mArrowAlign = builder.arrowAlign
        mMargin = builder.margin
        mFocusable = builder.focusable
        mOnShowListener = builder.onShowListener
        mOnDismissListener = builder.onDismissListener
        mOnCloseBtnListener = builder.onCloseBtnListener
        mOnClickListener = builder.onClickListener
        init()
    }

    private fun init() {
        configPopupWindow()
        inflateViews()
        configContentView()
    }

    private fun configPopupWindow() {
        mPopupWindow = PopupWindow(mContext, null, mDefaultPopupWindowStyleRes).apply {
            width = LinearLayout.LayoutParams.MATCH_PARENT
            setOnDismissListener(this@TooltipView)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isOutsideTouchable = true
            isClippingEnabled = false
            isClippingEnabled = false
            isFocusable = mFocusable
        }
    }

    private fun inflateViews() {
        val view = LayoutInflater.from(mContext).inflate(R.layout.chili_view_tooltip, null)
        this.view = TooltipVariables(
            root = view.findViewById(R.id.cl_root),
            title = view.findViewById(R.id.tv_title),
            subtitle = view.findViewById(R.id.tv_subtitle),
            close = view.findViewById(R.id.img_close),
            topArrowStart = view.findViewById(R.id.top_arrow_start),
            topArrowCenter = view.findViewById(R.id.top_arrow_center),
            topArrowEnd = view.findViewById(R.id.top_arrow_end),
            bottomArrowStart = view.findViewById(R.id.bottom_arrow_start),
            bottomArrowCenter = view.findViewById(R.id.bottom_arrow_center),
            bottomArrowEnd = view.findViewById(R.id.bottom_arrow_end)
        )
        mContentLayout = view
    }

    private fun configContentView() {
        with(view) {
            root.setOnClickListener { onClick() }
            title.text = mTitle
            subtitle.text = mSubtitle
            close.setOnClickListener { onCloseBtnClick() }
        }
        initArrow()
        mPopupWindow?.contentView = mContentLayout
    }

    private fun initArrow() {
        when {
            mGravity == GRAVITY_TOP && mArrowAlign == ALIGN_START -> {
                hideAllArrows()
                view.bottomArrowStart.visible()
            }

            mGravity == GRAVITY_TOP && mArrowAlign == ALIGN_CENTER -> {
                hideAllArrows()
                view.bottomArrowCenter.visible()
            }

            mGravity == GRAVITY_TOP && mArrowAlign == ALIGN_END -> {
                hideAllArrows()
                view.bottomArrowEnd.visible()
            }

            mGravity == GRAVITY_BOTTOM && mArrowAlign == ALIGN_START -> {
                hideAllArrows()
                view.topArrowStart.visible()
            }

            mGravity == GRAVITY_BOTTOM && mArrowAlign == ALIGN_CENTER -> {
                hideAllArrows()
                view.topArrowCenter.visible()
            }

            mGravity == GRAVITY_BOTTOM && mArrowAlign == ALIGN_END -> {
                hideAllArrows()
                view.topArrowEnd.visible()
            }

            else -> hideAllArrows()
        }
    }

    private fun hideAllArrows() {
        with(view) {
            topArrowStart.gone()
            topArrowCenter.gone()
            topArrowEnd.gone()
            bottomArrowStart.gone()
            bottomArrowCenter.gone()
            bottomArrowEnd.gone()
        }
    }

    private fun onCloseBtnClick() {
        dismiss()
        mOnCloseBtnListener?.onClose(this)
    }

    private fun onClick() {
        mOnClickListener?.onClick(this@TooltipView)
    }

    fun show() {
        if (mPopupWindow?.isShowing == true) return
        mContentLayout?.viewTreeObserver?.addOnGlobalLayoutListener(mLocationLayoutListener)

        mRootView?.post {
            mPopupWindow?.showAtLocation(
                mRootView,
                Gravity.NO_GRAVITY,
                mRootView?.width ?: 0,
                mRootView?.height ?: 0
            )
        }
    }

    private val mLocationLayoutListener: OnGlobalLayoutListener = object : OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            val popup = mPopupWindow ?: return

            TooltipUtils.removeOnGlobalLayoutListener(popup.contentView, this)

            popup.contentView.viewTreeObserver.addOnGlobalLayoutListener(mShowLayoutListener)

            calculatePopupLocation()?.let {
                popup.isClippingEnabled = true
                popup.update(it.x.toInt(), it.y.toInt(), popup.width, popup.height)
                popup.contentView.requestLayout()
            }
        }
    }

    private val mShowLayoutListener: OnGlobalLayoutListener = object : OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            val popup = mPopupWindow ?: return
            TooltipUtils.removeOnGlobalLayoutListener(popup.contentView, this)
            mOnShowListener?.onShow(this@TooltipView)
        }
    }

    private fun calculatePopupLocation(): PointF? {
        val popup = mPopupWindow ?: return null
        val anchorView = mAnchorView ?: return null

        val location = PointF()
        val anchorRect: RectF = TooltipUtils.calculateRectInWindow(anchorView)
        val anchorCenter = PointF(anchorRect.centerX(), anchorRect.centerY())

        when {
            mGravity == GRAVITY_TOP && mArrowAlign == ALIGN_START -> {
                location.x = anchorCenter.x - popup.contentView.width / 2f
                location.y = anchorRect.top - popup.contentView.height - mMargin
            }

            mGravity == GRAVITY_TOP && mArrowAlign == ALIGN_CENTER -> {
                location.x = anchorCenter.x - popup.contentView.width / 2f
                location.y = anchorRect.top - popup.contentView.height - mMargin
            }

            mGravity == GRAVITY_TOP && mArrowAlign == ALIGN_END -> {
                location.x = anchorCenter.x - popup.contentView.width / 2f
                location.y = anchorRect.top - popup.contentView.height - mMargin
            }

            mGravity == GRAVITY_BOTTOM && mArrowAlign == ALIGN_START -> {
                location.x = anchorCenter.x - popup.contentView.width / 2f
                location.y = anchorRect.bottom + mMargin
            }

            mGravity == GRAVITY_BOTTOM && mArrowAlign == ALIGN_CENTER -> {
                location.x = anchorCenter.x - popup.contentView.width / 2f
                location.y = anchorRect.bottom + mMargin
            }

            mGravity == GRAVITY_BOTTOM && mArrowAlign == ALIGN_END -> {
                location.x = anchorCenter.x - popup.contentView.width / 2f
                location.y = anchorRect.bottom + mMargin
            }

            mGravity == GRAVITY_CENTER -> {
                location.x = anchorCenter.x - popup.contentView.width / 2f
                location.y = anchorCenter.y - popup.contentView.height / 2f
            }
        }
        return location
    }

    override fun onDismiss() {
        TooltipUtils.removeOnGlobalLayoutListener(
            mPopupWindow?.contentView,
            mLocationLayoutListener
        )
        mOnDismissListener?.onDismiss(this)

        TooltipUtils.removeOnGlobalLayoutListener(
            mPopupWindow?.contentView,
            mLocationLayoutListener
        )
        TooltipUtils.removeOnGlobalLayoutListener(mPopupWindow?.contentView, mShowLayoutListener)
    }

    fun dismiss() {
        mPopupWindow?.dismiss()
    }

    interface OnShowListener {
        fun onShow(tooltipView: TooltipView)
    }

    interface OnDismissListener {
        fun onDismiss(tooltipView: TooltipView)
    }

    interface OnCloseBtnListener {
        fun onClose(tooltipView: TooltipView)
    }

    interface OnClickListener {
        fun onClick(tooltipView: TooltipView)
    }

    class Builder(context: Context) {
        var onShowListener: OnShowListener? = null
        var onDismissListener: OnDismissListener? = null
        var onCloseBtnListener: OnCloseBtnListener? = null
        var onClickListener: OnClickListener? = null

        val context: Context
        var title: CharSequence = ""
        var subtitle: CharSequence = ""
        var anchorView: View? = null
        var gravity: Int = GRAVITY_BOTTOM
        var arrowAlign: Int = ALIGN_CENTER
        var focusable: Boolean = true
        var margin = 0f

        init {
            this.context = context
        }

        fun title(text: CharSequence): Builder {
            title = text; return this
        }

        fun title(@StringRes textRes: Int): Builder {
            title = context.getString(textRes); return this
        }

        fun subtitle(text: CharSequence): Builder {
            subtitle = text; return this
        }

        fun subtitle(@StringRes textRes: Int): Builder {
            subtitle = context.getString(textRes); return this
        }

        fun anchorView(view: View): Builder {
            anchorView = view; return this
        }

        fun gravity(gr: Int): Builder {
            gravity = gr; return this
        }

        fun arrowAlign(align: Int): Builder {
            arrowAlign = align; return this
        }

        fun margin(mar: Float): Builder {
            margin = mar; return this
        }

        fun margin(@DimenRes marginRes: Int): Builder {
            margin = context.resources.getDimension(marginRes)
            return this
        }

        fun onShowListener(listener: OnShowListener): Builder {
            onShowListener = listener
            return this
        }

        fun onDismissListener(listener: OnDismissListener): Builder {
            onDismissListener = listener
            return this
        }

        fun onCloseBtnListener(listener: OnCloseBtnListener): Builder {
            onCloseBtnListener = listener
            return this
        }

        fun onClickListener(listener: OnClickListener): Builder {
            onClickListener = listener
            return this
        }

        fun build(): TooltipView {
            return TooltipView(this)
        }
    }

    companion object {
        const val GRAVITY_TOP = 0
        const val GRAVITY_BOTTOM = 1
        const val GRAVITY_CENTER = 4

        const val ALIGN_CENTER = 0
        const val ALIGN_START = 1
        const val ALIGN_END = 2
    }
}

private data class TooltipVariables(
    val root: ConstraintLayout,
    var title: TextView,
    var subtitle: TextView,
    var close: ImageView,
    var topArrowStart: ImageView,
    var topArrowCenter: ImageView,
    var topArrowEnd: ImageView,
    var bottomArrowStart: ImageView,
    var bottomArrowCenter: ImageView,
    var bottomArrowEnd: ImageView
)