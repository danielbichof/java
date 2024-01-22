#!/bin/bash

rm -rf bin
mkdir bin -p

g++ -shared \
    ./src/libstrcpp.cpp \
    -o bin/libstrcpp.so
