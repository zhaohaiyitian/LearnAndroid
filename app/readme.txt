JetPack
Lifecycle  通过反射加注解 拿到类中带有注解的方法
LifecycleOwner 被观察者
LifecycleObserver 观察者

LivaData粘性事件解决方案：
原来的流程是new LiveData 绑定Observer setValue执行onChanged
new对象-->订阅-->发消息-->订阅-->发消息-->订阅-->发消息

而在我们用时可能会出现new LivaData setValue执行onChanged 绑定Observer
new对象-->发消息-->订阅-->发消息-->订阅-->发消息-->订阅
当调用setValue发送数据时程序结束了 当打开第二个Activity的时候 生命周期变化的时候LifecycleBoundObserver 通过Lifecycle ReportFragment--》dispatch回调到LiveData的dispatchingValue 进行数据分发
处理方案 让第一次setValue不起效即可

// mDispatchingValue和mDispatchingValue两个变量解决递归出现的问题
  void dispatchingValue(@Nullable ObserverWrapper initiator) {
        if (mDispatchingValue) {
            mDispatchInvalidated = true;
            return;
        }
        mDispatchingValue = true;
        .........................
        mDispatchingValue = false;
    }
ViewModel 以注重生命周期的方式管理界面的相关数据
数据持久化
ViewModel的具体数据存储依赖于Bundle  封装了Bundle的处理方案
Bundle作为数据存储，已经和当前Activity实例没有任何关系了
在Activity父类的onCreate和onSaveInstanceState中完成数据的保存和恢复
使用层面使用了HashMap作为媒介

 业务目的：
        是为了达成数据持久化，
        去规避Activity在设计当中对于旋转处理造成的实例Activity重置下的数据保存问题
    1.ViewModel    =   Bundle使用！
    2.只不过是做了上层的封装！
    3.利用了jetpack组件套当中的应用！
    4. 在上一个被销毁的Activity与新建的这个Activity之间建立一个Bundle数据传递！

