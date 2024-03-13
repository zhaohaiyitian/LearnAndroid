package com.jack.learn.aidl.server;

import android.os.IBinder;
import android.os.IInterface;

import com.jack.learn.aidl.Person;

import java.util.List;

/**
 * IInterface framework层的核心类
 */
public interface IPersonManager extends IInterface {

    void addPerson(Person person);

    List<Person> getPersonList();

    @Override
    IBinder asBinder();
}
