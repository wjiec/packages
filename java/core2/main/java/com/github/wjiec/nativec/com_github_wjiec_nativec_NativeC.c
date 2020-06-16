#include <stdio.h>

#include "com_github_wjiec_nativec_NativeC.h"


extern JNIEXPORT void JNICALL Java_com_github_wjiec_nativec_NativeC_say__(JNIEnv *env, jobject cl) {
    printf("hello\n");
}

extern JNIEXPORT void JNICALL Java_com_github_wjiec_nativec_NativeC_say__Ljava_lang_String_2(JNIEnv *env, jobject cl, jstring s) {
    printf("hello %s\n", s);
}