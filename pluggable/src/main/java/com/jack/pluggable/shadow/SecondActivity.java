package com.jack.pluggable.shadow;

import android.os.Bundle;

import com.jack.pluggable.R;

public class SecondActivity extends ShadowContext {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
