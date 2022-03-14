package com.rafne.filedownloader;

import com.rafne.filedownloader.proxy.FileDownloaderProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class FileDownloaderApplication implements CommandLineRunner {


	static{

		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT %1$tL] [%4$-7s] %5$s %n");

	}

	private final static Logger log = Logger.getLogger(FileDownloaderApplication.class.getName());

	@Autowired
	FileDownloaderProxy fileDownloaderProxy;

	public static void main(String[] args) {

		SpringApplication.run(FileDownloaderApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		log.info("FileUploaderFactory: STARTS");

		ExecutorService executor = Executors.newCachedThreadPool();

		Arrays.asList(args).forEach((item)-> executor.execute(()-> fileDownloaderProxy.downloadFile(item)));

		executor.shutdown();
		// Wait until all threads are finish
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			log.warning("FileUploaderFactory: InterruptedException => "+e);
		}

		log.info("FileUploaderFactory: Finished all threads: ENDS");

		System.exit(0);

	}
}
