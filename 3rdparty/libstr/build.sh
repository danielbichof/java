#!/bin/bash

g++ -shared -fPIC -o bin/libstrcpp.so ./src/libstrcpp.cpp \
    -I/usr/lib/jvm/java-1.8.0-openjdk-amd64/include/ \
    -I /usr/lib/jvm/java-1.8.0-openjdk-amd64/include/linux \
    -I./src/
