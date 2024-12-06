package com.jack.learn.kotlin.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
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
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit


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

//        try {
//            (1..3).asFlow().collect {
//                cancel()
//            }
//        } catch (e: Exception) {  // 下游异常 用try catch函数捕获
//
//        }
//
//
//        flow {
//            emit(1)
//        }.catch { e: Throwable -> // 上游异常 用catch函数捕获
//            emit(10)
//        }.flowOn(Dispatchers.IO).collect {
//
//        }

        val receiveChannel: ReceiveChannel<Int> = GlobalScope.produce {
            repeat(1000) {
                delay(1000)
                send(1)
            }
        }
        val consumer = GlobalScope.launch {
            for (i in receiveChannel) {
                println("recevie: $i")
            }
        }
        consumer.join()

        val sendChannel: SendChannel<Int> = GlobalScope.actor {
            while (true) {
                var element = receive()
                println(element)
            }
        }

        val producer = GlobalScope.launch {
            delay(1000)
            for (i in 0..3) {
                sendChannel.send(i)
            }
        }
        producer.join()
        var count = 0
        val mutex= Mutex()
        List(1000) {
            GlobalScope.launch {
                mutex.withLock {
                    count++
                }
            }
        }.joinAll()
        println(count)
//
//        val semaphore = Semaphore(1)
//        List(1000) {
//            GlobalScope.launch {
//                semaphore.withPermit {
//                    count++
//                }
//            }
//        }.joinAll()
//        println(count)

//        val channel = Channel<Int>()
//        launch {
//            for (i in 1..5) {
//                delay(1000)
//                channel.send(i)
//            }
//            channel.close()
//        }
//
//        launch {
//            for (value in channel) {
//                println(value)
//            }
//        }

        val flow = flow {
            repeat(5) {
                emit(it)
            }
        }
        flow.transform { emit(it*2) }
            .collect()
    }
}