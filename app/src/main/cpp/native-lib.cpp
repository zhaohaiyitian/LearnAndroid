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
#include <sys/prctl.h>
#include <link.h>

#include <android/log.h>
#include <chrono>
#include <regex.h>
#include "jvmti.h"
#include "MemoryFile.h"

#define LOG_TAG "jvmti"

#define ALOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)
#define ALOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define ALOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define ALOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_jack_learn_jni_JNIActivity_stringFromJNI(JNIEnv *env ,jobject object) {
    std::string hello = "Hello from C++";
    std::string world = "World";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_jack_learn_jni_JNIActivity_callAddMethod(JNIEnv *env, jobject thiz, jint number1,
                                                  jint number2) {
    jclass cls = env->GetObjectClass(thiz);
    jmethodID md = env->GetMethodID(cls,"add", "(II)I");
    int result = env->CallIntMethod(thiz,md,number1,number2);
    ALOGI("%s", reinterpret_cast<const char *>(result));
}

extern "C"
JNIEXPORT void JNICALL
Java_com_jack_learn_jni_JNIActivity_changeAge(JNIEnv *env, jobject thiz) {
    jclass cls = env->GetObjectClass(thiz);
    jfieldID filed = env->GetFieldID(cls,"age","D");
    env->SetDoubleField(thiz,filed,200.0);
}

//
//void regist(JNIEnv *env,jobject thiz, jobject callback) {
//    LOGD("--动态注册调用=======成功-->");
//}
//
//jint RegisterNatives(JNIEnv *env) {
//    jclass activityClass = env->FindClass("com/jack/learn/jni/JNIActivity");
//    if (activityClass == NULL) {
//        return JNI_ERR;
//    }
//    JNINativeMethod  methods_MainActivity[] ={
//            {   "setAntiBiBCallback",
//                "(Lcom/jack/learn/jni/IAntiDebugCallback;)V",
//                (void *)regist}
//
//    };
//    return env->RegisterNatives(activityClass,methods_MainActivity,sizeof(methods_MainActivity)/
//                                                                   sizeof(methods_MainActivity[0]));
//}

// 动态注册 效率高 安全性高
// 当java加载so库时调用
//JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reversed) {
//    // 函数注册
//    JNIEnv *env = NULL;
//    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
//        return JNI_ERR;
//    }
//    RegisterNatives(env);
//
//    return JNI_VERSION_1_6; // 返回最低支持的jdk版本
//}
//
//extern "C"
//JNIEXPORT void JNICALL
//Java_com_jack_learn_jni_JNIActivity_setAntiBiBCallback(JNIEnv *env, jobject thiz, jobject callback) {
//
//}

//int m_fd;
//int32_t m_size;
//int8_t *m_ptr;
//
//extern "C"
//JNIEXPORT void JNICALL
//Java_com_jack_learn_jni_JNIActivity_writeTest(JNIEnv *env, jobject thiz) {
//    /**
//     * 报错：Fatal signal 11 (SIGSEGV), code 1 (SEGV_MAPERR), fault addr 0xffffffffffffffff in tid 8231 (Thread-2), pid 8203
//     */
//
//    std::string file = "/sdcard/a.txt";
//    //打开文件
//    m_fd = open(file.c_str(),O_RDWR | O_CREAT,S_IRWXU);
//    // 获取一页内存大小
//    //Linux采用了分页来管理内存，即内存的管理中，内存是以页为单位,一般的32位系统一页为 4096个字节
//    m_size = getpagesize();
//    //将文件设置为 m_size这么大
//    ftruncate(m_fd,m_size);
//    // m_size:映射区的长度。 需要是整数页个字节    byte[]
//    m_ptr = (int8_t*)mmap(0,m_size,PROT_READ | PROT_WRITE,MAP_SHARED,m_fd,0);
//    std::string data("刚刚写入的数据");
//    //将 data 的 data.size() 个数据 拷贝到 m_ptr
//    memcpy(m_ptr,data.data(),data.size());
//    LOGD("写入数据成功：%s",data.c_str());
//}
//extern "C"
//JNIEXPORT void JNICALL
//Java_com_jack_learn_jni_JNIActivity_readTest(JNIEnv *env, jobject thiz) {
//    // 申请内存
//    char *buf = static_cast<char *>(malloc(100));
//    memcpy(buf,m_ptr,100);
//    std::string result(buf);
//    LOGD("读取数据成功：%s",result.c_str());
//    // 取消映射
//    munmap(m_ptr,m_size);
//    close(m_fd);
//}

