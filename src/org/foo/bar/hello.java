package org.foo.bar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.status.StatusLogger;


public class hello {

    static {
        StatusLogger.getLogger().setLevel(Level.OFF);
        Configurator.setRootLevel(Level.INFO);
    }

    private static final Logger logger = LogManager.getLogger(hello.class);

    public static void main(String[] args) {
        logger.info("Olá, mundo! Usando o Log4j!");
        logger.warn("Somando 2 numeros: " + Calc.sum(3,7));

    }
}

