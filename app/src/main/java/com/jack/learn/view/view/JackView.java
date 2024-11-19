package com.jack.learn.view.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.View;

import androidx.annotation.Nullable;

import com.jack.learn.R;

public class JackView extends View {

    private Rect mTextBounds;
    private String text;
    private Paint mPaint;
    private int width = 0;
    private int height = 0;
    public JackView(Context context) {
        this(context,null);
    }

    public JackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public JackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // obtainStyledAttributes 从对象池中获取到TypedArray对象
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.JackView);
        text = ta.getString(R.styleable.JackView_str);
        ta.recycle(); // 回收TypedArray所占用的资源
        mPaint = new Paint();
        mPaint.setTextSize(50);
        mTextBounds = new Rect();
        mPaint.getTextBounds(text,0,text.length(),mTextBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = mTextBounds.width()+getPaddingStart()+getPaddingEnd();
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = mTextBounds.height()+getPaddingTop()+getPaddingBottom();
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text,getPaddingStart()+0,mTextBounds.height()+getPaddingEnd(),mPaint);
        SurfaceControl surfaceControl;
        Surface surface;
        invalidate();
        canvas.save();
        canvas.restore();







        //int save = canvas.save(); // 用于保存Canvas的当前状态,每次调用save()时，当前的绘制状态就会被压入一个状态堆栈中
        // 进行一些临时的绘制和变换操作
        //canvas.restore(); // 它将从状态堆栈中弹出最上面的状态，并应用于Canvas
        // 继续进行下面的绘制操作
    }
}
