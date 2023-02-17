package com.design.chili.view.shimmer

import android.view.View

interface Shimmering {

    fun getIgnoredViews(): Array<View>

    fun getCustomBones(): Array<CustomBone>

}