package com.jack.learn.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
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

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (iPersonManager == null) {
                return;
            }
            iPersonManager.asBinder().unlinkToDeath(deathRecipient,0);
            iPersonManager = null;
            //TODO 在这里重新绑定远程service
        }
    };

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
            // server端提供的代理接口 iBinder
            iPersonManager = BinderStub.asInterface(service);
            try {
                service.linkToDeath(deathRecipient,0);// 给Binder设置死亡代理
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
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
