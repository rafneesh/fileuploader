package com.company.component;

import com.company.enums.Protocol;
import com.company.service.FileUploaderService;
import com.company.service.impl.HTTPSService;
import com.company.service.impl.SFTPService;
import com.company.service.impl.FTPService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class FileUploaderFactory {


    final static Map<Protocol, Supplier<FileUploaderService>> map;

    static {
        map = new HashMap<>();
        map.put(Protocol.HTTPS, HTTPSService::new);
        map.put(Protocol.HTTP, HTTPSService::new);
        map.put(Protocol.FTP, FTPService::new);
        map.put(Protocol.SFTP, SFTPService::new);
    }

    public Optional<FileUploaderService> getFileUploaderService(String protocol) {

        System.out.println("getFileUploaderService");
        Supplier<FileUploaderService> fileUploaderServiceSupplier = null;

        try {

            fileUploaderServiceSupplier = map.get(Protocol.valueOf(protocol.toUpperCase(Locale.ROOT)));

        } catch (IllegalArgumentException e) {

            System.out.println("Oh oh! The protocol is not supported, " + protocol.toUpperCase());
            throw e;
        }

            return Optional.of(fileUploaderServiceSupplier.get());

    }


    public Optional<FileUploaderService> getFileUploaderServiceProxy(String protocol) {

        System.out.println("getFileUploaderService");
        Supplier<FileUploaderService> fileUploaderServiceSupplier = null;

        try {

            fileUploaderServiceSupplier = map.get(Protocol.valueOf(protocol.toUpperCase(Locale.ROOT)));

        } catch (IllegalArgumentException e) {

            System.out.println("Oh oh! The protocol is not supported, " + protocol.toUpperCase());
            throw e;
        }

        return Optional.of(fileUploaderServiceSupplier.get());

    }


}


