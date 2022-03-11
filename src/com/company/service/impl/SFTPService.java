package com.company.service.impl;

import com.company.service.FileUploaderService;
import com.jcraft.jsch.*;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

public class SFTPService implements FileUploaderService {
    @Override
    public Optional<File> write(String URL_LOCATION, String LOCAL_FILE) {

        try {

            File file = new File(LOCAL_FILE + URL_LOCATION.split("/")[URL_LOCATION.split("/").length - 1]);

            System.out.println(Thread.currentThread().getId()+" File writing started for SFTP");

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
            sftpChannel.get(source, file.toString());
            sftpChannel.exit();

            System.out.println(Thread.currentThread().getId()+" File written successfully SFTP");
            return Optional.of(file);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return Optional.empty();
    }

    @Override
    public long getFileSize(URL url) {
        return 0;
    }
}
