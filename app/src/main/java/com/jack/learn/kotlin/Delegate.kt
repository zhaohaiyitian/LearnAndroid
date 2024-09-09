package com.jack.learn.kotlin

import kotlin.reflect.KProperty

/**
 * 定义一个用于属性委托的对象
 */
class Delegate {

    operator fun getValue(thisRef: Any?,property: KProperty<*>): String {
        return "getValue： 所属对象：$thisRef, 参数名称：${property.name}"
    }

    operator fun setValue(thisRef: Any?,property: KProperty<*>,value: String) {
        println("setValue: 所属对象：$thisRef, 参数名称：${property.name}, 设置的值：$value")
    }

}

fun main() {
    var demoString by Delegate()
    // 使用demoString，这时候会去调用Delegate的getValue方法
    println(demoString)
    // 设置demoString的值，这时候去会调用setValue方法
    demoString = "main"
}