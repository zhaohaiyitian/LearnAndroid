package com.jack.learn.apm.startup;

import java.util.List;
import java.util.Map;

public class StartupSortStore {
    //所有的任务
    List<StartUp<?>> result;
    Map<Class<? extends StartUp>, StartUp<?>> startupMap;
    //任务依赖表
    Map<Class<? extends StartUp>, List<Class<? extends StartUp>>> startupChildrenMap;

    public StartupSortStore(List<StartUp<?>> result, Map<Class<? extends StartUp>, StartUp<?>> startupMap, Map<Class<? extends StartUp>, List<Class<? extends StartUp>>> startupChildrenMap) {
        this.result = result;
        this.startupMap = startupMap;
        this.startupChildrenMap = startupChildrenMap;
    }


    //set and get method
    public List<StartUp<?>> getResult() {
        return result;
    }

    public void setResult(List<StartUp<?>> result) {
        this.result = result;
    }

    public Map<Class<? extends StartUp>, StartUp<?>> getStartupMap() {
        return startupMap;
    }

    public void setStartupMap(Map<Class<? extends StartUp>, StartUp<?>> startupMap) {
        this.startupMap = startupMap;
    }

    public Map<Class<? extends StartUp>, List<Class<? extends StartUp>>> getStartupChildrenMap() {
        return startupChildrenMap;
    }

    public void setStartupChildrenMap(Map<Class<? extends StartUp>, List<Class<? extends StartUp>>> startupChildrenMap) {
        this.startupChildrenMap = startupChildrenMap;
    }
}
