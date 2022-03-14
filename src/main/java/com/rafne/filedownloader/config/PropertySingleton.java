package com.rafne.filedownloader.config;

import org.springframework.beans.factory.annotation.Value;

import java.util.logging.Logger;

public class PropertySingleton {

    static Logger log = Logger.getLogger(PropertySingleton.class.getName());

    private PropertySingleton() {
    }

    @Value("${location.path}")
    public static String DESTINATION;  //Configure


}
