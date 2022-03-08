package com.company.service;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

public interface FileUploaderService {

    public Optional<File> write(String filePath, String destination);

    public long getFileSize(URL url);
}
