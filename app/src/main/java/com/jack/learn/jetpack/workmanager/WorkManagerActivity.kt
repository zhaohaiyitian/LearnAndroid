package com.jack.learn.jetpack.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.jack.learn.R
import com.jack.learn.click
import com.jack.learn.databinding.ActivityWorkmanagerBinding
import java.util.concurrent.TimeUnit

class WorkManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWorkmanagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        initWork()
        binding.apply {
            button1.click {
                test1()
            }
            button2.click {
                test2()
            }
        }
    }

    private fun initWork() {
        val request =
            OneTimeWorkRequest
                .Builder(MyWorker::class.java)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .build()
        WorkManager.getInstance(this).enqueue(request)
    }

    private fun test1() {
        val sendData = Data.Builder().putString("data", "提供给manager的数据").build()
        val request =
            OneTimeWorkRequest
                .Builder(MyWorker::class.java)
                .setInputData(sendData)
                .build()
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id).observe(this,
            object : Observer<WorkInfo> {
                override fun onChanged(t: WorkInfo) {
                    val outData = t.outputData.getString("data")
                    Log.d("wangjie", " Activity取到了任务回传的数据:$outData")
                    Log.d("wangjie", " 执行状态:"+t.state.name)
                    if (t.state.isFinished) {
                        Log.d("wangjie", " 执行状态-a：isFinished=true 后台任务已经完成了...")
                    }
                }
            })
        WorkManager.getInstance(this).enqueue(request)
    }

    private fun test2() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val sendData = Data.Builder().putString("data", "提供给manager的数据").build()
        val request =
            OneTimeWorkRequest
                .Builder(MyWorker::class.java)
                .setConstraints(constraints)
                .setInputData(sendData)
                .build()
        WorkManager.getInstance(this).enqueue(request)
    }

    private fun test3() {
        val request = PeriodicWorkRequest.Builder(MyWorker::class.java, 20L, TimeUnit.MINUTES).addTag("test3").build()
        // 监听状态，在循环任务的情况下，一般不会调用
        //原因：默认识别是一次任务完成执行回调
        //但是重复任务，你可以看作为一次任务不会完结
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id).observe(this,
            object : Observer<WorkInfo> {
                override fun onChanged(t: WorkInfo) {
                    val outData = t.outputData.getString("data")
                    Log.d("wangjie", " Activity取到了任务回传的数据:$outData")
                    Log.d("wangjie", " 执行状态:"+t.state.name)
                    if (t.state.isFinished) {
                        Log.d("wangjie", " 执行状态-a：isFinished=true 后台任务已经完成了...")
                    }
                }
            })
        WorkManager.getInstance(this).cancelAllWorkByTag("test3")
    }
}