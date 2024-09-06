package com.jack.learn.architecture.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.jack.learn.R
import java.lang.reflect.ParameterizedType

abstract class BaseMVVMActivity<VM:BaseViewModel,VB:ViewBinding> : AppCompatActivity() {

    var mVM:VM? = null
    var mBinding: VB? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // 拿到泛型          //javaClass.genericSuperclass 当前对象超类的Type
        val superClass = javaClass.genericSuperclass as ParameterizedType // ParameterizedType表示参数化的类型

        // 初始化VM        把ViewModel的初始化放到了父类里进行
        val classVM = superClass.actualTypeArguments[0] as Class<VM> // 返回此类型实际类型参数的Type对象数组
        mVM = ViewModelProvider(this).get(classVM)


        // 初始化VB
        val classVB = superClass.actualTypeArguments[1] as Class<VB>
        val vbMethod = classVB.getMethod("inflate",LayoutInflater::class.java)
        mBinding = vbMethod.invoke(null,layoutInflater) as VB
        setContentView(mBinding?.root)
    }

    fun bindView(block: VB.() -> Unit) {
        mBinding?.apply {
            block()
        }
    }

    fun bindViewModel(block: VM.()-> Unit) {
        mVM?.apply {
            block()
        }
    }

    fun requireViewModel():VM {
        return mVM!!
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}