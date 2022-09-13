package com.design.chili.view.common

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Html
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.animation.addListener
import androidx.core.text.parseAsHtml
import com.design.chili.R

class AnimatedArcProgressView(context: Context, private val attrs: AttributeSet) : View(context, attrs) {

    private val paint: Paint = Paint()

    private var arcStartAngle = 125f
    private var arcSweepAngle = 290f

    private var arcMarginPx: Int = 0

    private var arcWidthPx: Int? = null

    private var arcProgressAngle: Float = 0f

    private var arcBaseStrokeWidthPx: Int = 0
    private var arcProgressStrokeWidthPx: Int = 0

    @ColorInt private var arcBaseStrokeColor: Int = Color.GRAY
    @ColorInt private var arcProgressStrokeColor: Int = Color.GREEN

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        initView()
    }

    private fun initView() {
        context?.obtainStyledAttributes(attrs, R.styleable.AnimatedArcProgressView)?.run {
            getDimensionPixelSize(R.styleable.AnimatedArcProgressView_arcWidth, -1).takeIf { it != -1 }?.let {
                arcWidthPx = it
            }
            getInteger(R.styleable.AnimatedArcProgressView_arcStartAngle, 125).let {
                arcStartAngle = it.toFloat()
            }
            getInteger(R.styleable.AnimatedArcProgressView_arcSweepAngle, 290).let {
                arcSweepAngle = it.toFloat()
            }
            getDimensionPixelSize(R.styleable.AnimatedArcProgressView_arcMargin, -1).takeIf { it != -1 }.let {
                arcMarginPx = it ?: resources.getDimensionPixelSize(R.dimen.padding_4dp)
            }
            getColor(R.styleable.AnimatedArcProgressView_arcBaseStrokeColor, Color.GRAY).let {
                arcBaseStrokeColor = it
            }
            getColor(R.styleable.AnimatedArcProgressView_arcProgressStrokeColor, Color.GREEN).let {
                arcProgressStrokeColor = it
            }
            getDimensionPixelSize(R.styleable.AnimatedArcProgressView_arcBaseStrokeWidth, -1).takeIf { it != -1 }.let {
                arcBaseStrokeWidthPx = it ?: resources.getDimensionPixelSize(R.dimen.padding_4dp)
            }
            getDimensionPixelSize(R.styleable.AnimatedArcProgressView_arcProgressStrokeWidth, -1).takeIf { it != -1 }.let {
                arcProgressStrokeWidthPx = it ?: resources.getDimensionPixelSize(R.dimen.padding_6dp)
            }
            recycle()
        }
    }

    fun setProgressStrokeColor(@ColorInt colorId: Int){
        arcProgressStrokeColor = colorId
        invalidate()
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, r, b)
        if (arcWidthPx == null)
            arcWidthPx = (height.coerceAtMost(width) - arcBaseStrokeWidthPx - arcMarginPx - arcMarginPx)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        drawGreyArc(canvas)
        drawArc(canvas)
    }

    private fun drawGreyArc(canvas: Canvas?) {
        if (canvas == null) return
        paint.strokeWidth = arcBaseStrokeWidthPx.toFloat()
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = arcBaseStrokeColor
        val right = (width / 2) + ((arcWidthPx ?: 0) / 2)
        val bottom = (arcWidthPx ?: 0) + arcProgressStrokeWidthPx.toFloat()
        val left = (width / 2) - ((arcWidthPx ?: 0) / 2)
        canvas.drawArc(left.toFloat(), arcProgressStrokeWidthPx.toFloat(), right.toFloat(), bottom.toFloat(), arcStartAngle, arcSweepAngle, false, paint)
    }

    private fun drawArc(canvas: Canvas?) {
        if (canvas == null) return
        paint.strokeWidth = arcProgressStrokeWidthPx.toFloat()
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = arcProgressStrokeColor
        val right = (width / 2) + ((arcWidthPx ?: 0) / 2)
        val bottom = (arcWidthPx ?: 0) + arcProgressStrokeWidthPx.toFloat()
        val left = (width / 2) - ((arcWidthPx ?: 0) / 2)
        canvas.drawArc(left.toFloat(), arcProgressStrokeWidthPx.toFloat(), right.toFloat(), bottom.toFloat(), arcStartAngle, arcProgressAngle, false, paint)
    }

    private fun animateRemainAmount(progressAngle: Float) {
        ValueAnimator.ofFloat(0f, progressAngle).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            addUpdateListener {
                drawProgress(it.animatedValue as Float)
            }
            start()
        }
    }

    private fun reversAnimation(onEnd: () -> Unit) {
        ValueAnimator.ofFloat(arcProgressAngle, 0F).apply {
            duration = 300
            interpolator = LinearInterpolator()
            addUpdateListener {
                drawProgress(it.animatedValue as Float)
                addListener(onEnd = { onEnd() })
            }
            start()
        }
    }

    private fun drawProgress(angle: Float) {
        arcProgressAngle = angle
        invalidate()
    }


    private fun calculateProgress(limit: Long, remain: Long, isUnlimited: Boolean?= false): Float {
        return when {
            isUnlimited == true -> 290f
            remain <= 1L -> 0f
            limit == 0L || remain > limit -> arcSweepAngle
            else -> arcSweepAngle * remain / limit
        }
    }

    fun setArcProgress(limit: Long = 0, remain: Long = 0, isUnlimited: Boolean? = false) {
        val angle = calculateProgress(limit, remain, isUnlimited)
        when (arcProgressAngle > 0) {
            true -> reversAnimation { animateRemainAmount(angle) }
            else -> animateRemainAmount(angle)
        }
    }
}