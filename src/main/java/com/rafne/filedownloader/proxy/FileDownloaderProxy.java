package com.rafne.filedownloader.proxy;

import com.rafne.filedownloader.component.FileDownloaderFactory;
import com.rafne.filedownloader.config.PropertySingleton;
import com.rafne.filedownloader.service.FileDownloaderService;

import java.io.File;
import java.util.Optional;
import java.util.logging.Logger;

public interface FileDownloaderProxy {

    static Logger log = Logger.getLogger(FileDownloaderProxy.class.getName());

    static final FileDownloaderFactory factory = new FileDownloaderFactory();

    static boolean makeDirectory(String destination) {

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
            return true;
        } catch (Exception e) {
            log.warning("Thread Id:" + Thread.currentThread().getId() + " Error creating/accessing the destination folder");
        }
        return false;
    }

    static void downloadFile(String fullFilePath) {

        Optional<File> result = Optional.empty();

        if (!makeDirectory(PropertySingleton.DESTINATION)) {
            log.warning("Thread Id:" + Thread.currentThread().getId() + " Failed at directory creation");
        }

        try {

            Optional<FileDownloaderService> service = factory.getFileDownloaderService(fullFilePath.split(":")[0]);

            result = service.get().write(fullFilePath, PropertySingleton.DESTINATION);

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
