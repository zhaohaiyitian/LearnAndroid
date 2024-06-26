package com.jack.learn.view

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import com.jack.learn.R

val Float.dp
 get() = TypedValue.applyDimension(
   TypedValue.COMPLEX_UNIT_DIP,
   this,
   Resources.getSystem().displayMetrics
 )

val Int.dp
 get() = this.toFloat().dp

fun getAvatar(res: Resources, width: Int): Bitmap {
  val options = BitmapFactory.Options()
  options.inJustDecodeBounds = true
  BitmapFactory.decodeResource(res, R.mipmap.image, options)
  options.inJustDecodeBounds = false
  options.inDensity = options.outWidth
  options.inTargetDensity = width
  return BitmapFactory.decodeResource(res, R.mipmap.image, options)
}