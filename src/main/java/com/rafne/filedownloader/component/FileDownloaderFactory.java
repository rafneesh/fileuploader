package com.rafne.filedownloader.component;

import com.rafne.filedownloader.enums.Protocol;
import com.rafne.filedownloader.service.FileDownloaderService;
import com.rafne.filedownloader.service.impl.FTPService;
import com.rafne.filedownloader.service.impl.HTTPSService;
import com.rafne.filedownloader.service.impl.SFTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Component
public class FileDownloaderFactory {

    static Logger log = Logger.getLogger(FileDownloaderFactory.class.getName());

    @Autowired
    HTTPSService httpsService;

    @Autowired
    FTPService ftpService;

    @Autowired
    SFTPService sftpService;

    public Optional<FileDownloaderService> getFileDownloaderService(String protocol) {

        log.finest("Thread Id:" + Thread.currentThread().getId() + "getFileUploaderService");
        Supplier<FileDownloaderService> fileDownloaderServiceSupplier = null;

        switch (Protocol.valueOf(protocol.toUpperCase())) {
            case HTTP:
            case HTTPS:
                return Optional.of(httpsService);
            case FTP:
                return Optional.of(ftpService);
            case SFTP:
                return Optional.of(sftpService);
            default:
                log.warning("Thread Id:" + Thread.currentThread().getId() + "Oh oh! The protocol is not supported, " + protocol.toUpperCase());
                throw new IllegalArgumentException();
        }

    }
}


