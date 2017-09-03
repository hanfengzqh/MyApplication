package com.example.zqh.myapplication.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by zqh on 2017/9/3.
 */

public class UrlTest {

    public void urlRequest() throws IOException {
        URL url = new URL("");
        url.getFile();
        url.getHost();
        url.getPort();
        url.getProtocol();
        URLConnection urlConnection = url.openConnection();
        urlConnection.setAllowUserInteraction(true);
        OutputStream outputStream = urlConnection.getOutputStream();

        InputStream inputStream = url.openStream();
        int read = 0;
        byte[] b = new byte[1024];
        while ((read = inputStream.read()) != -1){
            outputStream.write(b,0,read);
        }
        outputStream.close();
        inputStream.close();


    }
}
