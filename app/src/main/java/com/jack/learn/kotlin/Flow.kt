package com.jack.learn.kotlin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking


fun main() {
    runBlocking {
//        val flow = flowOf(1, 2, 3, 4, 5)
//        flow.filter {
//            it % 2 == 0
//        }.onEach {
//            println(it)
//        }.map {
//            it * it
//        }.collect {
//            println(it)
//        }

//        flow {
//            emit(1)
//            emit(2)
//            delay(600)
//            emit(3)
//            delay(100)
//            emit(4)
//            delay(100)
//            emit(5)
//        }
//            .debounce(500)
//            .collect {
//                println(it)
//            }

//        flow {
//            while (true) {
//                emit("发送一条弹幕")
//            }
//        }
//            .sample(2000)
//            .flowOn(Dispatchers.IO) //由于flow是通过死循环不断发送的，我们必须调用flowOn函数，让它在IO线程里去执行，否则我们的主线程就一直被卡死了。
//            .collect {
//                println(it)
//            }

//        val result = flow {
//            for (i in (1..100)) {
//                emit(i)
//            }
//        }.reduce {acc,value-> acc+value}
//        println(result)

//        val result = flow {
//            for (i in ('A'..'Z')) {
//                emit(i.toString())
//            }
//        }.fold("aa") {acc,value-> acc+value}
//        println(result)
//
//        flowOf(1,2,3)
//            .flatMapConcat {
//                flowOf("a$it","b$it")
//            }.collect {
//                println(it)
//            }

//        val flow1 = flowOf("a", "b", "c")
//        val flow2 = flowOf(1, 2, 3, 4, 5)
//        flow1.zip(flow2) { a, b ->
//            a + b
//        }.collect {
//            println(it)
//        }

//        val sharedFlow = MutableStateFlow<Int>(1)
//        sharedFlow.onEach { println(it) }.collect()
    }
}