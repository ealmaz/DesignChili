package com.design.chili.util

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.charset.Charset
import java.security.MessageDigest
import kotlin.math.roundToInt

class GlideBitmapScaleTransformation : BitmapTransformation() {
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val width = toTransform.width
        val height = toTransform.height
        if (height > BITMAP_MAX_SIZE || width > BITMAP_MAX_SIZE) {
            val scaleFactor = if (height > width) BITMAP_MAX_SIZE / height
            else BITMAP_MAX_SIZE / width
            return scaleImage(toTransform, scaleFactor)
        }
        return toTransform
    }

    private fun scaleImage(image: Bitmap, scaleFactor: Float): Bitmap {
        val sizeX = (image.width * scaleFactor).roundToInt()
        val sizeY = (image.height * scaleFactor).roundToInt()
        return Bitmap.createScaledBitmap(image, sizeX, sizeY, false)
    }

    override fun equals(other: Any?): Boolean {
        return other is GlideBitmapScaleTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    companion object {

        private const val ID = "com.design.chili.util.GlideBitmapScaleTransformation"
        private val ID_BYTES = ID.toByteArray(Charset.forName("UTF-8"))

        private const val BITMAP_MAX_SIZE = 3095f
    }
}