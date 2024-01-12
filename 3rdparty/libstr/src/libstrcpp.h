#ifndef LIBSTR_H
#define LIBSTR_H
#include <jni.h>

#ifdef __cplusplus
extern "C"{
#endif

JNIEXPORT void JNICALL Java_foo_bar_baz_LibCpp_writeStr(JNIEnv *env, jobject obj, jstring str);
JNIEXPORT jstring JNICALL Java_foo_bar_baz_LibCpp_readStr(JNIEnv *env, jobject obj);
JNIEXPORT jboolean JNICALL Java_foo_bar_baz_LibCpp_cmpInternalStr(JNIEnv *env, jobject obj, jstring str1, jstring str2);
#ifdef __cplusplus
}
#endif //cplusplus
#endif //LIBSTR_H
