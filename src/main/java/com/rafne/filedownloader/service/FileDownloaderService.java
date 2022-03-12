package com.rafne.filedownloader.service;

import java.io.File;
import java.util.Optional;

public interface FileDownloaderService {

    public Optional<File> write(String filePath, String destination);

    public long getFileSize(String filePath);
}
