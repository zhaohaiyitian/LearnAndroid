package com.jack.learn.architecture.mvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.jack.learn.R;

/**
 * MVP核心思想：把原来(MVC)的UI逻辑抽象成View接口，把原来(MVC)的业务逻辑抽象成Presenter接口，model还是原来(MVC)的model
 */
public abstract class BaseMVPActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        // 创建presenter
        mPresenter = createPresenter();
        // presenter与view绑定
        if (null != mPresenter) {
            mPresenter.attachView((V)this);
        }

    }

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView();;
        }
    }
}
