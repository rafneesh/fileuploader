package com.rafne.filedownloader.component;

import com.rafne.filedownloader.service.FileDownloaderService;
import com.rafne.filedownloader.util.FileDownloaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class FileDownloader {

    static Logger log = Logger.getLogger(FileDownloader.class.getName());

    @Autowired
    FileDownloaderFactory factory;

    @Value("${location.path}")
    String destinationDirectory;

    @Autowired
    FileDownloaderUtils fileDownloaderUtils;

    @PostConstruct
    public void init() {

        log.warning(" FileDownloader Init Starts: "+destinationDirectory);

        fileDownloaderUtils.makeDirectory(destinationDirectory);

        log.info(" FileDownloader Init Done");

    }

    public boolean downloadFile(String fullFilePath) {

        Optional<File> result = Optional.empty();

        try {

            log.warning("Thread Id:" + Thread.currentThread().getId() +" File writing  => "+fullFilePath+" => "+fullFilePath.split(":")[0]);

            Optional<FileDownloaderService> service = factory.getFileDownloaderService(fullFilePath.split(":")[0]);

            result = service.get().download(fullFilePath, destinationDirectory);

        } catch (Exception e) {

            log.warning("Thread Id:" + Thread.currentThread().getId() +" File writing failed => "+e);

            if(result.isPresent())
                deleteFile(result.get());
        }

        if(result.isPresent())
            log.info("Thread Id:" + Thread.currentThread().getId() +  " File has been successfully written to the destination!") ;
        else
            log.warning("Thread Id:" + Thread.currentThread().getId() +  " Failed for the protocol => " + fullFilePath.split(":")[0]);

        return result.isPresent();
    }


    boolean deleteFile(File file) {

        log.finest("Thread Id:" + Thread.currentThread().getId() + "Going for File Deletion If Exists");
        try {

            if(file.exists())
                file.delete();

            log.warning("Thread Id:" + Thread.currentThread().getId() + "File Deleted Successfully/Not exists");

            return true;

        } catch (Exception e) {
            log.warning("Thread Id:" + Thread.currentThread().getId() + " error deleting the file"+e);
        }
        return false;
    }


}
