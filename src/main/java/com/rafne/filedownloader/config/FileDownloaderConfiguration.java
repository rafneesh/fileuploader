package com.rafne.filedownloader.config;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Dsl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileDownloaderConfiguration {

    @Bean
    public AsyncHttpClient asyncHttpClient(){

        return Dsl.asyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().setReadTimeout(Integer.MAX_VALUE));
    }
}
