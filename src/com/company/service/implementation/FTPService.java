package com.company.service.implementation;

import com.company.service.FileUploaderService;

import java.io.*;
import java.net.URL;


public class FTPService implements FileUploaderService {
    @Override
    public boolean write(String URL_LOCATION, String LOCAL_FILE) {

        try {

            System.out.println("File writing started for FTP");

            URL url = new URL(URL_LOCATION);

            File file = new File(LOCAL_FILE+URL_LOCATION.split("/")[URL_LOCATION.split("/").length-1]);

            System.out.println("Size of the FTP file:"+this.getFileSize(url));

            InputStream fileInputStream  = url.openStream();


            FileOutputStream  out = new FileOutputStream(file);

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
            System.out.println("File written successfully");

            return true;

        } catch (Exception e) {
            System.err.println(e);
        }

        return false;

    }

    @Override
    public int getFileSize(URL url) {
        return 0;
    }
}
