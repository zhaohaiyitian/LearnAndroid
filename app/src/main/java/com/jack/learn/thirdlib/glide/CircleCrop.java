package com.jack.learn.thirdlib.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 只能对静态图片进行变换
 */
public class CircleCrop extends BitmapTransformation {
    /**
     * pool Bitmap缓存池，用于对Bitmap对象进行重用
     * toTransform 原始图片的bitmap对象
     * outWidth,outHeight分别代表图片变换后的宽度和高度，其实也就是override()方法中传入的宽和高的值
     */
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int diameter = Math.min(toTransform.getWidth(),toTransform.getHeight());
        Bitmap toReuse = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        Bitmap result;
        if (toReuse != null) {
            result = toReuse;
        } else {
            result = Bitmap.createBitmap(diameter,diameter,Bitmap.Config.ARGB_8888);
        }
        int dx = (toTransform.getWidth() - diameter) / 2;
        int dy = (toTransform.getHeight() - diameter) / 2;
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        BitmapShader bitmapShader = new BitmapShader(toTransform, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        if (dx !=0 || dy != 0) {
            Matrix matrix = new Matrix();
            matrix.setTranslate(-dx,-dy);
            bitmapShader.setLocalMatrix(matrix);
        }
        paint.setShader(bitmapShader);
        paint.setAntiAlias(true);
        float radius = diameter / 2f;
        canvas.drawCircle(radius,radius,radius,paint);
        if (toReuse != null) {
            pool.put(toReuse);
            toReuse.recycle();
        }
        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
