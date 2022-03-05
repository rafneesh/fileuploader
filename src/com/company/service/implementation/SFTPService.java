package com.company.service.implementation;

import com.company.service.FileUploaderService;
import com.jcraft.jsch.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class SFTPService implements FileUploaderService {
    @Override
    public boolean write(String URL_LOCATION, String LOCAL_FILE) {

        try {

            File file = new File(LOCAL_FILE + URL_LOCATION.split("/")[URL_LOCATION.split("/").length - 1]);

            System.out.println("File writing started for SFTP");

            //demo:password@test.rebex.net:22/pub/example/readme.txt
            JSch jsch = new JSch();
            URI uri = new URI(URL_LOCATION);


            //String host = URL_LOCATION.split("//")[1].split("/")[0].split("@")[1].split(":")[0];
            String host = uri.getHost();
            int port = uri.getPort();
            String username = uri.getRawUserInfo().split(":")[0];
            String password = uri.getRawUserInfo().split(":")[1];
            String source = uri.getPath();

            System.out.println("Host:" + host);
            System.out.println("Port:" + port);
            System.out.println("Username:" + username);
            System.out.println("Password:" + password);
            System.out.println("Source:" + source);

            System.out.println(URL_LOCATION);

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

            return true;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public int getFileSize(URL url) {
        return 0;
    }
}
