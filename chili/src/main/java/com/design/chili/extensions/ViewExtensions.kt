package com.design.chili.extensions

import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Spanned
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
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

fun View.setOnSingleClickListener(action: () -> Unit) {
    setOnClickListener {
        if (lastItemClickTime == 0L
            || TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - lastItemClickTime) >= 1) {
            lastItemClickTime = System.currentTimeMillis()
            action()
        }
    }
}

fun View.setOnDoubleClickListener(action: () -> Unit) {
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

internal fun TextView.setTextOrHide(value: Spanned?) {
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

internal fun TextView.setTextOrHide(charSequence: CharSequence?) {
    text = charSequence
    if (charSequence == null) gone()
    else visible()
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

fun ImageView.setImageByUrlWithListener(imageUrl: String?, onSuccess: ((Drawable) -> (Unit))? = null,
                                    onError: ((GlideException?) -> (Unit))? = null, requestOptions: RequestOptions? = null) {
    if (imageUrl == null) {
        onError?.invoke(null)
        return
    }
    val builder = Glide.with(this.context)
        .load(imageUrl)
        .listener(getGlideOnLoadListener(onSuccess, onError))
    if (requestOptions != null) builder.apply(requestOptions)
    builder.into(this)
}

private fun getGlideOnLoadListener(onSuccess: ((Drawable) -> Unit)?, onError: ((GlideException?) -> Unit)?): RequestListener<Drawable>? {
    val isHaveActionParams = onSuccess != null || onError != null
    if (!isHaveActionParams) return null
    else return object: RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?,
                                  model: Any?,
                                  target: Target<Drawable>?,
                                  isFirstResource: Boolean): Boolean {
            onError?.invoke(e)
            return true
        }

        override fun onResourceReady(resource: Drawable,
                                     model: Any?,
                                     target: Target<Drawable>?,
                                     dataSource: DataSource?,
                                     isFirstResource: Boolean): Boolean {
            onSuccess?.invoke(resource)
            return true
        }
    }
}

fun View.setupRoundedCardCornersMode(modeValue: Int) {
    this.setBackgroundResource(
        when (modeValue) {
            RoundedCornerMode.TOP.value -> R.drawable.chili_card_rounded_top_background
            RoundedCornerMode.MIDDLE.value -> R.drawable.chili_card_rounded_middle_background
            RoundedCornerMode.BOTTOM.value -> R.drawable.chili_card_rounded_bottom_background
            RoundedCornerMode.WITHOUT_ROUNDS.value -> R.drawable.chili_card_without_rounds_background
            else -> R.drawable.chili_card_rounded_background
        }
    )
}

fun View.setupRoundedCellCornersMode(modeValue: Int) {
    this.setBackgroundResource(
        when (modeValue) {
            RoundedCornerMode.TOP.value -> R.drawable.chili_cell_rounded_top_background
            RoundedCornerMode.MIDDLE.value -> R.drawable.chili_cell_rounded_middle_background
            RoundedCornerMode.BOTTOM.value -> R.drawable.chili_cell_rounded_bottom_background
            else -> R.drawable.chili_cell_rounded_background
        }
    )
}

fun ViewGroup.setIsSurfaceClickable(isSurfaceClickable: Boolean) {
    isClickable = isSurfaceClickable
    isFocusable = isSurfaceClickable
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        foreground = when (isSurfaceClickable) {
            true -> AppCompatResources.getDrawable(context, R.drawable.chili_ripple_rounded_corner_foreground)
            else -> null
        }
    }
}


fun View.setLeftMargin(margin: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(margin, params.topMargin, params.rightMargin, params.bottomMargin)
    layoutParams = params
}

fun TextView.setAppearance(@StyleRes resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.setTextAppearance(resId)
    } else {
        this.setTextAppearance(context, resId)
    }
}