jvmtiEnv *mJvmtiEnv;
MemoryFile *memoryFile;
jlong tag = 0;

std::string mPackageName;

//查找过滤
jboolean findFilter(const char *name) {
    std::string tmpstr = name;
    int idx;
    //先判断甩没有Error，有Error直接输出
    idx = tmpstr.find(mPackageName);
    if (idx == std::string::npos) {
        idx = tmpstr.find("OutOfMemoryError");
        if (idx == std::string::npos)//不存在。
        {
            return JNI_FALSE;
        } else {
            return JNI_TRUE;
        }
    } else {
        return JNI_TRUE;
    }
}

// 获取当时系统时间
std::string GetCurrentSystemTime() {
    //auto t = std::chrono::system_clock::to_time_t(std::chrono::system_clock::now());
    auto now = std::chrono::system_clock::now();
    //通过不同精度获取相差的毫秒数
    uint64_t dis_millseconds =
            std::chrono::duration_cast<std::chrono::milliseconds>(now.time_since_epoch()).count()
            - std::chrono::duration_cast<std::chrono::seconds>(now.time_since_epoch()).count() * 1000;
    time_t tt = std::chrono::system_clock::to_time_t(now);
    struct tm *ptm = localtime(&tt);
    char date[60] = {0};
    sprintf(date, "%d-%02d-%02d %02d:%02d:%02d.%03d",
            (int) ptm->tm_year + 1900, (int) ptm->tm_mon + 1, (int) ptm->tm_mday,
            (int) ptm->tm_hour, (int) ptm->tm_min, (int) ptm->tm_sec, (int) dis_millseconds);
    return move(std::string(date));
}

jvmtiEnv *CreateJvmtiEnv(JavaVM *vm) {
    jvmtiEnv *jvmti_env;
    jint result = vm->GetEnv((void **) &jvmti_env, JVMTI_VERSION_1_2);
    if (result != JNI_OK) {
        ALOGI("CreateJvmtiEnv is NULL");
        return nullptr;
    }
    return jvmti_env;
}

//调用System.Load()后会回调该方法
extern "C"
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    ALOGI("JNI_OnLoad");
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    ALOGI("JNI_OnLoad Finish");
    return JNI_VERSION_1_6;
}

void JNICALL objectAlloc(jvmtiEnv *jvmti_env, JNIEnv *jni_env, jthread thread,
                         jobject object, jclass object_klass, jlong size) {
    //给对象打tag，后续在objectFree()内可以通过该tag来判断是否成对出现释放
    tag += 1;
    jvmti_env->SetTag(object, tag);

    //获取线程信息
    jvmtiThreadInfo threadInfo;
    jvmti_env->GetThreadInfo(thread, &threadInfo);

    //获得 创建的对象的类签名
    char *classSignature;
    jvmti_env->GetClassSignature(object_klass, &classSignature, nullptr);

    if (mPackageName.empty() || findFilter(classSignature)) {
        //写入日志文件
        char str[500];
        char *format = "%s: object alloc {Thread:%s Class:%s Size:%lld Tag:%lld} \r\n";
        //ALOGI(format, GetCurrentSystemTime().c_str(),threadInfo.name, classSignature, size, tag);
        sprintf(str, format, GetCurrentSystemTime().c_str(), threadInfo.name, classSignature, size,
                tag);
        memoryFile->write(str, sizeof(char) * strlen(str));
    }
    jvmti_env->Deallocate((unsigned char *) classSignature);
}

