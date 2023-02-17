package com.design.chili.view.shimmer

import androidx.annotation.AttrRes
import com.design.chili.R

data class ShimmerSettings(
    val transitionDuration: Long = 20L,
    @AttrRes val bonesColor: Int = R.attr.ChiliShimmerBoneColor,
    val bonesRadius: Int = 8,
    @AttrRes val raysColor: Int = R.attr.ChiliShimmerRayColor
)