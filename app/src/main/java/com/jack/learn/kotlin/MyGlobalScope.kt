package com.jack.learn.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

object MyGlobalScope:CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = EmptyCoroutineContext
}