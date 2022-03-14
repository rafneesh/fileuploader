package com.rafne.filedownloader.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

@Component
public class FileDownloaderUtils {

    public File createFile(String threadId, String localFile, String urlLocation){

        return new File(localFile + threadId+ "_"+ UUID.randomUUID()+"_"+ urlLocation.split("/")[urlLocation.split("/").length - 1]);

    }
}
