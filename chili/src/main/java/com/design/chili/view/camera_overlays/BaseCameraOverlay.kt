package com.design.chili.view.camera_overlays

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.design.chili.R
import com.design.chili.extensions.dp

abstract class BaseCameraOverlay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var auxCanvas: Canvas
    private lateinit var auxBitmap: Bitmap

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return
        auxBitmap = Bitmap.createBitmap(canvas.width, canvas.height, Bitmap.Config.ARGB_8888)
        auxCanvas = Canvas(auxBitmap)
        drawShapes(auxCanvas)
        canvas.drawBitmap(auxBitmap, 0f, 0f, Paint())
    }

    abstract fun drawShapes(canvas: Canvas)


    protected fun cutRectangleFromCanvas(canvas: Canvas, startX: Float, startY: Float, bottomX: Float, bottomY: Float): Float {
        val rect = RectF(startX, startY, bottomX, bottomY)
        val radius = 12.dp.toFloat()
        canvas.drawRoundRect(rect, radius, radius, getEraser())
        return rect.bottom
    }


    protected fun drawText(canvas: Canvas, text: String, textX: Float, textY: Float, textConfig: TextConfig): Float {
        val textColor = ContextCompat.getColor(context, R.color.white_1)
        val textSizePx = textConfig.textSizePx
        val lineSpacingPx = 8.dp

        val textPaint = Paint().apply {
            color = textColor
            textSize = textSizePx
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            letterSpacing = -0.38588235f / textSizePx
            typeface = Typeface.create("Roboto", textConfig.textStyle)
        }

        val lines = text.split("\n")
        var textYLines = textY + textSizePx
        for (line in lines) {
            canvas.drawText(line, textX, textYLines, textPaint)
            textYLines += textSizePx + lineSpacingPx
        }
        return textYLines - (textSizePx + lineSpacingPx)
    }


    protected fun drawColor(canvas: Canvas, @ColorRes colorId: Int, alpha: Int = 100) {
        val color = ColorUtils.setAlphaComponent(ContextCompat.getColor(context, colorId), alpha)
        canvas.drawColor(color)
    }

    private fun getEraser(): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0x77000000
            style = Paint.Style.FILL
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }
    }
}


data class TextConfig(
    val textSizePx: Float,
    val textStyle: Int = Typeface.NORMAL
)