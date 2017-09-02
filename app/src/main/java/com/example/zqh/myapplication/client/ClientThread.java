package com.example.zqh.myapplication.client;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.zqh.myapplication.util.Contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by zqh on 2017/9/2.
 */

public class ClientThread implements Runnable {

    public Handler handler;
    public Socket s;
    public BufferedReader br;
    public OutputStream os;
    public Handler reHandler;

    public ClientThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {

        try {
            s = new Socket("192.168.1.88", 30000);
            br = new BufferedReader(new InputStreamReader(s.getInputStream(), Contact.CHARACTER));
            os = s.getOutputStream();
            //启动一条子线程来读取服务器的数据
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    String content = null;
                    try {
                        //不断读取Socket输入流中的数据
                        while ((content = br.readLine()) != null) {
                            //每当读取到来自服务器的数据时，发送消息通知程序界面显示该数据。
                            Message msg = new Message();
                            msg.what = 0x123;
                            msg.obj = content;
                            handler.sendMessage(msg);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            //为当前线程初始化Looper
            Looper.prepare();
            //创建Handler对象
            reHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    //接收到Ui线程里面输入的数据
                    if (msg.what == 0x345) {
                        try {
                            os.write(msg.obj.toString().getBytes(Contact.CHARACTER));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            //启动Looper
            Looper.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
