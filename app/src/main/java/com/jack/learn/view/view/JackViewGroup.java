package com.jack.learn.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class JackViewGroup extends ViewGroup {

    private int mWidth;
    private int mHeight;
    public JackViewGroup(Context context) {
        this(context,null);
    }

    public JackViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public JackViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 没有子view的情况
        int childCount = getChildCount();
        if (childCount == 0) {
            mWidth = measureWidthAndHeight(widthMeasureSpec);
            mHeight = measureWidthAndHeight(heightMeasureSpec);
        } else {
            int childViewWidth = 0;
            int childViewHeight = 0;
            int childViewMarginTop = 0;
            int childViewMarginBottom = 0;
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                measureChild(childView,widthMeasureSpec,heightMeasureSpec);
                MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
                childViewWidth = Math.max(childViewWidth,childView.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin);
                childViewHeight+=childView.getMeasuredHeight();
                childViewMarginTop+=layoutParams.topMargin;
                childViewMarginBottom+=layoutParams.bottomMargin;
            }
            mWidth = childViewWidth+getPaddingLeft()+getPaddingRight();
            mHeight = childViewHeight+childViewMarginTop+childViewMarginBottom+getPaddingTop()+getPaddingBottom();
        }
        setMeasuredDimension(measureWidthAndHeight(widthMeasureSpec,mWidth),measureWidthAndHeight(heightMeasureSpec,mHeight));
    }
    private int measureWidthAndHeight(int measureSpec,int size) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = size;
        }
        return result;
    }

    private int measureWidthAndHeight(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left,top,right,bottom;
        int childCount = getChildCount();
        int countTop = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();
            left = getPaddingLeft()+layoutParams.leftMargin;
            right = layoutParams.leftMargin+childView.getMeasuredWidth();
            top = getPaddingTop()+countTop + layoutParams.topMargin;
            bottom = top+childView.getMeasuredHeight();
            childView.layout(left,top,right,bottom);
            countTop += (bottom-top)+layoutParams.topMargin+layoutParams.bottomMargin;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