void JNICALL methodEntry(jvmtiEnv *jvmti_env, JNIEnv *jni_env, jthread thread, jmethodID method) {
    jclass clazz;
    char *signature;
    char *methodName;

    //获得方法对应的类
    jvmti_env->GetMethodDeclaringClass(method, &clazz);
    //获得类的签名
    jvmti_env->GetClassSignature(clazz, &signature, nullptr);
    //获得方法名字
    jvmti_env->GetMethodName(method, &methodName, nullptr, nullptr);

    if (mPackageName.empty() || findFilter(signature)) {
        //写日志文件
        char str[500];
        char *format = "%s: methodEntry {%s %s} \r\n";
        //ALOGI(format, GetCurrentSystemTime().c_str(), signature, methodName);
        sprintf(str, format, GetCurrentSystemTime().c_str(), signature, methodName);
        memoryFile->write(str, sizeof(char) * strlen(str));
    }

    jvmti_env->Deallocate((unsigned char *) methodName);
    jvmti_env->Deallocate((unsigned char *) signature);
}

//初始化工作
extern "C"
JNIEXPORT jint JNICALL
Agent_OnAttach(JavaVM *vm, char *options, void *reserved) {
    int error;
    //准备JVMTI环境
    mJvmtiEnv = CreateJvmtiEnv(vm);

    //开启JVMTI的能力
    jvmtiCapabilities caps;
    mJvmtiEnv->GetPotentialCapabilities(&caps);
    mJvmtiEnv->AddCapabilities(&caps);

    ALOGI("Agent_OnAttach Finish");
    return JNI_OK;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_jack_learn_apm_Monitor_attachInit(JNIEnv *env, jobject thiz, jstring path) {
    ALOGI("attachInit");

    const char *_path = env->GetStringUTFChars(path, NULL);

    ALOGI("mPackageName:%s", mPackageName.c_str());

    memoryFile = new MemoryFile(_path);

    //开启JVMTI事件监听
    jvmtiEventCallbacks callbacks;
    memset(&callbacks, 0, sizeof(callbacks));
    callbacks.VMObjectAlloc = &objectAlloc;
    callbacks.MethodEntry = &methodEntry;

    ALOGI("SetEventCallbacks");

    //设置回调函数
    int error = mJvmtiEnv->SetEventCallbacks(&callbacks, sizeof(callbacks));
    ALOGI("返回码：%d\n", error);

    //开启监听
    mJvmtiEnv->SetEventNotificationMode(JVMTI_ENABLE, JVMTI_EVENT_VM_OBJECT_ALLOC, nullptr);
    mJvmtiEnv->SetEventNotificationMode(JVMTI_ENABLE, JVMTI_EVENT_METHOD_ENTRY, nullptr);

    env->ReleaseStringUTFChars(path, _path);

    ALOGI("attachInit Finished");
}
extern "C"
JNIEXPORT void JNICALL
Java_com_jack_learn_apm_Monitor_attachRelease(JNIEnv *env, jobject thiz) {
    delete memoryFile;
    //关闭监听
    mJvmtiEnv->SetEventNotificationMode(JVMTI_DISABLE, JVMTI_EVENT_VM_OBJECT_ALLOC, NULL);
    mJvmtiEnv->SetEventNotificationMode(JVMTI_DISABLE, JVMTI_EVENT_METHOD_ENTRY, NULL);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_jack_learn_apm_Monitor_attachWFilters(JNIEnv *env, jobject thiz, jstring packagename) {
    const char *_packagename = env->GetStringUTFChars(packagename, NULL);
    mPackageName = std::string(_packagename);
    env->ReleaseStringUTFChars(packagename, _packagename);
}