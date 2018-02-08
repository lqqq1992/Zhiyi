package com.example.lqqq.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

/**
 * 推送接收
 * Created by LQQQ1 on 2018/2/6.
 */

public class PushReceiver extends BroadcastReceiver {
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("=======>>>>>>>","收到推送消息");
        this.context = context;
        Bundle bundle = intent.getExtras();
        switch (intent.getAction()){
            //"cn.jpush.android.intent.REGISTRATION"
            case JPushInterface.ACTION_REGISTRATION_ID:
                //SDK 向 JPush Server 注册所得到的注册 ID
                getRegistrationID(bundle);
                break;
            //"cn.jpush.android.intent.MESSAGE_RECEIVED"
            case JPushInterface.ACTION_MESSAGE_RECEIVED:
                //收到了自定义消息 Push 。SDK 对自定义消息，只是传递，不会有任何界面上的展示。
                getMessage(bundle);
                break;
            //"cn.jpush.android.intent.NOTIFICATION_RECEIVED"
            case JPushInterface.ACTION_NOTIFICATION_RECEIVED:
                //收到了通知 Push。如果通知的内容为空，则在通知栏上不会展示通知。
                // 但是，这个广播 Intent 还是会有。开发者可以取到通知内容外的其他信息。
                getNotification(bundle);
                break;
            //"cn.jpush.android.intent.NOTIFICATION_OPENED"
            case JPushInterface.ACTION_NOTIFICATION_OPENED:
                //用户点击通知后的操作，不接收默认打开主界面
                openNotification(bundle);
                break;
            //"cn.jpush.android.intent.NOTIFICATION_CLICK_ACTION"
            case JPushInterface.ACTION_NOTIFICATION_CLICK_ACTION:
                //用户点击了通知栏中自定义的按钮
                break;
            case JPushInterface.ACTION_CONNECTION_CHANGE:
                //JPush 服务的连接状态发生变化。（注：不是指 Android 系统的网络连接状态。）
                getConnectionState(bundle);
                break;
                default:
        }
    }

    /**
     * 接收注册ID
     * @param bundle
     */
    private void getRegistrationID(Bundle bundle){
        String registrationID = bundle.getString(JPushInterface.ACTION_REGISTRATION_ID);
        Log.e("--->>>registrationID",registrationID+"");
    }

    /**
     * 接收自定义消息
     * @param bundle
     */
    private void getMessage(Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);//标题
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);//内容
        String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);//附加字段，json格式
        String msgID = bundle.getString(JPushInterface.EXTRA_MSG_ID);//消息ID
        Log.e("--->>>message",title+";"+message+";"+extra
                +";"+msgID);
//        startActivity(bundle,JPushInterface.ACTION_MESSAGE_RECEIVED);
        setNotificationBar(title,message,bundle);
    }

    /**
     * 接收通知
     * @param bundle
     */
    private void getNotification(Bundle bundle){
        String notiTitle = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);//标题
        String alert = bundle.getString(JPushInterface.EXTRA_ALERT);//内容
        String notiExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);//附加字段，json格式
        int notiID = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);//通知栏的Notification ID，可以用于清除Notification;如果服务端内容（alert）字段为空，则notification id 为0
        String path = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH);//富媒体通知推送下载的HTML的文件路径,用于展现WebView
        String res = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_RES);//富媒体通知推送下载的图片资源的文件名,多个文件名用 “，” 分开。 与 “JPushInterface.EXTRA_RICHPUSH_HTML_PATH” 位于同一个路径
        String notiMsgID = bundle.getString(JPushInterface.EXTRA_MSG_ID);//消息ID
        String bigText = bundle.getString(JPushInterface.EXTRA_BIG_TEXT);//大文本通知样式中大文本的内容
        String bigPath = bundle.getString(JPushInterface.EXTRA_BIG_PIC_PATH);//大图片通知样式中大图片的路径/地址
        String inBox = bundle.getString(JPushInterface.EXTRA_INBOX);//收件箱通知样式中收件箱的内容,json格式
        String priropity = bundle.getString(JPushInterface.EXTRA_NOTI_PRIORITY);//通知的优先级
        String category = bundle.getString(JPushInterface.EXTRA_NOTI_CATEGORY);//通知分类
        Log.e("--->>>getNotification",notiTitle+";"+alert+";"+notiExtra+";"+notiMsgID);
    }

    /**
     * 打开通知栏
     * @param bundle
     */
    private void openNotification(Bundle bundle){
        String openTitle = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);//标题
        String openAlert = bundle.getString(JPushInterface.EXTRA_ALERT);//内容
        String openExtra = bundle.getString(JPushInterface.EXTRA_EXTRA);//附加字段，json格式
        int  openID = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);//通知栏的Notification ID，可以用于清除Notification;如果服务端内容（alert）字段为空，则notification id 为0
        String openMsgID = bundle.getString(JPushInterface.EXTRA_MSG_ID);//消息ID
        Log.e("--->>>open",openTitle+";"+openAlert+";"+openExtra+";"+openID+";"+openMsgID);
        startActivity(bundle,JPushInterface.ACTION_NOTIFICATION_OPENED);
    }

    /**
     * 获取连接状态
     * @param bundle
     */
    private void getConnectionState(Bundle bundle){
        boolean connection = bundle.getBoolean(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        Log.e("--->>>state",connection+"");
    }

    /**
     * 打开消息显示界面
     * @param bundle
     * @param action
     */
    private void startActivity(Bundle bundle,String action){
        Intent intent = new Intent(context,MessageActivity.class);
        intent.setAction(action);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 接收自定义消息后，创建通知栏
     * @param title 通知栏标题
     * @param summary 通知栏摘要
     * @param bundle 通知内容
     */
    private void setNotificationBar(String title,String summary,Bundle bundle){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"push");

        builder.setContentTitle(title)
                .setContentText(summary)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true);

        Intent intent = new Intent(context,MessageActivity.class);
        intent.setAction(JPushInterface.ACTION_MESSAGE_RECEIVED);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        manager.notify(1,notification);
    }
}
