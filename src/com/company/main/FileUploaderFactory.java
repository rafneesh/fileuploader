package com.company.main;

import com.company.misc.Protocol;
import com.company.service.FileUploaderService;
import com.company.service.implementation.HTTPSService;
import com.company.service.implementation.HTTPService;
import com.company.service.implementation.SFTPService;
import com.company.service.implementation.FTPService;

public class FileUploaderFactory {

    public FileUploaderService createFileUploaderService(Protocol protocol) {
        if (protocol == null)
            return null;

        switch (protocol) {
            case FTP:
                return new FTPService();
            case SFTP:
                return new SFTPService();
            case HTTP:
                return new HTTPService();
            case HTTPS:
                return new HTTPSService();
        }
        return null;
    }


}


