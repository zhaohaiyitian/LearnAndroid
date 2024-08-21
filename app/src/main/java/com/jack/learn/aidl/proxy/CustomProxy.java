package com.jack.learn.aidl.proxy;

import android.os.IBinder;
import android.os.Parcel;

import com.jack.learn.aidl.Person;
import com.jack.learn.aidl.server.BinderStub;
import com.jack.learn.aidl.server.IPersonManager;

import java.util.List;

/**
 * Binder机制的发送端
 * 代理对象实例就是client最终拿到的代理服务，通过代理和server进行通信
 */
public class CustomProxy implements IPersonManager {

    private IBinder mRemote;
    public CustomProxy(IBinder remote) {
        mRemote = remote;
    }


    @Override
    public void addPerson(Person person) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();

        try {
            data.writeInterfaceToken(BinderStub.DESCRIPTOR);
            if (person != null) {
                data.writeInt(1);
                person.writeToParcel(data,0);
            } else {
                data.writeInt(0);
            }
            // flag 0 同步传输  IBinder.FLAG_ONEWAY 异步传输
            mRemote.transact(BinderStub.ADD_PERSON,data,reply,0);// 如果是同步传输 这里会挂起
            //如果是同步传输 在发送数据后 Client进程的该线程会暂时被挂起
            reply.readException();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    @Override
    public List<Person> getPersonList() {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        List<Person> result = null;
        try {
            data.writeInterfaceToken(BinderStub.DESCRIPTOR);
            mRemote.transact(BinderStub.GET_PERSON,data,reply,0);
            // 在发送数据后 Client进程的该线程会暂时被挂起
            reply.readException();
            result = reply.createTypedArrayList(Person.CREATOR);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
        return result;
    }

    // Binder驱动根据代理对象 找到对应的真身Binder对象所在的server进程（系统自动执行）
    // Binder驱动把数据发送server进程中，并通知server进程进行解包（系统自动执行）

    @Override
    public IBinder asBinder() {
        return null;
    }
}
