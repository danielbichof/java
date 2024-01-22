
# Exercício 10

Usando a mesma biblioteca C++ e fazer aplicação em Java integrando tal biblioteca via JNA.

##

Seguindo a documentação do [JNA no github](https://github.com/java-native-access/jna?tab=readme-ov-file)
encontramos o a referencia para adiciona-lo ao maven

```xml
<dependency>
    <groupId>net.java.dev.jna</groupId>
    <artifactId>jna-platform</artifactId>
    <version>5.14.0</version>
</dependency>
```

Com JNA podemos deixar nosso código mais limpo e nos privar da complexidade das chamadas do JNI.
Vejamos como nossa biblioteca C++ fica com essa mudança. Podemos descartar o uso de um header, e também do header do JNI,
Assim nos concentramos apenas na implementação que contem valor para nossa aplicação.

```cpp
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
```

O código executa as mesmas coisas que antes, porem vemos que sem as chamadas do JNI.
Ele ainda contem a chamada de **extern "C"**, como já vimos o java carrega apenas código em C puro.

Vamos compilar:
```sh
g++ -shared \
    ./src/libstrcpp.cpp \
    -o bin/libstrcpp.so
```

Com isso podemos implementar uma interface Java, deixando tudo muito mais simples:

```java
package foo.bar.baz;

import com.sun.jna.Library;

public interface LibCpp extends Library {
    public String readStr();
    public void writeStr(String str);
    public boolean cmpInternalStr(String str1, String str2);
}
```

Aqui não carregamos nossa biblioteca, apenas declaramos as funções que vamos utilizar. Vamos carregar a biblioteca na hora de instanciar nossa interface. Alterando o código anterior, ele deve ficar assim:

```java
import com.sun.jna.Native;
...
LibCpp clib = (LibCpp) Native.loadLibrary("strcpp", LibCpp.class);
clib.writeStr("Hello c++ with JNA");
logger.info(clib.readStr());
```

Agora podemos compilar usando maven:
```sh
mkdir -p src/main/resources/lib/
cp 3rdparty/libstr/bin/libstrcpp.so src/main/resources/lib/
mvn clean package
```

```sh
java -Djna.library.path=target/lib -jar target/HelloWorld-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Note que ao invés de usarmos o **java.library.path** estamos usando **jna.library.path**, isso porque não estamos
mais carregando a biblioteca com JNI nativo do java, e sim uma API que faz isso para nós.

```sh
#output
09:30:27.973 [main] INFO  foo.bar.baz.Main - Olá, mundo! Usando o Log4j!
09:30:27.975 [main] WARN  foo.bar.baz.Main - Somando 2 numeros: 10
09:30:27.995 [main] INFO  foo.bar.baz.Main - Id:1
09:30:27.995 [main] INFO  foo.bar.baz.Main - Name:Daniel
09:30:27.995 [main] INFO  foo.bar.baz.Main - Email:daniel@example.com
09:30:28.027 [main] INFO  foo.bar.baz.Main - Hello c++ com JNA
```
