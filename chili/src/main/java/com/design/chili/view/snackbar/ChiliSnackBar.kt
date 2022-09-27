package com.design.chili.view.snackbar

import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.design.chili.R
import com.design.chili.extensions.*
import com.google.android.material.snackbar.BaseTransientBottomBar

class ChiliSnackBar private constructor(
    parent: ViewGroup,
    var content: SnackbarLayoutView)
    : BaseTransientBottomBar<ChiliSnackBar>(parent, content, content) {

    private var timer: CountDownTimer? = null

    init {
        getView().setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.transparent))
        getView().setPadding(0, 0, 0, 0)
    }

    fun setupVisibilityCallback(listener: SnackbarVisibilityStateListener?) {
        addCallback(object : BaseTransientBottomBar.BaseCallback<ChiliSnackBar>() {
            override fun onDismissed(transientBottomBar: ChiliSnackBar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                timer?.cancel()
                listener?.onDismissed()
            }

            override fun onShown(transientBottomBar: ChiliSnackBar?) {
                super.onShown(transientBottomBar)
                listener?.onShown()
                timer?.start()
            }
        })
    }

    fun setSnackbarIcon(@DrawableRes icon: Int = -1) {
        if (icon == -1) return
        content.apply {
            ivIcon.visible()
            ivIcon.setImageResource(icon)
            pbProgress.gone()
            tvSecondsLeft.gone()
        }
    }

    fun setupSnackbarAsLoading(isInfiniteLoaderSnackbar: Boolean) {
        if (!isInfiniteLoaderSnackbar) return
        setInfinitiveDuration()
        content.apply {
            ivIcon.invisible()
            tvSecondsLeft.gone()
            pbProgress.indeterminateDrawable = context?.drawable(R.drawable.chili_circular_loop_progress_bar)
            pbProgress.isIndeterminate = true
            pbProgress.visible()
        }
    }

    fun setInfinitiveDuration() {
        duration = LENGTH_INDEFINITE
    }

    fun setMessage(message: String?) {
        content.tvMessage.text = message
    }

    fun setMessage(@StringRes messageRes: Int) {
        content.tvMessage.setText(messageRes)
    }


    fun setupActionButton(actionInfo: ActionInfo?) {
        when (actionInfo == null) {
            true -> content.tvAction.gone()
            else -> {
                content.tvAction.apply {
                    visible()
                    text = actionInfo.title
                    setOnSingleClickListener { actionInfo.onClick?.invoke(this@ChiliSnackBar) }
                }
            }
        }
    }

    fun setupTimer(timerInfo: TimerInfo?) {
        if (timerInfo == null) return
        timer = when (timerInfo.showCountDownTimer) {
            true -> setupTimerWithCountDown(timerInfo)
            else -> setupInvisibleTimer(timerInfo)
        }
    }

    private fun setupInvisibleTimer(timerInfo: TimerInfo): CountDownTimer {
        return object : CountDownTimer(timerInfo.durationMills, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                timerInfo.onTimerExpire?.invoke(this@ChiliSnackBar)
            }
        }
    }

    private fun setupTimerWithCountDown(timerInfo: TimerInfo): CountDownTimer {
        content.ivIcon.invisible()
        content.tvSecondsLeft.visible()
        content.pbProgress.apply {
            visible()
            max = timerInfo.durationMills.toInt()
            progressDrawable = context?.drawable(R.drawable.chili_circular_progress_bar)
        }
        return object : CountDownTimer(timerInfo.durationMills, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                content.tvSecondsLeft.text = (millisUntilFinished / 1000 + 1).toString()
                content.pbProgress.progress = millisUntilFinished.toInt()
            }

            override fun onFinish() {
                timerInfo.onTimerExpire?.invoke(this@ChiliSnackBar)
            }
        }
    }

    class Builder(val rootView: View) {

        private var snackbarMessage: String? = null
        private var snackbarDurationMills: Long? = null
        private var snackbarActionInfo: ActionInfo? = null
        private var snackbarVisibilityStateListener: SnackbarVisibilityStateListener? = null

        private var snackbarTimerInfo: TimerInfo? = null
        @DrawableRes private var snackbarIcon: Int = -1
        private var isInfiniteLoaderSnackbar: Boolean = false

        fun setSnackbarMessage(snackbarMessage: String): Builder {
            this.snackbarMessage = snackbarMessage
            return this
        }
        fun setSnackbarDurationMills(snackbarDurationMills: Long): Builder {
            this.snackbarDurationMills = snackbarDurationMills
            return this
        }
        fun setSnackbarActionInfo(snackbarActionInfo: ActionInfo): Builder {
            this.snackbarActionInfo = snackbarActionInfo
            return this
        }
        fun setSnackbarVisibilityStateListener(snackbarVisibilityStateListener: SnackbarVisibilityStateListener): Builder {
            this.snackbarVisibilityStateListener = snackbarVisibilityStateListener
            return this
        }
        fun setSnackbarTimerInfo(snackbarTimerInfo: TimerInfo): Builder {
            this.snackbarTimerInfo = snackbarTimerInfo
            return this
        }
        fun setSnackbarIcon(@DrawableRes snackbarIcon: Int): Builder {
            this.snackbarIcon = snackbarIcon
            return this
        }
        fun setIsInfiniteLoaderSnackbar(isInfiniteLoaderSnackbar: Boolean): Builder {
            this.isInfiniteLoaderSnackbar = isInfiniteLoaderSnackbar
            return this
        }

        fun build(): ChiliSnackBar {
            val parent = rootView.findSuitableParent()
            val snackbarLayoutView = SnackbarLayoutView(parent.context)
            return ChiliSnackBar(parent, snackbarLayoutView).apply {
                setMessage(snackbarMessage)
                duration = snackbarDurationMills?.toInt() ?: 3000
                setupActionButton(snackbarActionInfo)
                setupVisibilityCallback(snackbarVisibilityStateListener)
                setupTimer(snackbarTimerInfo)
                setSnackbarIcon(snackbarIcon)
                setupSnackbarAsLoading(isInfiniteLoaderSnackbar)
            }
        }
    }
}

data class ActionInfo(
    var title: String? = null,
    var onClick: ((ChiliSnackBar) -> Unit)? = null
)

data class TimerInfo(
    var durationMills: Long = 0,
    var onTimerExpire: ((ChiliSnackBar) -> Unit)? = null,
    var showCountDownTimer: Boolean = true
)

open class SnackbarVisibilityStateListener {
    open fun onShown() {}
    open fun onDismissed() {}
}

private fun View?.findSuitableParent(): ViewGroup {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                return view
            } else {
                fallback = view
            }
        }

        if (view != null) {
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)
    return fallback ?: throw IllegalArgumentException(
        "No suitable parent found from the given view. Please provide a valid view.")
}
