#ifndef  STRCPP_H
#define STRCPP_H


#include <iostream>
#include <cstring>
#include <stdio.h>

#ifdef __cplusplus
extern "C"{
#endif
    static std::string storedString;


    const char* readStr(){
        return storedString.c_str();
    }
    void writeStr(const char* str){
        storedString = str;
    }
    bool cmpInternalStr(char* str1){
        return (strcmp(str1, storedString.c_str()));
    }

}
#endif /* ifndef  STRCPP_H */
