package com.jack.learn.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class JackView extends View {
    public JackView(Context context) {
        super(context);
    }

    public JackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        int save = canvas.save(); // 用于保存Canvas的当前状态,每次调用save()时，当前的绘制状态就会被压入一个状态堆栈中
        canvas.restore(); // 它将从状态堆栈中弹出最上面的状态，并应用于Canvas

    }
}
