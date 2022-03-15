package com.rafne.filedownloader.util;

import com.rafne.filedownloader.component.FileDownloader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

@Component
public class FileDownloaderUtils {


    static Logger log = Logger.getLogger(FileDownloaderUtils.class.getName());

    public File createFile(String threadId, String localFile, String urlLocation){

        return new File(localFile + threadId+ "_"+ UUID.randomUUID()+"_"+ urlLocation.split("/")[urlLocation.split("/").length - 1]);

    }

    public void makeDirectory(String destinationDirectory){

        log.info(" Creating/verifying the Destination Folder Starts: "+destinationDirectory);

        try {

            File dstFile = null;
            // check the directory for existence.
            String dstFolder = destinationDirectory.substring(0, destinationDirectory.lastIndexOf(File.separator));
            if (!(dstFolder.endsWith(File.separator) || dstFolder.endsWith("/")))
                dstFolder += File.separator;

            // Creates the destination folder if doesn't not exists
            dstFile = new File(dstFolder);
            if (!dstFile.exists()) {
                dstFile.mkdirs();
            }

        } catch (Exception e) {
            log.warning(" Error creating/accessing the destination folder");
            throw e;
        }

        log.info(" Creating/verifying the Destination Folder Done");

    }
}
