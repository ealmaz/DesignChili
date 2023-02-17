package com.design.chili.view.shimmer

import android.view.View
import com.eudycontreras.boneslibrary.framework.bones.BoneBuilder

data class CustomBone(val view: View, val builder: BoneBuilder.() -> Unit)