package com.phx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@RestController
public class SeaWeedFS {


    // seaweed
    @RequestMapping(value = "/seaweed", method = RequestMethod.GET)
    public String seaweed(String signature, String timestamp, String nonce, String echostr) throws IOException {





        return echostr;
    }


    public static void main(String[] args) throws IOException {
        /*FileSource fileSource = new FileSource();*//*

        // SeaweedFS master server host
        fileSource.setHost("140.143.26.129");
        // SeaweedFS master server port
        fileSource.setPort(9333);
        // Startup manager and listens for the change
        fileSource.startup();

        File file = new File("/data/ww.mp4");
        FileInputStream fis = new FileInputStream(file);

        FileTemplate template = new FileTemplate(fileSource.getConnection());
        System.out.println(template.saveFileByStream(file.getName(), fis).toString());*/
    }


}
