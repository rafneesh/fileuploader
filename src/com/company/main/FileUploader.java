package com.company.main;


import com.company.main.proxy.FileUploaderProxy;
import com.company.misc.PropertySingleton;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class FileUploader {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("FileUploaderFactory: main()");

        ExecutorService executor = Executors.newFixedThreadPool(PropertySingleton.NTHREDS);

        Arrays.asList(args).forEach((item)-> executor.execute(()-> FileUploaderProxy.writeFile(item)));

        executor.shutdown();
        // Wait until all threads are finish
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        System.out.println("Finished all threads");

        System.exit(0);


    }
}
