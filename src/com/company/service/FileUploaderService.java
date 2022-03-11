package com.company.service;

import java.io.File;
import java.net.URL;
import java.util.Optional;

public interface FileUploaderService {

    public Optional<File> write(String filePath, String destination);

    public long getFileSize(URL url);
}
