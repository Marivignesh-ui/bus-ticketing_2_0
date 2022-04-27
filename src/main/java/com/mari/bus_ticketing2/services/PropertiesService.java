package com.mari.bus_ticketing2.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
@Service
public class PropertiesService {
    private static final Properties configProp = new Properties();

    private static final Logger logger = LogManager.getLogger(PropertiesService.class);

    static {
        try {
            InputStream in = PropertiesService.class.getClassLoader().getResourceAsStream("application.properties");
            logger.info("reading  properties from the file");

            configProp.load(in);
            System.out.println("read properties success..");
            logger.info("read properties successful");
        } catch (IOException e) {
            logger.catching(e);
            logger.error("Error loading properties");
        }
    }

    public static String getProperty(String key) {
        logger.info("about to get property: " + key);
        String value = configProp.getProperty(key);
        if (value == null) {
            logger.warn("unable to get property from file for key: " + key);
        } else {
            logger.info("got property from file for key: " + key);
        }
        return value;
    }
}
