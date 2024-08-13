package com.jack.pluggable.shadow;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jack.shadowcore.ContextTheme;

public class ShadowContext implements ContextTheme {
    public Activity activity;



    public <T extends View> T findViewById(int id) {
        return activity.findViewById(id);
    }

    public void startActivity(Intent intent) {
        Intent mIntent = new Intent();
        mIntent.putExtra("classname",intent.getComponent().getClassName());
        activity.startActivity(mIntent);
    }
    @Override
    public void attach(Activity attachActivity) {
        activity = attachActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    public void setContentView(int layoutResId) {
        activity.setContentView(layoutResId);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }
}
