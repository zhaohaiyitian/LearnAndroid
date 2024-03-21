JetPack
Lifecycle  通过反射和注解 拿到类中带有注解的方法
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


为什么反射耗性能？反射原理

1.内存角度讲为什么要设计class和object？
假设没有class 会导致内存爆棚
假设没有object 会导致对象不安全

方法区  class
堆区  object

反射 遍历内存--找到指令入口地址--放到栈区

// dx指令
dx --dex --verbose --dump-to=maniu.txt --dump-method=Student.run --verbose-dump Student.class

new-instance指令做的事情：
1，高速缓存声明一个v0
2，会将Student.class加载进内存(方法区)
3，实例化一个对象Student
4，v0指向Student对象

java方法== 指令的集合组成

将java方法封装成一个ArtMethod对象 ArtMethod 指向方法指令的地址

对象调用方法和反射调用方法有什么区别？
堆中的对象持有方法区中类的引用  类中有ArtMethod的列表
对象调用 直接从ArtMethod的列表中通过索引去查找方法指令

反射调用
1.遍历ArtMethod的列表
2.根据方法名匹配对应的ArtMethod再比对方法签名 找到对应的方法指令

klass描述类信息 classSize 和对象信息 objectSize
