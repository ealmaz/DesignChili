package com.design.chili.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.design.chili.util.GlideBitmapScaleTransformation
import java.io.File

fun ImageView.loadImageFormFilePath(filePath: String) {
    if (tag == filePath) return
    Glide.with(context)
        .load(File(filePath))
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .transform(GlideBitmapScaleTransformation())
        .override(Target.SIZE_ORIGINAL)
        .into(this)
}