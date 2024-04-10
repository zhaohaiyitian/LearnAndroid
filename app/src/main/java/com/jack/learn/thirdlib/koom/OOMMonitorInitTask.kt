package com.jack.learn.thirdlib.koom

import android.app.Application
import com.kwai.koom.base.InitTask
import com.kwai.koom.base.MonitorLog
import com.kwai.koom.base.MonitorManager
import com.kwai.koom.javaoom.monitor.OOMHprofUploader
import com.kwai.koom.javaoom.monitor.OOMMonitorConfig
import com.kwai.koom.javaoom.monitor.OOMReportUploader
import java.io.File

class OOMMonitorInitTask: InitTask {

    override fun init(application: Application) {
        val config = OOMMonitorConfig.Builder()
            .setThreadThreshold(50) //50 only for test! Please use default value!
            .setFdThreshold(300) // 300 only for test! Please use default value!
            .setHeapThreshold(0.9f) // 0.9f for test! Please use default value!
            .setVssSizeThreshold(1_000_000) // 1_000_000 for test! Please use default value!
            .setMaxOverThresholdCount(1) // 1 for test! Please use default value!
            .setAnalysisMaxTimesPerVersion(3) // Consider use default value！
            .setAnalysisPeriodPerVersion(15 * 24 * 60 * 60 * 1000) // Consider use default value！
            .setLoopInterval(5_000) // 5_000 for test! Please use default value!
            .setEnableHprofDumpAnalysis(true)
            // 内存快照回调
            .setHprofUploader(object: OOMHprofUploader {
                //type 快照类型 origin 原始 stripped 裁剪
                override fun upload(file: File, type: OOMHprofUploader.HprofType) {
                    MonitorLog.e("OOMMonitor", "todo, upload hprof ${file.name} if necessary")
                }
            })
            // 报告回调
            .setReportUploader(object: OOMReportUploader {
                override fun upload(file: File, content: String) {
                    MonitorLog.i("OOMMonitor", "wangjie: "+content)
                    MonitorLog.e("OOMMonitor", "todo, upload report ${file.name} if necessary")
                }
            })
            .build()
        MonitorManager.addMonitorConfig(config)
    }
}