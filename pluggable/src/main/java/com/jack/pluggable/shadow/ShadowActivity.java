package com.jack.pluggable.shadow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jack.pluggable.R;

/**
 * 需要上下文和生命周期
 */
public class ShadowActivity extends ShadowContext {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, savedInstanceState.get("number").toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
