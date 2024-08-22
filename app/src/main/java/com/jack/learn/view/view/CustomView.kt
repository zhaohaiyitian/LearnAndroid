package com.jack.learn.view.view

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * getMeasuredWidth和getWidth有什么区别？什么时候会有不同？如何让getWidth的值跟getMeasuredWidth不同？
 * getMeasuredWidth方法获得的值是setMeasuredDimension方法设置的值，它的值在measure方法运行后就会确定
 * getWidth方法获得是layout方法中传递的四个参数中的mRight-mLeft，它的值是在layout方法运行后确定的
 * 一般情况下在onLayout方法中使用getMeasuredWidth方法，而在除onLayout方法之外的地方用getWidth方法
 *
 *
 *
 * 通过view#post获取view宽高，可以获取到么?原理
 * post 往消息队列中插入消息
 *
 *
 * 经过dispatchAttachedToWindow(AttachInfo info, int visibility)，ViewRootImpl中关联的所有View共享了AttachInfo。
 *
 *
 * 只要在ActivityThread#handleResumeActivity()之前的流程中（如onCreate()）新起一个子线程更新UI，也是会生效的，不过一般不建议这么操作
 *
 */
class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    // 如何让getWidth的值跟getMeasuredWidth不同?
    // 通过重写layout 修改r，b的值
    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l, t, r+100, b+100)
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        measuredWidth // mMeasuredWidth & MEASURED_SIZE_MASK;
        width //  mRight - mLeft;
    }
}