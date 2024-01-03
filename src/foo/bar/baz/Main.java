package foo.bar.baz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.status.StatusLogger;

import foo.bar.baz.proto.PersonOuterClass;
import foo.bar.baz.proto.PersonOuterClass.Person;

public class Main {

    static {
        StatusLogger.getLogger().setLevel(Level.OFF);
        Configurator.setRootLevel(Level.INFO);
    }

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Olá, mundo! Usando o Log4j!");
        logger.warn("Somando 2 numeros: " + Calc.sum(3,7));


        Person person = Person.newBuilder()
            .setName("Daniel")
            .setId(1)
            .setEmail("daniel@example.com")
            .build();

        //Fazendo a serialização
        byte[] serializedData = person.toByteArray();
        try{
            // Desserializando
            Person newPerson = Person.parseFrom(serializedData);
            logger.info("Id:" + newPerson.getId());
            logger.info("Name:" + newPerson.getName());
            logger.info("Email:" + newPerson.getEmail());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

