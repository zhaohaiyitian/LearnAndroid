package com.jack.learn.kotlin

import android.util.Log

/**
 * 主构造函数执行优先级最高、init代码块次之、次构造函数执行优先级最低
 */
// 主构造函数
class Person(name: String) {
    //主构造函数的一部分
    var nameT: String = name

    init {
        Log.d("wangjie","init代码块 name: $nameT")
    }

    //次构造函数
    constructor(name: String, age: Int):this(name) {
        Log.d("wangjie","name: $nameT")
        Log.d("wangjie","secondary constructor")
    }


}