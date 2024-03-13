package com.jack.learn.aidl.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jack.learn.aidl.Person;
import com.jack.learn.aidl.proxy.CustomProxy;

import java.util.List;

/**
 * BinderStub：就是你自己实现的service
 */
public abstract class BinderStub extends Binder implements IPersonManager {

    // binder唯一标识 自定义
    public static final String DESCRIPTOR = "com.jack.learn.aidl.server.IPersonManager";
    // 方法标识
    public static final int GET_PERSON = IBinder.FIRST_CALL_TRANSACTION;
    public static final int ADD_PERSON = IBinder.FIRST_CALL_TRANSACTION + 1;
    public BinderStub() {
        this.attachInterface(this,DESCRIPTOR);
    }

    /**
     *
     * @param iBinder Binder驱动传递过来的IBinder对象
     * @return
     */
    public static IPersonManager asInterface(IBinder iBinder) {
        if (iBinder==null) {
            return null;
        }
        IInterface iin = iBinder.queryLocalInterface(DESCRIPTOR);
        if (iin instanceof IPersonManager) {
            // client和server处于同一个进程 直接返回
            return (IPersonManager) iin;
        }
        return new CustomProxy(iBinder); // 返回代理对象
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    /**
     * 完成数据的交互
     * @param code The action to perform.  This should
     * be a number between {@link #FIRST_CALL_TRANSACTION} and
     * {@link #LAST_CALL_TRANSACTION}.
     * @param data Marshalled data being received from the caller.
     * @param reply If the caller is expecting a result back, it should be marshalled
     * in to here.
     * @param flags Additional operation flags.  Either 0 for a normal
     * RPC, or {@link #FLAG_ONEWAY} for a one-way RPC.
     *
     * @return
     * @throws RemoteException
     */
    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        // 1.收到Binder驱动通知后，Server进程通过回调Binder对象 onTransact() 进行数据解包，调用目标方法
        // code即在transact中约定的目标方法的标识符
        switch (code) {
            case INTERFACE_TRANSACTION:
                return true;
            case GET_PERSON:
                // 解析Parcel中的数据
                data.enforceInterface(DESCRIPTOR);
                List<Person> personList = this.getPersonList();
                if (reply != null) {
                    reply.writeNoException();
                    reply.writeTypedList(personList);
                }
                return true;
            case ADD_PERSON:
                // 解析Parcel中的数据
                data.enforceInterface(DESCRIPTOR);
                Person person = null;
                // 解析目标方法对象的标识符
                if (data.readInt() != 0) {
                    person = Person.CREATOR.createFromParcel(data);
                }
                this.addPerson(person);
                // 将结果写到reply
                if (reply != null) {
                    reply.writeNoException();
                }
                return true;
        }
        // 将结算结果返回到Binder驱动
        return super.onTransact(code, data, reply, flags);
    }
}
