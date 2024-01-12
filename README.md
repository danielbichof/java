# Exercício 09

Criando uma lib c++ simples e usando no nosso programa java.

Primeiro vamos criar uma estrutura para criar a lib

```sh
mkdir -p 3rdparty/libstr/src 3rdparty/libstr/bin
```

Agora vamos criar uma classe que usa a lib C++.

```java
package foo.bar.baz;

public class LibCpp{
    static {
        System.loadLibrary("strcpp");
    }

    public native String readStr();
    public native void writeStr(String str);
    public native boolean cmpInternalStr(String str1, String str2);
}
```

Aqui carregamos uma lib chamada `strcpp`,  e declara as funções que vamos usar. Para isso vamos gerar um arquivo header "*.h*" do JNI para podermos usar na nossa lib.

```sh
javac -h 3rdparty/libstr/src/ src/main/java/foo/bar/baz/LibCpp.java
```

Assim podemos usar esse header como uma interface para o C++. Para implementar as funções que queremos usar na classe LibCpp vamos criar um arquivo `libstrcpp.h` para exportar as funções que queremos implementar.

```cpp
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

```

- *JNIEXPORT* Diz ao compilador para exportar a função
- *JNICALL* Converte a chamada da funções

Vamos implementar as funções em um documento *libstrcpp.cpp*, dentro de *3rdparty/libstr/src*

```cpp
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
```

Agora podemos compilar e gerar uma lib. No sistema linux usamos um tipo de arquivo com a extensão *.so*, para bibliotecas dinâmicas compartilhadas.

```sh
g++ -shared -fPIC -o bin/libstrcpp.so ./src/libstrcpp.cpp \
    -I/usr/lib/jvm/java-1.8.0-openjdk-amd64/include/ \
    -I /usr/lib/jvm/java-1.8.0-openjdk-amd64/include/linux \
    -I./src/
```

- *-shared -fPIC* Esses dois argumentos dizem ao compilador que vamos gerar uma biblioteca compartilhada (dinâmica) e "Position Independent Code", necessário para libs compartilhadas.

Agora podemos configurar nosso pom.xml para que o maven encontre a nossa lib. Dentro da tag *plugins* vamos adicionar os plugins necessários:

```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <version>3.2.0</version>
                <executions>
                    <execution>
                        <id>copy-resources</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>copy-resources</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>
                                ${project.build.directory}/lib
                            </outputDirectory>
                            <resources>
                                <resource>
                                    <directory>
                                        ${project.basedir}/src/main/resources/lib
                                    </directory>
                                    <includes>
                                        <include>libstrcpp.so</include>
                                    </includes>
                                </resource>
                            </resources>

                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M5</version>
                <configuration>
                    <environmentVariables>
                    <LD_LIBRARY_PATH>${project.build.directory}/lib</LD_LIBRARY_PATH>
                    </environmentVariables>
                </configuration>
        </plugin>
```

Estamos adicionando a nossa biblioteca, a partir do caminho *${project.basedir}/src/main/resources/lib*, no nosso arquivo Jar. Para isso devemos adicionar nossa lib ao caminho correto:

```sh
mkdir -p src/main/resources/lib/
cp 3rdparty/libstr/bin/libstrcpp.so src/main/resources/lib/
```

Agora podemos compilar e rodar nosso programa:

```sh
mvn clean package
java -Djava.library.path=target/lib -jar target/HelloWorld-1.0-SNAPSHOT-jar-with-dependencies.jar
```
- *-Djava.library.path=target/lib*: Devemos passar o caminho para a nossa lib, já que nossa classe não usa a biblioteca dinâmica diretamente do jar

```sh
#output
11:18:23.412 [main] INFO  foo.bar.baz.Main - Olá, mundo! Usando o Log4j!
11:18:23.414 [main] WARN  foo.bar.baz.Main - Somando 2 numeros: 10
11:18:23.436 [main] INFO  foo.bar.baz.Main - Id:1
11:18:23.436 [main] INFO  foo.bar.baz.Main - Name:Daniel
11:18:23.436 [main] INFO  foo.bar.baz.Main - Email:daniel@example.com
11:18:23.436 [main] INFO  foo.bar.baz.Main - Hello c++
```