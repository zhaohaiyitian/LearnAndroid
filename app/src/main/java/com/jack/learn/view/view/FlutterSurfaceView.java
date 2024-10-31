package com.jack.learn.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

/**
 * SurfaceView背后有一个单独的Surface，这个Surface不在View层级中，因此它可以独立于UI线程进行绘制
 * SurfaceView 支持双缓冲机制，可以在后台线程中准备好一帧后，再切换到前台显示，从而减少闪烁。
 */
public class FlutterSurfaceView extends SurfaceView implements SurfaceHolder.Callback ,Runnable {

    private boolean isRunning; // 子线程标识位
    private Paint mPaint; // 画笔
    public FlutterSurfaceView(Context context) {
        super(context);
    }

    public FlutterSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public FlutterSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        getHolder().addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    /**
     * 绘制界面相关的初始化工作
     *  SurfaceView构建好之后会调用surfaceCreated这个方法
     * @param holder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
      isRunning = true;
      new Thread(this).start();


    }

    /**
     * 在 surfaceCreated() 调用后该函数至少会被调用一次。
     * @param holder The SurfaceHolder whose surface has changed.
     * @param format The new {@link PixelFormat} of the surface.
     * @param width The new width of the surface.
     * @param height The new height of the surface.
     */
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * 清理使用的资源
     * @param holder The SurfaceHolder whose surface is being destroyed.
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while(isRunning) {
            Canvas canvas = null;
            try {
                // 获取一个Canvas对象，并锁定之。所得到的Canvas对象，其实就是Surface中一个成员
                canvas = getHolder().lockCanvas(); // 就是为了在绘制的过程中，Surface中的数据不会被改变。
                if (canvas != null) {
                    doSomeThing();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    // 当修改Surface中的数据完成后，释放同步锁，并提交改变，然后将新的数据进行展示，同时Surface中相关数据会被丢失。
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }

        }
    }

    private void doSomeThing() {

    }
}







