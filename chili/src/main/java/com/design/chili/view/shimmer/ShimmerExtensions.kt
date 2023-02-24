package com.design.chili.view.shimmer

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.design.chili.extensions.getColorFromAttr
import com.design.chili.extensions.gone
import com.design.chili.extensions.invisible
import com.design.chili.extensions.visible
import com.eudycontreras.boneslibrary.extensions.disableSkeletonLoading
import com.eudycontreras.boneslibrary.extensions.dp
import com.eudycontreras.boneslibrary.framework.skeletons.SkeletonDrawable
import com.eudycontreras.boneslibrary.properties.CornerRadii
import com.eudycontreras.boneslibrary.properties.MutableColor

fun Shimmering.showShimmer() {
    val parentShimmer = this
    (this as? ViewGroup)
        ?.getAllChildViewGroups()
        ?.forEach {
            val shimmering = (it as? Shimmering) ?: parentShimmer
            it.createShimmerDrawable(
                ignoredBones = shimmering.getIgnoredViews(),
                customBones = shimmering.getCustomBones()
            )
        }
}

fun Shimmering.hideShimmer() {
    (this as? ViewGroup)
        ?.getAllChildViewGroups()
        ?.forEach { it.disableSkeletonLoading() }
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
    val views = getAllChildViews()
    return views
        .filter { it is T }
        .toTypedArray()
}


fun ViewGroup.getAllChildViews(): List<View> {
    val views = mutableListOf<View>()
    children.forEach {
        when (it) {
            is ViewGroup -> views.addAll(it.getAllChildViews())
            else -> views.add(it)
        }
    }
    return views
}

fun ViewGroup.getAllChildViewGroups(): List<ViewGroup> {
    return children
        .filter { it is ViewGroup }
        .map { it as ViewGroup }
        .toList()
}

fun FacebookShimmering.startShimmering() {
    onStartShimmer()
    getShimmerLayouts().forEach { it.startShimmer() }
    getShimmeribleViewsPair().forEach {
        it.key.invisible()
        it.value?.visible()
    }
}

fun FacebookShimmering.stopShimmering() {
    onStopShimmer()
    getShimmerLayouts().forEach { it.stopShimmer() }
    getShimmeribleViewsPair().forEach {
        it.key.visible()
        it.value?.gone()
    }
}