package com.jack.learn.apm.view;

import android.view.View;
import android.view.ViewGroup;

public class AsyncInflateItem {

    String inflateKey;
    int layoutResId;
    ViewGroup parent;
    OnInflateFinishedCallback callback;
    View inflatedView;

    private boolean cancelled;
    private boolean inflating;
}
