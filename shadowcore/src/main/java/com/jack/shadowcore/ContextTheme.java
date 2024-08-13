package com.jack.shadowcore;

import android.app.Activity;
import android.os.Bundle;

public interface ContextTheme {

    // 传递上下文
    public void attach(Activity attachActivity);
    // 传递生命周期

    public void onCreate(Bundle savedInstanceState);

    public void onStart();
    public void onResume();

    public void onPause();

    public void onStop();

}
