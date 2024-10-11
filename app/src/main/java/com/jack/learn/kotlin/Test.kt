package com.jack.learn.kotlin

fun main() {
    hello {
        println("Bye bye....")
    }
    println("Continue")
}

// 交叉内联，用于修饰内联函数中的函数类型参数，使函数类型参数能被间接调用。
inline fun hello(crossinline block: ()-> Unit) {
    println("Say hello....")
    block()
}

// 关闭针对函数类型参数的内联优化，使 block 依然作为一个对象被使用，如此就可以正常 return 了
inline fun hello1(noinline block: () -> Unit): () -> Unit {
    println("Say Hello!")
    return block
}