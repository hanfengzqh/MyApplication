package com.example.zqh.myapplication.client;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zqh.myapplication.R;

public class MainActivity extends AppCompatActivity {
    private Button send;
    private EditText input;
    private TextView show;
    private Handler handler;
    private ClientThread clientThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = (Button) findViewById(R.id.bt_send);
        input = (EditText) findViewById(R.id.input);
        show = (TextView) findViewById(R.id.tv_show);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x123){
                show.append(msg.obj.toString());
                }
            }
        };
        clientThread = new ClientThread(handler);
        //客户端创建网络连接，读取来自服务器的数据
        new Thread(clientThread).start();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                msg.what = 0x345;
                msg.obj = input.getText().toString();
                clientThread.reHandler.sendMessage(msg);
                //清空文本框
                input.setText("");


            }
        });
    }
}
