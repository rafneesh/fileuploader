package com.company.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public interface FileUploaderService {

    public boolean write(String filePath, String destination);

    public int getFileSize(URL url);
}
