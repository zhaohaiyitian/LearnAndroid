package com.jack.learn.jetpack.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(val context: Context, val workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {

        // 接收Activity传递过来的数据
        val dataString = workerParams.inputData.getString("data")
        Log.d("wangjie", "doWork: 接收Activity传递过来的数据:$dataString")

        val sendData = Data.Builder().putString("data", "执行完任务将结果和数据回传给Activity").build()
        val result = Result.success(sendData)
        return result
    }

}