//
// Created by wangjie on 2024/3/14.
//
#include <jni.h>
#include <string>
#include <jni.h>
#include "Log.h"
#include <sys/stat.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <unistd.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_jack_learn_jni_JNIActivity_stringFromJNI(JNIEnv *env ,jobject object) {
    std::string hello = "Hello from C++";
    std::string world = "World";
    return env->NewStringUTF(hello.c_str());
}

void regist(JNIEnv *env,jobject thiz, jobject callback) {
    LOGD("--动态注册调用=======成功-->");
}

jint RegisterNatives(JNIEnv *env) {
    jclass activityClass = env->FindClass("com/jack/learn/jni/JNIActivity");
    if (activityClass == NULL) {
        return JNI_ERR;
    }
    JNINativeMethod  methods_MainActivity[] ={
            {   "setAntiBiBCallback",
                "(Lcom/jack/learn/jni/IAntiDebugCallback;)V",
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
Java_com_jack_learn_jni_JNIActivity_setAntiBiBCallback(JNIEnv *env, jobject thiz, jobject callback) {

}

int m_fd;
int32_t m_size;
int8_t *m_ptr;

extern "C"
JNIEXPORT void JNICALL
Java_com_jack_learn_jni_JNIActivity_writeTest(JNIEnv *env, jobject thiz) {
    /**
     * 报错：Fatal signal 11 (SIGSEGV), code 1 (SEGV_MAPERR), fault addr 0xffffffffffffffff in tid 8231 (Thread-2), pid 8203
     */

    std::string file = "/sdcard/a.txt";
    //打开文件
    m_fd = open(file.c_str(),O_RDWR | O_CREAT,S_IRWXU);
    // 获取一页内存大小
    //Linux采用了分页来管理内存，即内存的管理中，内存是以页为单位,一般的32位系统一页为 4096个字节
    m_size = getpagesize();
    //将文件设置为 m_size这么大
    ftruncate(m_fd,m_size);
    // m_size:映射区的长度。 需要是整数页个字节    byte[]
    m_ptr = (int8_t*)mmap(0,m_size,PROT_READ | PROT_WRITE,MAP_SHARED,m_fd,0);
    std::string data("刚刚写入的数据");
    //将 data 的 data.size() 个数据 拷贝到 m_ptr
    memcpy(m_ptr,data.data(),data.size());
    LOGD("写入数据成功：%s",data.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_jack_learn_jni_JNIActivity_readTest(JNIEnv *env, jobject thiz) {
    // 申请内存
    char *buf = static_cast<char *>(malloc(100));
    memcpy(buf,m_ptr,100);
    std::string result(buf);
    LOGD("读取数据成功：%s",result.c_str());
    // 取消映射
    munmap(m_ptr,m_size);
    close(m_fd);
}