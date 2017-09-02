package com.example.zqh.myapplication.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by zqh on 2017/9/2.
 */

public class MyServer {
    public  static ArrayList<Socket> socketList = new ArrayList<Socket>();
    public static final int PORT = 30000;
    public static void main(String[] args){
        try {
            ServerSocket ss = new ServerSocket(PORT);
            while (true){
                Socket s = ss.accept();
                socketList.add(s);
                new Thread(new ServerThread(s)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }
}
