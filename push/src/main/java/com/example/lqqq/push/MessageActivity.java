package com.example.lqqq.push;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;

/**
 * 消息接收显示
 */
public class MessageActivity extends AppCompatActivity {

    private TextView pushTitle,pushContent,pushExtra,pushMsgID;//标题，内容，附加信息，消息ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        init();
    }
    private void init(){
        Bundle bundle = getIntent().getExtras();
        pushTitle = findViewById(R.id.push_title);
        pushContent = findViewById(R.id.push_content);
        pushExtra = findViewById(R.id.push_extra);
        pushMsgID = findViewById(R.id.push_msg_id);

        switch (getIntent().getAction()){
            //通知消息展示
            case JPushInterface.ACTION_NOTIFICATION_OPENED:
                String openTitle = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);//标题
                String openAlert = bundle.getString(JPushInterface.EXTRA_ALERT);//内容
                String openExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);//附加字段，json格式
                int  openID = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);//通知栏的Notification ID，可以用于清除Notification;如果服务端内容（alert）字段为空，则notification id 为0
                String openMsgID = bundle.getString(JPushInterface.EXTRA_MSG_ID);//消息ID

                pushTitle.setText(openTitle);
                pushContent.setText(openAlert);
                pushExtra.setText(openExtra);
                pushMsgID.setText(openMsgID);
                break;
                //自定义消息展示
            case JPushInterface.ACTION_MESSAGE_RECEIVED:
                String title = bundle.getString(JPushInterface.EXTRA_TITLE);//标题
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);//内容
                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);//附加字段，json格式
                String msgID = bundle.getString(JPushInterface.EXTRA_MSG_ID);//消息ID

                pushTitle.setText(title);
                pushContent.setText(message);
                pushExtra.setText(extra);
                pushMsgID.setText(msgID);
                break;
                default:
        }
    }
}
