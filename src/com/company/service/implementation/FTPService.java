package com.company.service.implementation;

import com.company.service.FileUploaderService;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Dsl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Optional;


public class FTPService implements FileUploaderService {


    AsyncHttpClient client = Dsl.asyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setReadTimeout(10000000));

    @Override
    public Optional<File> write(String URL_LOCATION, String LOCAL_FILE) {

        Optional<URL> url = Optional.empty();

        Optional<File> file = Optional.empty();

        long remoteFileSize = -1;

        long fileSize = -1;

        try {

            System.out.println(Thread.currentThread().getId() + " File writing started for HTTP/HTTPS");

            url = Optional.of(new URL(URL_LOCATION));

            remoteFileSize = getFileSize(url.get());

            System.out.println(Thread.currentThread().getId() + " File on the server is, HTTP/HTTPS:"+remoteFileSize);

            if(remoteFileSize<=0){

                System.out.println(Thread.currentThread().getId() + " File on the server is empty, HTTP/HTTPS");

                return file;
            }

            file = Optional.of(new File(LOCAL_FILE + URL_LOCATION.split("/")[URL_LOCATION.split("/").length - 1]));

            FileOutputStream out = new FileOutputStream(file.get());

            ReadableByteChannel readableByteChannel = Channels.newChannel(url.get().openStream());

            FileChannel channel = out.getChannel();

            try (out; readableByteChannel; channel) {

                channel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

            }

            fileSize = file.get().length();

            System.out.println(Thread.currentThread().getId() + " HTTP/HTTPS File written successfully/partially, remote file size:"+remoteFileSize+" and written file size:"+fileSize);

            return file;

        } catch (Exception e) {

            System.err.println(e);
        }
        finally {

            if(remoteFileSize!=fileSize){

                System.out.println(Thread.currentThread().getId() + " HTTP/HTTPS File size miss matches, going to delete the partial file, Size on Server:"+remoteFileSize+" On Local:"+fileSize);

                throw new RuntimeException("File size miss matches");

            }
        }

        return file;

    }

    @Override
    public long getFileSize(URL url) {
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).setRequestMethod("HEAD");
            }
            conn.getInputStream();

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
