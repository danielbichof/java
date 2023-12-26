package com.projeto.main;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

class Main{
    private static final Logger logger;// = LogManager.getLogger(Main.class);
    static{
        Configurator.initialize(null, null); // Se você não tiver um arquivo de configuração, pode deixar o segundo parâmetro como null

        logger = LogManager.getLogger(Main.class);
    }
    public static void main(String[] args){
        logger.info("Somando os numeros");
        int num = Calc.sum(7,5);
        System.out.println("num: " + num);

    }

}
