package com.rafne.filedownloader;


import com.rafne.filedownloader.proxy.FileDownloaderProxy;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class FileDownloaderApplication {

    static{

        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT %1$tL] [%4$-7s] %5$s %n");

    }

    private final static Logger log = Logger.getLogger(FileDownloaderApplication.class.getName());

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
