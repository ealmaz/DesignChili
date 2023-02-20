package com.design.chili.view.shimmer

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.design.chili.extensions.getColorFromAttr
import com.eudycontreras.boneslibrary.extensions.disableSkeletonLoading
import com.eudycontreras.boneslibrary.extensions.dp
import com.eudycontreras.boneslibrary.framework.skeletons.SkeletonDrawable
import com.eudycontreras.boneslibrary.properties.CornerRadii
import com.eudycontreras.boneslibrary.properties.MutableColor

fun Shimmering.showShimmer() {
    (this as? ViewGroup)?.createShimmerDrawable(
        ignoredBones = getIgnoredViews(),
        customBones = getCustomBones()
    )
}

fun Shimmering.hideShimmer() {
    (this as? ViewGroup)?.disableSkeletonLoading()
}

private fun ViewGroup.createShimmerDrawable(
    settings: ShimmerSettings = ShimmerSettings(),
    ignoredBones: Array<View> = emptyArray(),
    customBones: Array<CustomBone> = emptyArray(),
) {
    SkeletonDrawable.create(this, true)
        .builder()
        .setStateTransitionDuration(settings.transitionDuration)
        .withBoneBuilder {
            setMinThickness(8.dp)
            setMaxThickness(8.dp)
            setMaxDistance(8.dp)
            setShaderMultiplier()
            setDissectBones(true)
            setColor(MutableColor.fromColor(context.getColorFromAttr(settings.bonesColor)))
            setCornerRadii(CornerRadii(settings.bonesRadius.dp))
        }
        .apply {
            withIgnoredBones(*ignoredBones)
            customBones.forEach { withBoneBuilder(it.view, it.builder) }
        }
        .withShimmerBuilder {
            setColor(MutableColor.fromColor(context.getColorFromAttr(settings.raysColor)))
            setCount(1)
            setAnimationDuration(1000)
        }
}

inline fun <reified T> ViewGroup.getViewsByType(): Array<View> {
    val views = getAllViewsFromTree()
    return views
        .filter { it is T }
        .toTypedArray()
}


fun ViewGroup.getAllViewsFromTree(): List<View> {
    val views = mutableListOf<View>()
    children.forEach {
        when (it) {
            is ViewGroup -> views.addAll(it.getAllViewsFromTree())
            else -> views.add(it)
        }
    }
    return views
}