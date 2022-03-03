package com.company.service;

@FunctionalInterface
public interface FileUploaderService {

    public boolean write(String filePath, String destination);
}
