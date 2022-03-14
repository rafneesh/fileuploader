package com.rafne.filedownloader.proxy;

import com.rafne.filedownloader.component.FileDownloaderFactory;
import com.rafne.filedownloader.service.FileDownloaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class FileDownloaderProxy {

    static Logger log = Logger.getLogger(FileDownloaderProxy.class.getName());

    @Autowired
    FileDownloaderFactory factory;

    @Value("${location.path}")
    String destinationDirectory;

    @PostConstruct
    void init() {

        log.warning(" Creating/verifying the Destination Folder Starts:"+destinationDirectory);

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

            log.warning(" Creating/verifying the Destination Folder Done");

        } catch (Exception e) {
            log.warning(" Error creating/accessing the destination folder");
            throw e;
        }

    }

    public void downloadFile(String fullFilePath) {

        Optional<File> result = Optional.empty();

        try {

            Optional<FileDownloaderService> service = factory.getFileDownloaderService(fullFilePath.split(":")[0]);

            result = service.get().download(fullFilePath, destinationDirectory);

        } catch (Exception e) {

            log.warning("Thread Id:" + Thread.currentThread().getId() +" File writing failed => "+e);

            if(result.isPresent())
                deleteFile(result.get());
        }

        log.info("Thread Id:" + Thread.currentThread().getId() + (result.isPresent() ? " File has been successfully written to the destination!" :  " Failed for the protocol => " + fullFilePath.split(":")[0]));

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
