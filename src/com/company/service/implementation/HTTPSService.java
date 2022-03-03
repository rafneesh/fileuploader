package com.company.service.implementation;

import com.company.service.FileUploaderService;
import org.apache.commons.io.FileUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class HTTPSService implements FileUploaderService {
    @Override
    public boolean write(String URL_LOCATION, String LOCAL_FILE) {

        File dstFile = null;
        // check the directory for existence.
        String dstFolder = LOCAL_FILE.substring(0,LOCAL_FILE.lastIndexOf(File.separator));
        if(!(dstFolder.endsWith(File.separator) || dstFolder.endsWith("/")))
            dstFolder += File.separator;

        // Creates the destination folder if doesn't not exists
        dstFile = new File(dstFolder);
        if (!dstFile.exists()) {
            dstFile.mkdirs();
        }
        try {
            URL url = new URL(URL_LOCATION);

            File file = new File(LOCAL_FILE);

            //FileUtils.copyURLToFile(url, file);

            InputStream fileInputStream  = url.openStream();


            FileOutputStream  out = new FileOutputStream(file+URL_LOCATION.split("/")[URL_LOCATION.split("/").length-1]);

            byte[] buf=new byte[8192];
            int bytesRead = 0, bytesBuffered = 0;

            while( (bytesRead = fileInputStream.read( buf )) > -1 ) {

                out.write( buf, 0, bytesRead );
                bytesBuffered += bytesRead;
                if (bytesBuffered > 1024 * 1024) { //flush after 1MB
                    System.out.println("File writing..."+bytesBuffered);
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
}
