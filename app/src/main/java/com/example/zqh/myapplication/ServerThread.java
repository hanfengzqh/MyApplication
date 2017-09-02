package com.example.zqh.myapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by zqh on 2017/9/2.
 */

public class ServerThread implements Runnable {
    public Socket s = null;
    public BufferedReader br = null;

    public ServerThread(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream(), Contact.CHARACTER));
    }

    @Override
    public void run() {
        String content = null;
        while ((content = readFromClient()) != null) {
            ArrayList<Socket> socketList = MyServer.socketList;
            for (Socket s : socketList){
                try {
                    OutputStream os = s.getOutputStream();
                    os.write((content+"\n").getBytes(Contact.CHARACTER));
                } catch (IOException e) {
                    e.printStackTrace();
                    socketList.remove(s);
                }
            }
        }

    }

    private String readFromClient() {

        try {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            //抛出异常，意味着连接失败或客户端已经关闭
            MyServer.socketList.remove(s);
            return null;
        }
    }
}
