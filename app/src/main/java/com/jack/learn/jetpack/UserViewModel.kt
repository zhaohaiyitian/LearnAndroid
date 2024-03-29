package com.jack.learn.jetpack

import android.os.CountDownTimer
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * 参考：https://blog.csdn.net/Jason_Lee155/article/details/119970440
 * ViewModel：如何实现Activity旋转之后，依旧能恢复数据的？
 * 屏幕旋转前，Activity销毁时：
 * ComponentActivity调用onRetainNonConfigurationInstance()方法，将要销毁的Activity的mViewModelStore转化为NonConfigurationInstances对象，
 * 继续调用Activity的retainNonConfigurationInstances()方法，最终在ActivityThread的performDestroyActivity()中将数据保存在ActivityClientRecord中。
 *
 * Activity重建后：
 * 在Activity启动时，ActivityThread调用performLaunchActivity()方法，将存储在ActivityClientRecord中的lastNonConfigurationInstances
 * 通过Activity的attach()方法传递到对应的Activity中，然后通过getLastNonConfigurationInstance()恢复mViewModelStore实例对象，
 * 最后根据对应的key拿到销毁前对应的ViewModel实例
 */
class UserViewModel:ViewModel() {

    val text: MutableLiveData<String> = MutableLiveData()

    val liveData1 = MutableLiveData<String>()
    val liveData2 = MutableLiveData<String>()
    val mergerLiveData: MediatorLiveData<String> = MediatorLiveData()
    fun setText(count: Long) {
        for (i in 0..10) {
            // 参考：https://blog.csdn.net/jwg1988/article/details/122925810?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2-122925810-blog-121806419.235%5Ev43%5Epc_blog_bottom_relevance_base2&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2-122925810-blog-121806419.235%5Ev43%5Epc_blog_bottom_relevance_base2&utm_relevant_index=5

            // 循环调用postValue只会收到最后一次发送的数据 为什么？
            // postValue里首先好会将分发的值赋值给mPendingData全局变量，然后将值的分发操作放在一个Runnable里进行，
            // 从postValue到 ArchTaskExecutor执行postToMainThread方法(其实里面就是mMainHandler.post(runnable))是有一个时间间隙的。
            // 在这个时间间隙中，由于连续两次postValue,所以mPendingData带着第二次的最新值，进行分发，所以UI刷新，只能看到第二次的值。
            text.postValue("时间： "+i)
//            text.value = "setValue: ${i}"
        }
    }

    // 合并两个LiveData
    fun updateContent(): MediatorLiveData<String> {
//        viewModelScope.launch {
//
//        }
        mergerLiveData.addSource(liveData1, object : Observer<String> {
            override fun onChanged(t: String?) {
                mergerLiveData.value = t
            }

        })
        mergerLiveData.addSource(liveData2, object : Observer<String> {
            override fun onChanged(t: String?) {
                mergerLiveData.value = t
            }

        })
        return mergerLiveData
    }

    fun mergerTest() {
        object: CountDownTimer(1*60*1000,3*1000) {
            override fun onTick(millisUntilFinished: Long) {
                liveData1.postValue("网络数据有更新+${millisUntilFinished/1000}")
            }

            override fun onFinish() {
            }

        }.start()
        object: CountDownTimer(1*60*1000,5*1000) {
            override fun onTick(millisUntilFinished: Long) {
                liveData1.postValue("本地数据有更新+${millisUntilFinished/1000}")
            }

            override fun onFinish() {
            }

        }.start()
    }
}