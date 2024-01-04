# Exercício 08

8. Adicione a funcionalidade de testes unitários com JUnit5.

##

Primeiramente, coloquei o projeto na estrurura padrão do maven.
Veja em "[getting started](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html#creating-a-project)" do maven

```sh
my-app
|-- pom.xml
`-- src
    |-- main
    |   `-- java
    |       `-- com
    |           `-- mycompany
    |               `-- app
    |                   `-- App.java
    `-- test
        `-- java
            `-- com
                `-- mycompany
                    `-- app
                        `-- AppTest.java
```

Adicionei a dependência do JUnit ao `pom.xml`
https://maven.apache.org/surefire/maven-surefire-plugin/examples/junit-platform.html \

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.9.1</version>
    <scope>test</scope>
</dependency>
```


##

Agora vamos criar um teste para a classe Calc.
Dentro da pasta `./src/test/java/foo/bar/baz` criamos o arquivo para teste: TestCalc.java contendo o seguinte código:

```java
package foo.bar.baz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import foo.bar.baz.Calc;

class CalcJUnitTests {
    private final Calc calculator = new Calc();

    @Test
    void addition()
    {
        assertEquals(2, calculator.sum(1,1));
    }
}
```

A classe o JUnit irá executar todos as métodos com a anotação **@Test** dentro da classe **CalcJUnitTests**.

##

Compile a gere o jar do programa

```sh
mvn clean package
```

Dessa forma o maven cria o jar e executa os testes. Caso queria apenas compilar e não rodar nenhum teste basta adicionar a flag **-DskipTests=true**

```sh
mvn package -DskipTests=true
```

Para apenas executar os testes:

```sh
mvn test
```


