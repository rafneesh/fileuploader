package com.company.service.implementation;

import com.company.service.FileUploaderService;

public class FTPService implements FileUploaderService {
    @Override
    public boolean write(String filePath, String destination) {
        return false;
    }
}
