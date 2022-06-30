package com.design.chili.extensions

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.design.chili.R
import com.design.chili.util.RoundedCornerMode
import com.design.chili.view.image.SquircleView
import java.util.concurrent.TimeUnit

internal var View.lastItemClickTime: Long
    set(value) {
        setTag(R.id.lastItemClickTime, value)
    }
    get() {
        return getTag(R.id.lastItemClickTime) as? Long ?: 0L
    }

internal fun View.setOnSingleClickListener(action: () -> Unit) {
    setOnClickListener {
        if (lastItemClickTime == 0L
            || TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - lastItemClickTime) >= 1) {
            lastItemClickTime = System.currentTimeMillis()
            action()
        }
    }
}

internal fun View.setOnDoubleClickListener(action: () -> Unit) {
    setOnClickListener {
        val clickTime = System.currentTimeMillis()
        if (TimeUnit.MILLISECONDS.toSeconds(clickTime - lastItemClickTime) < 0.2) action()
        lastItemClickTime = clickTime
    }
}

internal fun View.visible() {
    visibility = View.VISIBLE
}

internal var View.lastCharInputTime: Long
    set(value) {
        setTag(R.id.lastCharInputTime, value)
    }
    get() {
        return getTag(R.id.lastCharInputTime) as? Long ?: 0L
    }

internal fun EditText.setOnTextChangedListenerWithDebounce(debounceSeconds: Long, action: () -> Unit) {
    addTextChangedListener {
        val changedTime = System.currentTimeMillis()
        if (TimeUnit.MILLISECONDS.toSeconds(changedTime - lastCharInputTime) > debounceSeconds) {
            action()
        }
    }
}

internal fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

internal fun View.invisible() {
    visibility = View.INVISIBLE
}

internal fun View.gone() {
    visibility = View.GONE
}

internal fun TextView.setTextOrHide(value: String?) {
    text = value
    when (value) {
        null -> gone()
        else -> visible()
    }
}

internal fun TextView.setTextOrHide(resId: Int?) {
    when (resId) {
        null -> gone()
        else -> {
            visible()
            setText(resId)
        }
    }
}

fun SquircleView.setImageByUrl(url: String?) {
    Glide.with(this)
        .load(url)
        .dontTransform()
        .into(this)
}

fun ImageView.setImageByUrl(url: String?) {
    Glide.with(this)
        .load(url)
        .dontTransform()
        .into(this)
}

fun View.setupRoundedCornersMode(modeValue: Int) {
    this.setBackgroundResource(
        when (modeValue) {
            RoundedCornerMode.TOP.value -> R.drawable.card_rounded_top_background
            RoundedCornerMode.MIDDLE.value -> R.drawable.card_rounded_middle_background
            RoundedCornerMode.BOTTOM.value -> R.drawable.card_rounded_bottom_background
            else -> R.drawable.card_rounded_background
        }
    )
}