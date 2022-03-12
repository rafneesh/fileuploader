package com.rafne.filedownloader.component;

import com.rafne.filedownloader.enums.Protocol;
import com.rafne.filedownloader.service.FileDownloaderService;
import com.rafne.filedownloader.service.impl.FTPService;
import com.rafne.filedownloader.service.impl.HTTPSService;
import com.rafne.filedownloader.service.impl.SFTPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class FileDownloaderFactory {

    static Logger log = LoggerFactory.getLogger(FileDownloaderFactory.class);

    final static Map<Protocol, Supplier<FileDownloaderService>> map;

    static {
        map = new HashMap<>();
        map.put(Protocol.HTTPS, HTTPSService::new);
        map.put(Protocol.HTTP, HTTPSService::new);
        map.put(Protocol.FTP, FTPService::new);
        map.put(Protocol.SFTP, SFTPService::new);
    }

    public Optional<FileDownloaderService> getFileDownloaderService(String protocol) {

        log.debug("Thread Id:" + Thread.currentThread().getId() +"getFileUploaderService");
        Supplier<FileDownloaderService> fileDownloaderServiceSupplier = null;

        try {

            fileDownloaderServiceSupplier = map.get(Protocol.valueOf(protocol.toUpperCase(Locale.ROOT)));

        } catch (IllegalArgumentException e) {

            log.error("Thread Id:" + Thread.currentThread().getId() +"Oh oh! The protocol is not supported, " + protocol.toUpperCase());
            throw e;
        }

            return Optional.of(fileDownloaderServiceSupplier.get());

    }


    public Optional<FileDownloaderService> getFileDownloaderServiceProxy(String protocol) {

        log.error("Thread Id:" + Thread.currentThread().getId() +"getFileDownloaderServiceProxy");
        Supplier<FileDownloaderService> fileDownloaderServiceSupplier = null;

        try {

            fileDownloaderServiceSupplier = map.get(Protocol.valueOf(protocol.toUpperCase(Locale.ROOT)));

        } catch (IllegalArgumentException e) {

            System.out.println("Oh oh! The protocol is not supported, " + protocol.toUpperCase());
            throw e;
        }

        return Optional.of(fileDownloaderServiceSupplier.get());

    }


}


