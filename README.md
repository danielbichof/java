# Exercício 02

Crie outro projeto (partindo do anterior), crie um pacote, adicione outra classe, no mesmo pacote. Chame a segunda a partir do main() da primeira

##

Coloquei o meu main no pacote com.projeto.main e criei uma classe simples que faz uma some ao mesmo pacote:

```java
package com.projeto.main;

class Calc{
    Calc(){}

    public static int sum(int x, int y){
        return x + y;
    }
}
```

Agora o mesmo processo para compilar
```sh
javac com/projeto/main/*.java
```

Agora adicionando à um JAR:

```sh
jar cf programa.jar com/projeto/main/*.class
```

Executando:

```sh
java -cp programa.jar com.projeto.main.Hello
```
