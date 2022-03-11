package com.company.main.proxy;

import com.company.component.FileUploaderFactory;
import com.company.config.PropertySingleton;
import com.company.service.FileUploaderService;

import java.io.File;
import java.util.Optional;

public interface FileUploaderProxy {

    static final FileUploaderFactory factory = new FileUploaderFactory();

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
            System.err.println(Thread.currentThread().getId() + " Error creating/accessing the destination folder");
        }
        return false;
    }

    static void writeFile(String fullFilePath) {

        Optional<File> result = Optional.empty();

        if (!makeDirectory(PropertySingleton.DESTINATION)) {
            System.err.println(Thread.currentThread().getId() + " File writing failed at directory creation");
        }

        try {

            Optional<FileUploaderService> service = factory.getFileUploaderService(fullFilePath.split(":")[0]);

            result = service.get().write(fullFilePath, PropertySingleton.DESTINATION);

        } catch (Exception e) {
            System.err.println("File writing failed");
            deleteFile(result.get());
        }

        System.out.println(Thread.currentThread().getId() + (result.isPresent() ? " File has been successfully written to the destination!" : Thread.currentThread().getId() + " Failed for the protocol!" + fullFilePath.split(":")[0]));

    }


    static boolean deleteFile(File file) {

        System.out.println(Thread.currentThread().getId() + "Going for File Deletion If Exists");
        try {

            if(file.exists())
                file.delete();

            System.out.println(Thread.currentThread().getId() + "File Deleted Successfully/Not exists");

            return true;
        } catch (Exception e) {
            System.err.println(Thread.currentThread().getId() + " Error deleting the file");
        }
        return false;
    }


}
