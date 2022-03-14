package com.rafne.filedownloader.service.impl;

import com.rafne.filedownloader.service.FileDownloaderService;
import com.rafne.filedownloader.util.FileDownloaderUtils;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Dsl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class FTPService implements FileDownloaderService {

    static Logger log = Logger.getLogger(FTPService.class.getName());

    @Autowired
    FileDownloaderUtils fileDownloaderUtils;

    @Override
    public Optional<File> download(String URL_LOCATION, String LOCAL_FILE) {

        long remoteFileSize = -1;

        long fileSize = -1;

        try {

            remoteFileSize = getFileSize(URL_LOCATION);


            if(remoteFileSize<=0){

                log.info("Thread Id:" +Thread.currentThread().getId() + " File on the server is empty, FTP");

                throw new RuntimeException("File on the server is empty, unable to download");
            }


            Optional<URL> url = Optional.empty();

            log.info("Thread Id:" + Thread.currentThread().getId() + " File writing started for FTP");

            log.info("Thread Id:" + Thread.currentThread().getId() + " File on the server is, FTP:"+remoteFileSize);

            Optional<File> file = Optional.of(fileDownloaderUtils.createFile(String.valueOf(Thread.currentThread().getId()),LOCAL_FILE,URL_LOCATION));

            FileOutputStream out = new FileOutputStream(file.get());

            url = Optional.of(new URL(URL_LOCATION));

            ReadableByteChannel readableByteChannel = Channels.newChannel(url.get().openStream());

            FileChannel channel = out.getChannel();

            try (out; readableByteChannel; channel) {

                channel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

            }

            fileSize = file.get().length();

            log.info("Thread Id:" + Thread.currentThread().getId() + " FTP File written successfully/partially, remote file size:"+remoteFileSize+" and written file size:"+fileSize);

            return file;

        } catch (Exception e) {
             log.warning(e.toString());
             throw new RuntimeException("File writing fails ",e);
        }
        finally {

            if(remoteFileSize!=fileSize){

                log.info("Thread Id:" + Thread.currentThread().getId() + " FTP File size miss matches, going to delete the partial file, Size on Server:"+remoteFileSize+" On Local:"+fileSize);

                throw new RuntimeException("File size miss matches");

            }
        }


    }

    @Override
    public long getFileSize(String path) {
        URLConnection conn = null;

        try {
            conn = new URL(path).openConnection();

            return conn.getContentLengthLong();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
