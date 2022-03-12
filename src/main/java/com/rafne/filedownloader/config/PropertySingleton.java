package com.rafne.filedownloader.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertySingleton {

    static java.util.logging.Logger log = Logger.getLogger(PropertySingleton.class.getName());

    private PropertySingleton() {
    }

    public static String DESTINATION = null;  //Configure

    static {

       try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            DESTINATION = prop.getProperty("location");

            // get the property value and print it out
            log.info("Configured Destination Location : " + DESTINATION);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}
