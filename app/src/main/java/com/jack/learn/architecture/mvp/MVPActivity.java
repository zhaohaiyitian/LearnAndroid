package com.jack.learn.architecture.mvp;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.jack.learn.R;

/**
 * MVP核心思想：把原来(MVC)的UI逻辑抽象成View接口，把原来(MVC)的业务逻辑抽象成Presenter接口，model还是原来(MVC)的model
 */
public class MVPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
    }
}
