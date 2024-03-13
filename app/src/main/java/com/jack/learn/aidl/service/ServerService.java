package com.jack.learn.aidl.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.jack.learn.aidl.Person;
import com.jack.learn.aidl.server.BinderStub;

import java.util.ArrayList;
import java.util.List;

public class ServerService extends Service {

    private List<Person> mPersonList = new ArrayList<>();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private Binder binder = new BinderStub() {
        @Override
        public void addPerson(Person person) {
            if (person == null) {
                person = new Person();
            }
            mPersonList.add(person);
        }

        @Override
        public List<Person> getPersonList() {
            return mPersonList;
        }
    };
}
