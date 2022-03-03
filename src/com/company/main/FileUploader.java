package com.company.main;

import com.company.misc.Protocol;
import com.company.service.FileUploaderService;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FileUploader {

    static String destination = "";  //Configure

    static {

        try (InputStream input = new FileInputStream("src/com/company/resources/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            destination = prop.getProperty("location");

            // get the property value and print it out
            System.out.println("Configured Destination Location : " + destination);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    static final FileUploaderFactory factory = new FileUploaderFactory();

    public static void main(String[] args) {

        System.out.println("FileUploaderFactory: main()");

        //Arrays.asList(args).forEach((item)->factory.getFileUploaderService(item.split(":")[0]).get().write(item,destination));

        for (String item : Arrays.asList(args)) {

            System.out.println("FileUploaderFactory: Item - " + item + " Protocol - " + item.split(":")[0]);

            boolean result;
            
            try {

                Optional<FileUploaderService> service = factory.getFileUploaderService(item.split(":")[0]);

                result = service.get().write(item, destination);

            } catch (Exception e) {
                System.out.println("File writing failed");
                continue;
            }

            System.out.println(result ? "File has been successfully written to the destination!" : "File writing failed!");

        }
    }
}
