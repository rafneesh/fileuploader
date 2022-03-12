package com.rafne.filedownloader.service.impl;

import com.rafne.filedownloader.service.FileDownloaderService;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Dsl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Optional;
import java.util.logging.Logger;

public class HTTPSService implements FileDownloaderService {

    static java.util.logging.Logger log = Logger.getLogger(HTTPSService.class.getName());

    AsyncHttpClient client = Dsl.asyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setReadTimeout(Integer.MAX_VALUE));

    @Override
    public Optional<File> write(String URL_LOCATION, String LOCAL_FILE) {

        Optional<File> file = Optional.of(new File(LOCAL_FILE + Thread.currentThread().getId()+ "_"+System.currentTimeMillis()+"_"+ URL_LOCATION.split("/")[URL_LOCATION.split("/").length - 1]));

        long remoteFileSize = -1;

        long fileSize = -1;

        try {

            remoteFileSize = getFileSize(URL_LOCATION);


            if(remoteFileSize<=0){

                log.info("Thread Id:" +Thread.currentThread().getId() + " File on the server is empty, HTTP/HTTPS");

                return file;
            }


            Optional<URL> url = Optional.empty();

            log.info("Thread Id:" + Thread.currentThread().getId() + " File writing started for HTTP/HTTPS");

            log.info("Thread Id:" + Thread.currentThread().getId() + " File on the server is, HTTP/HTTPS:"+remoteFileSize);

            FileOutputStream out = new FileOutputStream(file.get());

            url = Optional.of(new URL(URL_LOCATION));

            ReadableByteChannel readableByteChannel = Channels.newChannel(url.get().openStream());

            FileChannel channel = out.getChannel();

            try (out; readableByteChannel; channel) {

                channel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

            }

            fileSize = file.get().length();

            log.info("Thread Id:" + Thread.currentThread().getId() + " HTTP/HTTPS File written successfully/partially, remote file size:"+remoteFileSize+" and written file size:"+fileSize);

            return file;

        } catch (Exception e) {

            log.warning(e.toString());
        }
        finally {

            if(remoteFileSize!=fileSize){

                log.warning("Thread Id:" + Thread.currentThread().getId() + " HTTP/HTTPS File size miss matches, going to delete the partial file, Size on Server:"+remoteFileSize+" On Local:"+fileSize);

                throw new RuntimeException("File size miss matches");

            }
        }

        return file;

    }

    @Override
    public long getFileSize(String path) {
        URLConnection conn = null;
        try {
            conn = new URL(path).openConnection();
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).setRequestMethod("HEAD");
            }

            //conn.getInputStream();

            long length = conn.getContentLengthLong();

            return length;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).disconnect();
            }
        }
    }
}
