package com.company.service.implementation;

import com.company.service.FileUploaderService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HTTPSService implements FileUploaderService {
    @Override
    public boolean write(String URL_LOCATION, String LOCAL_FILE) {

        try {

            System.out.println("File writing started for HTTP/HTTPS");

            URL url = new URL(URL_LOCATION);

            File file = new File(LOCAL_FILE+URL_LOCATION.split("/")[URL_LOCATION.split("/").length-1]);

            //FileUtils.copyURLToFile(url, file);

            InputStream fileInputStream  = url.openStream();


            FileOutputStream out = new FileOutputStream(file);

            System.out.println("Size of the HTTP/HTTPS file:"+this.getFileSize(url));

            byte[] buf=new byte[8192];
            int bytesRead = 0, bytesBuffered = 0;

            while( (bytesRead = fileInputStream.read( buf )) > -1 ) {

                out.write( buf, 0, bytesRead );
                bytesBuffered += bytesRead;
                if (bytesBuffered > 1024 * 1024) { //flush after 1MB
                    System.out.println("Bytes..."+bytesBuffered);
                    bytesBuffered = 0;
                    out.flush();
                }
            }

            out.flush();
            System.out.println("HTTP/HTTPS File written successfully");

            return true;

        } catch (Exception e) {
            System.err.println(e);
        }

        return false;

    }

    @Override
    public int getFileSize(URL url) {
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).setRequestMethod("HEAD");
            }
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection)conn).disconnect();
            }
        }
    }
}
