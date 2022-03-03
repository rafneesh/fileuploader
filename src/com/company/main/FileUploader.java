package com.company.main;

import com.company.misc.Protocol;
import com.company.service.FileUploaderService;

import java.util.Arrays;
import java.util.Locale;

public class FileUploader {
    
    //

    static final FileUploaderFactory factory = new FileUploaderFactory();

    static final String destination = "C:\\Users\\muhammed.rafneesh\\Documents\\Dest\\Out";  //Configure

    public static void main(String[] args) {

        System.out.println("FileUploaderFactory: main()");

        for(String item: Arrays.asList(args)){

            System.out.println("FileUploaderFactory: Item - "+item+ " Protocol - "+item.split(":")[0]);

            FileUploaderService service = factory.createFileUploaderService(Protocol.valueOf(item.split(":")[0].toUpperCase(Locale.ROOT)));

            boolean result = service.write(item, destination);

            System.out.println(result?"File has been successfully written to the destination :":"File writing failed");

        }



    }
}
