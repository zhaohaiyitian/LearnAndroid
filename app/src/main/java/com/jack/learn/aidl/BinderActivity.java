package com.jack.learn.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jack.learn.R;
import com.jack.learn.aidl.server.BinderStub;
import com.jack.learn.aidl.server.IPersonManager;
import com.jack.learn.aidl.service.ServerService;

import java.util.List;

public class BinderActivity extends AppCompatActivity {

    IPersonManager iPersonManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);
        View viewById = findViewById(R.id.button);
        bindService();
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPersonManager != null) {
                    iPersonManager.addPerson(new Person("as",12));
                    List<Person> personList = iPersonManager.getPersonList();
                    for (Person person : personList) {
                        Log.d("wangjie",person.name);
                    }
                }
            }
        });
    }

    private void bindService() {
        Intent intent = new Intent(this, ServerService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }




    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // server端提供的代理接口
            iPersonManager = BinderStub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
