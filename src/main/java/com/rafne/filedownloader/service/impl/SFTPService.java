package com.rafne.filedownloader.service.impl;

import com.jcraft.jsch.*;
import com.rafne.filedownloader.service.FileDownloaderService;
import com.rafne.filedownloader.util.FileDownloaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SFTPService implements FileDownloaderService {

    static Logger log = Logger.getLogger(SFTPService.class.getName());

    @Autowired
    FileDownloaderUtils fileDownloaderUtils;

    @Override
    public Optional<File> download(String URL_LOCATION, String LOCAL_FILE) {

        long remoteFileSize = -1;

        long fileSize = -1;

        try {

            remoteFileSize = getFileSize(URL_LOCATION);

            if(remoteFileSize<=0){

                log.info("Thread Id:" +Thread.currentThread().getId() + " File on the server is empty, HTTP/HTTPS");
                throw new RuntimeException("File on the server is empty, unable to download");

            }

            log.info("Thread Id:" + Thread.currentThread().getId() + " File writing started for SFTP");

            //demo:password@test.rebex.net:22/pub/example/readme.txt
            JSch jsch = new JSch();
            URI uri = new URI(URL_LOCATION);

            String host = uri.getHost();
            int port = uri.getPort();
            String username = uri.getRawUserInfo().split(":")[0];
            String password = uri.getRawUserInfo().split(":")[1];
            String source = uri.getPath();

            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            Optional<File> file = Optional.of(fileDownloaderUtils.createFile(String.valueOf(Thread.currentThread().getId()),LOCAL_FILE,URL_LOCATION));

            sftpChannel.get(source, file.get().toString());
            SftpATTRS sftpATTRS = sftpChannel.lstat(source);
            sftpChannel.exit();

            log.info("Thread Id:" + Thread.currentThread().getId() + " File Size SFTP:" + sftpATTRS.getSize());


            log.info("Thread Id:" + Thread.currentThread().getId() + " File written successfully SFTP");
            return file;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return Optional.empty();
    }

    @Override
    public long getFileSize(String path) {

        Channel channel = null;
        ChannelSftp sftpChannel = null;

        try {
            JSch jsch = new JSch();
            URI uri = new URI(path);

            String host = uri.getHost();
            int port = uri.getPort();

            String username = uri.getRawUserInfo().split(":")[0];
            String password = uri.getRawUserInfo().split(":")[1];
            String source = uri.getPath();

            Session session = null;

            session = jsch.getSession(username, host, port);

            session.setPassword(password);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;
            SftpATTRS sftpATTRS = sftpChannel.lstat(source);

            return sftpATTRS.getSize();

        } catch (Exception e) {
            log.warning(e.toString());
            throw new RuntimeException(e);
        } finally {
            sftpChannel.disconnect();
            sftpChannel.exit();
        }

    }
}
