package com.company.main;


import com.company.main.proxy.FileUploaderProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class FileUploader {

    static Logger log = LoggerFactory.getLogger(FileUploader.class);

    public static void main(String[] args) throws InterruptedException {

        log.info("FileUploaderFactory: main()");

        ExecutorService executor = Executors.newCachedThreadPool();

        Arrays.asList(args).forEach((item)-> executor.execute(()-> FileUploaderProxy.writeFile(item)));

        executor.shutdown();
        // Wait until all threads are finish
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        log.info("Finished all threads");

        System.exit(0);


    }
}
