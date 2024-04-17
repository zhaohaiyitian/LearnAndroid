package com.jack.learn.view.sticky

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView

class CustomNestedScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : NestedScrollView(context, attrs) {


    /**
     * consume 标识父布局消费的垂直或水平距离
     * type 触发滑动事件的类型
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
//        super.onNestedPreScroll(target, dx, dy, consumed, type)
        val headViewHeight = (getChildAt(0) as LinearLayout).getChildAt(0).measuredHeight
        // 向上滑动，若当前headView可见，需要将headView滑动至不可见
        // 就是先父容器滑动 当headView不可见时再滑动子view
        val isNeedHideTop = dy >0 && scrollY < headViewHeight
        if (isNeedHideTop) {
            scrollBy(0,dy)
            consumed[1] = dy
        }
    }
}