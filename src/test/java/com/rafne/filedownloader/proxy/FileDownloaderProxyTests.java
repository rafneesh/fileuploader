package com.rafne.filedownloader.proxy;

import com.rafne.filedownloader.component.FileDownloaderFactory;
import com.rafne.filedownloader.service.impl.FTPService;
import com.rafne.filedownloader.service.impl.HTTPSService;
import com.rafne.filedownloader.service.impl.SFTPService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FileDownloaderProxyTests {

	@InjectMocks
	FileDownloaderProxy fileDownloaderProxy;

	@Mock
	FileDownloaderFactory factory;

	@Mock
	HTTPSService httpsService;

	@Mock
	SFTPService sftpService;

	@Mock
	FTPService ftpService;

	@Test
	void httpFileDownloadSuccess() {

		ReflectionTestUtils.setField(fileDownloaderProxy,
				"destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

		Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(httpsService));

		Mockito.when(httpsService.download(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.of(new File("/")));

		var result =  fileDownloaderProxy.downloadFile("http://speedtest.tele2.net/1MB.zip");

		Assert.assertTrue(result);

	}

	@Test
	void httpFileDownloadFailure() {

		ReflectionTestUtils.setField(fileDownloaderProxy,
				"destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

		Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(httpsService));

		Mockito.when(httpsService.download(Mockito.anyString(),Mockito.anyString())).thenThrow(new RuntimeException(""));

		var result =  fileDownloaderProxy.downloadFile("http://speedtest.tele2.net/1MB.zip");

		Assert.assertFalse(result);

	}

	@Test
	void ftpFileDownloadSuccess() {

		ReflectionTestUtils.setField(fileDownloaderProxy,
				"destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

		Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(ftpService));

		Mockito.when(ftpService.download(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.of(new File("/")));

		var result =  fileDownloaderProxy.downloadFile("http://speedtest.tele2.net/1MB.zip");

		Assert.assertTrue(result);

	}

	@Test
	void ftpFileDownloadFailure() {

		ReflectionTestUtils.setField(fileDownloaderProxy,
				"destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

		Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(ftpService));

		Mockito.when(ftpService.download(Mockito.anyString(),Mockito.anyString())).thenThrow(new RuntimeException(""));

		var result =  fileDownloaderProxy.downloadFile("http://speedtest.tele2.net/1MB.zip");

		Assert.assertFalse(result);

	}

	@Test
	void sftpFileDownloadSuccess() {

		ReflectionTestUtils.setField(fileDownloaderProxy,
				"destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

		Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(sftpService));

		Mockito.when(sftpService.download(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.of(new File("/")));

		var result =  fileDownloaderProxy.downloadFile("sftp://demo:password@test.rebex.net:22/pub/example/KeyGeneratorSmall.png");

		Assert.assertTrue(result);

	}

	@Test
	void sftpFileDownloadFailure() {

		ReflectionTestUtils.setField(fileDownloaderProxy,
				"destinationDirectory", "C:\\Users\\muhammed.rafneesh\\Documents\\Destination\\");

		Mockito.when(factory.getFileDownloaderService(Mockito.anyString())).thenReturn(Optional.of(sftpService));

		Mockito.when(sftpService.download(Mockito.anyString(),Mockito.anyString())).thenThrow(new RuntimeException(""));

		var result =  fileDownloaderProxy.downloadFile("sftp://demo:password@test.rebex.net:22/pub/example/KeyGeneratorSmall.png");

		Assert.assertFalse(result);

	}

}
