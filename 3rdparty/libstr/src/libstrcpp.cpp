#include <stdio.h>
#include <cstring>
#include <iostream>

static std::string storedString;

#ifdef __cplusplus
extern "C"{
#endif


    const char* readStr(){
        return storedString.c_str();
    }
    void writeStr(const char* str){
        storedString = str;
    }
    bool cmpInternalStr(char* str1){
        return (strcmp(str1, storedString.c_str()));
    }

#ifdef __cplusplus
}

#endif //cplusplus


