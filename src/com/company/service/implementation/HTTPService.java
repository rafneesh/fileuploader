package com.company.service.implementation;

import com.company.service.FileUploaderService;
import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPService implements FileUploaderService {
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
            FileUtils.copyURLToFile(url, dstFile);
        } catch (Exception e) {
            System.err.println(e);

        }






        return false;
    }
}
