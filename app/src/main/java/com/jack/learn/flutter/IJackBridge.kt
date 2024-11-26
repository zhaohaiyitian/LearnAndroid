package com.jack.learn.flutter

interface IJackBridge<P,CallBack> {

    fun onBack(p:P?)
    fun goToNative(p:P)
    fun getHeadParams(callBack: CallBack)
}