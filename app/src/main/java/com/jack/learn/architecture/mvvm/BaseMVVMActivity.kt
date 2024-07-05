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


        // 拿到泛型
        val superClass = javaClass.genericSuperclass as ParameterizedType

        // 初始化VM
        val classVM = superClass.actualTypeArguments[0] as Class<VM>
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