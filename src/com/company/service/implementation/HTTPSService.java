package com.company.service.implementation;

import com.company.service.FileUploaderService;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.76");
            //URLConnection connection = url.openConnection();
            BufferedInputStream stream = new BufferedInputStream(connection.getInputStream());
            int available = stream.available();
            byte b[]= new byte[available];
            stream.read(b);
            File file = new File(LOCAL_FILE);
            OutputStream out  = new FileOutputStream(file);
            out.write(b);
            System.out.println("File written successfully");
            return true;
        } catch (Exception e) {
            System.err.println(e);
        }

        return false;

    }
}
