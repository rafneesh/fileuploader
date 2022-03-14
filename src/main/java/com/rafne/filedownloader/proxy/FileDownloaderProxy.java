package com.rafne.filedownloader.proxy;

import com.rafne.filedownloader.component.FileDownloaderFactory;
import com.rafne.filedownloader.service.FileDownloaderService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public interface FileDownloaderProxy {

    static Logger log = Logger.getLogger(FileDownloaderProxy.class.getName());

    static final FileDownloaderFactory factory = new FileDownloaderFactory();

    static final String dest = "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\";

    static boolean makeDirectory(String destination) {

        boolean status = false;

        log.warning("Thread Id:" + Thread.currentThread().getId() + " Creating/verifying the Destination Folder:"+destination);

        try {

            File dstFile = null;
            // check the directory for existence.
            String dstFolder = destination.substring(0, destination.lastIndexOf(File.separator));
            if (!(dstFolder.endsWith(File.separator) || dstFolder.endsWith("/")))
                dstFolder += File.separator;

            // Creates the destination folder if doesn't not exists
            dstFile = new File(dstFolder);
            if (!dstFile.exists()) {
                dstFile.mkdirs();
            }

            status = true;

        } catch (Exception e) {
            log.warning("Thread Id:" + Thread.currentThread().getId() + " Error creating/accessing the destination folder");
            status = false;
            throw e;
        }
        return status;
    }

    static void downloadFile(String fullFilePath) {

        Optional<File> result = Optional.empty();

        if (!makeDirectory(dest)) {
            log.warning("Thread Id:" + Thread.currentThread().getId() + " Failed at directory creation");
        }

        try {

            Optional<FileDownloaderService> service = factory.getFileDownloaderService(fullFilePath.split(":")[0]);

            result = service.get().write(fullFilePath, dest);

        } catch (Exception e) {
            log.warning("Thread Id:" + Thread.currentThread().getId() +"File writing failed");
            deleteFile(result.get());
        }

        log.info("Thread Id:" + Thread.currentThread().getId() + (result.isPresent() ? " File has been successfully written to the destination!" : Thread.currentThread().getId() + " Failed for the protocol!" + fullFilePath.split(":")[0]));

    }


    static boolean deleteFile(File file) {

        log.finest("Thread Id:" + Thread.currentThread().getId() + "Going for File Deletion If Exists");
        try {

            if(file.exists())
                file.delete();

            log.warning("Thread Id:" + Thread.currentThread().getId() + "File Deleted Successfully/Not exists");

            return true;
        } catch (Exception e) {
            log.warning("Thread Id:" + Thread.currentThread().getId() + " info deleting the file");
        }
        return false;
    }


}
