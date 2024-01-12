#include <iostream>
#include "foo_bar_baz_LibCpp.h"
#include <cstring>

static std::string storedString;

JNIEXPORT jstring JNICALL Java_foo_bar_baz_LibCpp_readStr
(JNIEnv *env, jobject) {
    return env->NewStringUTF(storedString.c_str());
}

JNIEXPORT void JNICALL Java_foo_bar_baz_LibCpp_writeStr
(JNIEnv *env, jobject obj, jstring javaStr) {
    const char* nativeStr = env->GetStringUTFChars(javaStr, 0);
    storedString = nativeStr;
    env->ReleaseStringUTFChars(javaStr, nativeStr);
}


JNIEXPORT jboolean JNICALL Java_foo_bar_baz_LibCpp_cmpInternalStr
(JNIEnv *env, jobject obj, jstring jstr1, jstring jstr2) {
    const char* nativeStr1 = env->GetStringUTFChars(jstr1, nullptr);
    const char* nativeStr2 = env->GetStringUTFChars(jstr2, nullptr);
    bool res = (strcmp(nativeStr1, nativeStr2) == 0);

    env->ReleaseStringUTFChars(jstr1, nativeStr1);
    env->ReleaseStringUTFChars(jstr2, nativeStr2);
    return static_cast<jboolean>(res);
}


