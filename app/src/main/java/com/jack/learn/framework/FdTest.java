package com.jack.learn.framework;

import android.os.Looper;
import android.os.MessageQueue;
import android.os.ParcelFileDescriptor;

import androidx.annotation.NonNull;

import java.io.FileDescriptor;
import java.io.IOException;

public class FdTest implements MessageQueue.OnFileDescriptorEventListener {



    public void createPipe()  {
        try {
            ParcelFileDescriptor[] mFds = ParcelFileDescriptor.createPipe();
            ParcelFileDescriptor mFd0 = mFds[0];// 读描述符
            ParcelFileDescriptor mFd1 = mFds[1];// 写描述符


            MessageQueue messageQueue = Looper.myQueue();
            messageQueue.addOnFileDescriptorEventListener(new FileDescriptor(), MessageQueue.OnFileDescriptorEventListener.EVENT_INPUT,this);



            //TODO 往Looper中添加fd
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int onFileDescriptorEvents(@NonNull FileDescriptor fd, int events) {
        return 0;
    }
}
