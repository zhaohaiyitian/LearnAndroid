package com.jack.learn.apm.startup.sort;
import com.jack.learn.apm.startup.Startup;
import com.jack.learn.apm.startup.StartupSortStore;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopologySort {

    public static StartupSortStore sort(List<? extends Startup<?>> startupList) {
        //入度表
        Map<Class<? extends Startup>, Integer> inDegreeMap = new HashMap<>();
        //0度表
        Deque<Class<? extends Startup>> zeroDeque = new ArrayDeque<>();
        //用于保存原图
        Map<Class<? extends Startup>, Startup<?>> startupMap = new HashMap<>();
        //任务依赖表
        Map<Class<? extends Startup>, List<Class<? extends Startup>>> startupChildrenMap = new HashMap<>();

        //1.找出图中入度数为0的节点
        for (Startup<?> startup : startupList) {
            startupMap.put(startup.getClass(), startup);
            //记录每个任务的入度数（依赖的任务数）
            int dependenciesCount = startup.getDependenciesCount();
            inDegreeMap.put(startup.getClass(), dependenciesCount);
            //记录入度数（依赖的任务数）为0的任务
            if (dependenciesCount == 0) {
                zeroDeque.offer(startup.getClass());
            } else {
                //遍历本任务的依赖（父）任务列表
                for (Class<? extends Startup<?>> parent : startup.dependencies()) {
                    List<Class<? extends Startup>> children = startupChildrenMap.get(parent);
                    if (children == null) {
                        children = new ArrayList<>();
                        //记录这个父任务的所有子任务
                        startupChildrenMap.put(parent, children);
                    }
                    children.add(startup.getClass());
                }
            }
        }

        //2.删除图中入度为0的这些顶点,并更新全图
        List<Startup<?>> result = new ArrayList<>();
//        List<Startup<?>> main = new ArrayList<>();
//        List<Startup<?>> threads = new ArrayList<>();


        //处理入度为0的任务
        while (!zeroDeque.isEmpty()) {
            Class<? extends Startup> cls = zeroDeque.poll();
            Startup<?> startup = startupMap.get(cls);
            result.add(startup);
//            if (startup.callCreateOnMainThread()) {
//                main.add(startup);
//            } else {
//                threads.add(startup);
//            }


            //删除此入度为0的任务
            if (startupChildrenMap.containsKey(cls)) {
                List<Class<? extends Startup>> childStartup = startupChildrenMap.get(cls);
                for (Class<? extends Startup> childCls : childStartup) {
                    Integer num = inDegreeMap.get(childCls);
                    inDegreeMap.put(childCls, num - 1);
                    if (num - 1 == 0) {
                        zeroDeque.offer(childCls);
                    }
                }
            }
        }
//        result.addAll(threads);
//        result.addAll(main);
        return new StartupSortStore(result, startupMap, startupChildrenMap);
    }
}
