package com.company.misc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertySingleton {

    private PropertySingleton(){}

    public static String DESTINATION = null;  //Configure

    static {

        try (InputStream input = new FileInputStream("src/com/company/resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            DESTINATION = prop.getProperty("location");

            // get the property value and print it out
            System.out.println("Configured Destination Location : " + DESTINATION);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
