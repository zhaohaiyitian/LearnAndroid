package com.jack.learn.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class WrapViewPager extends ViewPager {
    public WrapViewPager(@NonNull Context context) {
        super(context);
    }

    public WrapViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            ViewGroup.LayoutParams layoutParams = childView.getLayoutParams();
            childView.measure(widthMeasureSpec,getChildMeasureSpec(heightMeasureSpec,0,layoutParams.height));
            int measuredHeight = childView.getMeasuredHeight();
            if (measuredHeight > height) {
                height = measuredHeight;
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
