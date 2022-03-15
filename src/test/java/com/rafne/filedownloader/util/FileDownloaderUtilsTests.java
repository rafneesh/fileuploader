package com.rafne.filedownloader.util;

import com.rafne.filedownloader.component.FileDownloader;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FileDownloaderUtilsTests {

    @Value("${location.path}")
    String destinationDirectory;

    @InjectMocks
    FileDownloaderUtils fileDownloaderUtils;

    @Mock
    File file;

    @Test
    void deleteFileSuccess() {

        try {
            file = File.createTempFile("tmp", null);
            Assert.assertTrue(fileDownloaderUtils.deleteFile(file));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    @Test
    void deleteFileFailure() {


        Mockito.when(file.exists()).thenThrow(new SecurityException());
        Assert.assertFalse(fileDownloaderUtils.deleteFile(file));


    }


}
