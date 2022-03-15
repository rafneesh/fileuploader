package com.rafne.filedownloader.proxy;

import com.rafne.filedownloader.component.FileDownloaderFactory;
import com.rafne.filedownloader.component.FileDownloader;
import com.rafne.filedownloader.enums.Protocol;
import com.rafne.filedownloader.service.impl.FTPService;
import com.rafne.filedownloader.service.impl.HTTPSService;
import com.rafne.filedownloader.service.impl.SFTPService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FileDownloaderTests {

    @InjectMocks
    FileDownloader fileDownloader;

    @Mock
    FileDownloaderFactory factory;

    @Mock
    HTTPSService httpsService;

    @Mock
    SFTPService sftpService;

    @Mock
    FTPService ftpService;

    @Before
    public void prepare() {
        // "manual" call to @PostConstruct which will now work as expected
        fileDownloader.init();
    }

    @Test
    void httpFileDownloadSuccess() {

        ReflectionTestUtils.setField(fileDownloader,
                "destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(httpsService));

        Mockito.when(httpsService.download(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(new File("/")));

        var result = fileDownloader.downloadFile("http://speedtest.tele2.net/1MB.zip");

        Assert.assertTrue(result);

    }

    @Test
    void httpFileDownloadFailureForEmpty() {

        ReflectionTestUtils.setField(fileDownloader,
                "destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(httpsService));

        Mockito.when(httpsService.download(Mockito.anyString(), Mockito.anyString())).thenThrow(new RuntimeException("File on the server is empty"));

        var result = fileDownloader.downloadFile("http://speedtest.tele2.net/1MB.zip");

        Assert.assertFalse(result);

    }

    @Test
    void httpFileDownloadFailureForSizeMissMatch() {

        ReflectionTestUtils.setField(fileDownloader,
                "destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(httpsService));

        Mockito.when(httpsService.download(Mockito.anyString(), Mockito.anyString())).thenThrow(new RuntimeException("File size miss matches"));

        var result = fileDownloader.downloadFile("http://speedtest.tele2.net/1MB.zip");

        Assert.assertFalse(result);

    }

    @Test
    void httpFileDownloadFailureForTransfer() {

        ReflectionTestUtils.setField(fileDownloader,
                "destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(httpsService));

        Mockito.when(httpsService.download(Mockito.anyString(), Mockito.anyString())).thenThrow(new RuntimeException("File download fails "));

        var result = fileDownloader.downloadFile("http://speedtest.tele2.net/1MB.zip");

        Assert.assertFalse(result);

    }

    @Test
    void ftpFileDownloadSuccess() {

        ReflectionTestUtils.setField(fileDownloader,
                "destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(ftpService));

        Mockito.when(ftpService.download(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(new File("/")));

        var result = fileDownloader.downloadFile("http://speedtest.tele2.net/1MB.zip");

        Assert.assertTrue(result);

    }

    @Test
    void ftpFileDownloadFailureForEmpty() {

        ReflectionTestUtils.setField(fileDownloader,
                "destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(ftpService));

        Mockito.when(ftpService.download(Mockito.anyString(), Mockito.anyString())).thenThrow(new RuntimeException("File on the server is empty"));

        var result = fileDownloader.downloadFile("http://speedtest.tele2.net/1MB.zip");

        Assert.assertFalse(result);

    }

    @Test
    void ftpFileDownloadFailureForSizeMissMatch() {

        ReflectionTestUtils.setField(fileDownloader,
                "destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(ftpService));

        Mockito.when(ftpService.download(Mockito.anyString(), Mockito.anyString())).thenThrow(new RuntimeException("File size miss matches"));

        var result = fileDownloader.downloadFile("http://speedtest.tele2.net/1MB.zip");

        Assert.assertFalse(result);

    }

    @Test
    void ftpFileDownloadFailureForTransfer() {

        ReflectionTestUtils.setField(fileDownloader,
                "destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(ftpService));

        Mockito.when(ftpService.download(Mockito.anyString(), Mockito.anyString())).thenThrow(new RuntimeException("File download fails "));

        var result = fileDownloader.downloadFile("http://speedtest.tele2.net/1MB.zip");

        Assert.assertFalse(result);

    }


    @Test
    void sftpFileDownloadSuccess() {

        ReflectionTestUtils.setField(fileDownloader,
                "destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(sftpService));

        Mockito.when(sftpService.download(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(new File("/")));

        var result = fileDownloader.downloadFile("sftp://demo:password@test.rebex.net:22/pub/example/KeyGeneratorSmall.png");

        Assert.assertTrue(result);

    }

    @Test
    void sftpFileDownloadFailureForEmpty() {

        ReflectionTestUtils.setField(fileDownloader,
                "destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(sftpService));

        Mockito.when(sftpService.download(Mockito.anyString(), Mockito.anyString())).thenThrow(new RuntimeException("File on the server is empty"));

        var result = fileDownloader.downloadFile("sftp://demo:password@test.rebex.net:22/pub/example/KeyGeneratorSmall.png");

        Assert.assertFalse(result);

    }

    @Test
    void sftpFileDownloadFailureForSizeMissMatch() {

        ReflectionTestUtils.setField(fileDownloader,
                "destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(sftpService));

        Mockito.when(sftpService.download(Mockito.anyString(), Mockito.anyString())).thenThrow(new RuntimeException("File size miss matches"));

        var result = fileDownloader.downloadFile("sftp://demo:password@test.rebex.net:22/pub/example/KeyGeneratorSmall.png");

        Assert.assertFalse(result);

    }

    @Test
    void sftpFileDownloadFailureForTransfer() {

        ReflectionTestUtils.setField(fileDownloader,
                "destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(sftpService));

        Mockito.when(sftpService.download(Mockito.anyString(), Mockito.anyString())).thenThrow(new RuntimeException("File download fails "));

        var result = fileDownloader.downloadFile("sftp://demo:password@test.rebex.net:22/pub/example/KeyGeneratorSmall.png");

        Assert.assertFalse(result);

    }

    @Test
    void unknownProtocolFileDownloadFailure() {

        boolean result = false;


        Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenThrow(new IllegalArgumentException("No enum constant com.rafne.filedownloader.enums.Protocol.UNSUPPORTED"));

        result = fileDownloader.downloadFile("unknown://demo:password@test.rebex.net:22/pub/example/KeyGeneratorSmall.png");

        Assert.assertFalse(result);

    }


}
