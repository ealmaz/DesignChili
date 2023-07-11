package com.design.chili.view.camera_overlays

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.util.AttributeSet
import com.design.chili.R
import com.design.chili.extensions.dp

class PassportCardOverlay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.passportCardCameraOverlay,
    defStyleRes: Int = R.style.Chili_CameraOverlayPassportCard
) : BaseCameraOverlay(context, attrs, defStyleAttr, defStyleRes) {

    private var headerText: String? = null
    private var title: String? = null
    private var description: String? = null
    private var overlayAlpha = 100

    init {
        obtainAttributes(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.PassportCardOverlay, defStyleAttr, defStyleRes).run {
            headerText = getString(R.styleable.PassportCardOverlay_headerText)
            title = getString(R.styleable.PassportCardOverlay_title)
            description = getString(R.styleable.PassportCardOverlay_description)
            overlayAlpha = getInteger(R.styleable.PassportCardOverlay_overlayAlpha, 77)
            recycle()
        }
    }

    fun setTitle(title: String) {
        this.title = title
        invalidate()
    }

    fun setHeaderText(text: String) {
        headerText = text
        invalidate()
    }

    fun setDescription(text: String) {
        description = text
        invalidate()
    }

    fun setTitle(titleRes: Int) {
        this.title = resources.getString(titleRes)
        invalidate()
    }

    fun setHeaderText(textRes: Int) {
        headerText = resources.getString(textRes)
        invalidate()
    }

    fun setDescription(textRes: Int) {
        description = resources.getString(textRes)
        invalidate()
    }


    override fun drawShapes(canvas: Canvas) {
        drawColor(canvas, R.color.black_1, overlayAlpha)
        var y = drawHeaderText(canvas, 0f)
        y = cutPassportCardShape(canvas, y)
        y = drawTitle(canvas, y)
        drawDescription(canvas, y)

    }

    private fun drawHeaderText(canvas: Canvas, startY: Float): Float {
        if (headerText == null) return startY
        val startYWithMargin = startY + 56.dp.toFloat()
        return drawText(
            canvas,
            headerText ?: "",
            (width / 2).toFloat(),
            startYWithMargin,
            TextConfig(16.dp.toFloat(), Typeface.BOLD)
        )
    }

    private fun cutPassportCardShape(canvas: Canvas, startY: Float): Float {
        val startYWithMargin = startY + 24.dp.toFloat()
        val marginPx = 16.dp

        val rectWidth = canvas.width - (2 * marginPx)
        val rectHeight = rectWidth * (6.0f / 9.0f)

        val left = marginPx.toFloat()
        var top = startYWithMargin
        val right = left + rectWidth
        val bottom = top + rectHeight

        return cutRectangleFromCanvas(
            canvas,
            left,
            top,
            right,
            bottom
        )
    }

    private fun drawTitle(canvas: Canvas, startY: Float): Float {
        if (title == null) return startY
        val startYWithMargin = startY + 24.dp.toFloat()
        return drawText(
            canvas,
            title ?: "",
            (width / 2).toFloat(),
            startYWithMargin,
            TextConfig(16.dp.toFloat(), Typeface.BOLD)
        )
    }

    private fun drawDescription(canvas: Canvas, startY: Float): Float {
        if (description == null) return startY
        val startYWithMargin = startY + 8.dp.toFloat()
        return drawText(
            canvas,
            description ?: "",
            (width / 2).toFloat(),
            startYWithMargin,
            TextConfig(16.dp.toFloat())
        )
    }
}