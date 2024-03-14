//
// Created by wangjie on 2024/3/14.
//
#include <jni.h>
#include <string>
#include <jni.h>
#include "Log.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_jack_learn_MainActivity_stringFromJNI(JNIEnv *env ,jobject object) {
    std::string hello = "Hello from C++";
    std::string world = "World";
    return env->NewStringUTF(hello.c_str());
}

void regist(JNIEnv *env,jobject thiz, jobject callback) {
    LOGD("--动态注册调用=======成功-->");
}

jint RegisterNatives(JNIEnv *env) {
    jclass activityClass = env->FindClass("com/jack/learn/MainActivity");
    if (activityClass == NULL) {
        return JNI_ERR;
    }
    JNINativeMethod  methods_MainActivity[] ={
            {   "setAntiBiBCallback",
                "(Lcom/jack/learn/IAntiDebugCallback;)V",
                (void *)regist}

    };
    return env->RegisterNatives(activityClass,methods_MainActivity,sizeof(methods_MainActivity)/
                                                                   sizeof(methods_MainActivity[0]));
}

// 动态注册 效率高 安全性高
// 当java加载so库时调用
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reversed) {
    // 函数注册
    JNIEnv *env = NULL;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    RegisterNatives(env);

    return JNI_VERSION_1_6; // 返回最低支持的jdk版本
}

extern "C"
JNIEXPORT void JNICALL
Java_com_jack_learn_MainActivity_setAntiBiBCallback(JNIEnv *env, jobject thiz, jobject callback) {

}