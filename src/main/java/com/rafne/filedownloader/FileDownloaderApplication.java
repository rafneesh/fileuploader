package com.rafne.filedownloader;


import com.rafne.filedownloader.proxy.FileDownloaderProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class FileDownloaderApplication {

    static Logger log = LoggerFactory.getLogger(FileDownloaderApplication.class);

    public static void main(String[] args) throws InterruptedException {

        log.info("FileUploaderFactory: STARTS");

        ExecutorService executor = Executors.newCachedThreadPool();

        Arrays.asList(args).forEach((item)-> executor.execute(()-> FileDownloaderProxy.downloadFile(item)));

        executor.shutdown();
        // Wait until all threads are finish
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        log.info("FileUploaderFactory: Finished all threads: ENDS");

        System.exit(0);


    }
}
