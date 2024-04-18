package com.jack.learn.flutter

interface IShowMessage {
    fun onShowMessage(args: String?)

    fun sendMessage(message: String?, useEventChannel: Boolean)
}