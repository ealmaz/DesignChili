package com.design.chili.view.tooltip

import android.graphics.RectF
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout

object TooltipUtils {

    fun findFrameLayout(anchorView: View?): ViewGroup? {
        var rootView = anchorView?.rootView as? ViewGroup
        if (rootView?.childCount == 1 && rootView.getChildAt(0) is FrameLayout) {
            rootView = rootView.getChildAt(0) as ViewGroup
        }
        return rootView
    }

    fun removeOnGlobalLayoutListener(view: View?, listener: OnGlobalLayoutListener?) {
        view?.viewTreeObserver?.removeOnGlobalLayoutListener(listener)
    }

    fun calculateRectInWindow(view: View): RectF {
        val location = IntArray(2)
        view.getLocationInWindow(location)
        return RectF(
            location[0].toFloat(),
            location[1].toFloat(),
            (location[0] + view.measuredWidth).toFloat(),
            (location[1] + view.measuredHeight).toFloat()
        )
    }
}