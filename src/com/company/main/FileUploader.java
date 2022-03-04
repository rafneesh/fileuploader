package com.company.main;

import com.company.main.proxy.FileUploaderProxy;

import java.util.*;


public class FileUploader {

     public static void main(String[] args) {

        System.out.println("FileUploaderFactory: main()");

        Arrays.asList(args).forEach((item)-> FileUploaderProxy.writeFile(item));


    }
}
