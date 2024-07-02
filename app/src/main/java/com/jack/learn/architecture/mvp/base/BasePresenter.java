package com.jack.learn.architecture.mvp.base;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<T> {

    protected WeakReference<T> viewRef; // BasePresenter中持有一个view的弱引用。


    protected void attachView(T view) {
        this.viewRef = new WeakReference<>(view);
    }

    protected T getView() {
        return viewRef.get();
    }

    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    protected void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }
}
