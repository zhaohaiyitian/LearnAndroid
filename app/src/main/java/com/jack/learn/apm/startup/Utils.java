package com.jack.learn.apm.startup;

import android.os.Process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class Utils {


    /**
     * 遍历每个核，找出时钟频率最高的那个核
     * @return
     */
    public static int getMaxFreqCPU() {
        int maxFreq = -1;
        try {
            for (int i = 0; i < getNumberOfCPUCores(); i++) {
                String filename = "/sys/devices/system/cpu/cpu" + i + "/cpufreq/cpuinfo_max_freq";
                File cpuInfoMaxFreqFile = new File(filename);
                if (cpuInfoMaxFreqFile.exists()) {
                    byte[] buffer = new byte[128];
                    FileInputStream stream = new FileInputStream(cpuInfoMaxFreqFile);
                    try {
                        stream.read(buffer);
                        int endIndex = 0;
                        //Trim the first number out of the byte buffer.
                        while (buffer[endIndex] >= '0' && buffer[endIndex] <= '9'
                                && endIndex < buffer.length) endIndex++;
                        String str = new String(buffer, 0, endIndex);
                        Integer freqBound = Integer.parseInt(str);
                        if (freqBound > maxFreq) maxFreq = freqBound;
                    } catch (NumberFormatException e) {

                    } finally {
                        stream.close();
                    }
                }
            }
        } catch (IOException e) {

        }
        return maxFreq;
    }


    /**
     * 统计该设备 CPU 有多少个核
     * @return
     */
    public static int getNumberOfCPUCores() {
        int cores = new File("/sys/devices/system/cpu/").listFiles((file) -> {
            String path = file.getName();
            if (path.startsWith("cpu")) {
                for (int i = 3; i < path.length(); i++) {
                    if (path.charAt(i) < '0' || path.charAt(i) > '9') {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }).length;

        return cores;
    }


    /**
     * 获取RenderThread的Pid
     * @return
     */
    public static int getRenderThreadTid() {
        File taskParent = new File("/proc/" + Process.myPid() + "/task/");
        if (taskParent.isDirectory()) {
            File[] taskFiles = taskParent.listFiles();
            if (taskFiles != null) {
                for (File taskFile : taskFiles) {
                    //读线程名
                    BufferedReader br = null;
                    String cpuRate = "";
                    try {
                        br = new BufferedReader(new FileReader(taskFile.getPath() + "/stat"), 100);
                        cpuRate = br.readLine();
                    } catch (Throwable throwable) {
                        //ignore
                    } finally {
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    if (!cpuRate.isEmpty()) {
                        String param[] = cpuRate.split(" ");
                        if (param.length < 2) {
                            continue;
                        }

                        String threadName = param[1];
                        //找到name为RenderThread的线程，则返回第0个数据就是 tid
                        if (threadName.equals("(RenderThread)")) {
                            return Integer.parseInt(param[0]);
                        }
                    }
                }
            }
        }

        return -1;
    }


}
