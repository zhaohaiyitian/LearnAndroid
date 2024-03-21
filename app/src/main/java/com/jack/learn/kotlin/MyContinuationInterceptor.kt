package com.jack.learn.kotlin

import android.util.Log
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * 自定义一个拦截器
 */
class MyContinuationInterceptor: ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return MyContinuation(continuation)
    }
}

class MyContinuation<T>(val continuation: Continuation<T>): Continuation<T> {
    override val context: CoroutineContext
        get() = continuation.context

    override fun resumeWith(result: Result<T>) {
        Log.d("wangjie","$result")
        continuation.resumeWith(result)
    }

}