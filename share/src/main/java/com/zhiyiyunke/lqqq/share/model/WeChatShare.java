package com.zhiyiyunke.lqqq.share.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.zhiyiyunke.lqqq.share.R;
import com.zhiyiyunke.lqqq.share.utils.BitmapUtil;
import com.zhiyiyunke.lqqq.share.utils.WeChatUtil;
import com.zhiyiyunke.lqqq.share.view.ShareBaseActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 微信官方分享model
 * Created by LQQQ1 on 2018/1/24.
 */

public class WeChatShare implements WeChatShareBase {

    private ShareBaseActivity shareView;
    private int scene;
    private IWXAPI api;
    private static final int THUMB_SIZE = 150;

    public WeChatShare(ShareBaseActivity shareView, int scene){
        this.shareView = shareView;
        this.scene = scene;
        this.api = shareView.getApi();
    }
    @Override
    public void shareText(String text) {

        //1.创建一个WXTextObject对象
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;

        //2.创建一个WXMediaMessage对象
        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = textObject;

        //3.将分享内容传递给WXMediaMessage对象
        message.description = text;

        //4.创建一个rep对象，设置标签、内容、目标
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = WeChatUtil.buildTransaction("text");//用于标识请求，回到中会用到
        req.message = message;//分享内容
        //分享目标场景,有以下3个值：
        //微信聊天      SendMessageToWX.Req.WXSceneSession = 0
        //微信朋友圈     SendMessageToWX.Req.WXSceneTimeline = 1
        //微信收藏      SendMessageToWX.Req.WXSceneFavorite = 2
        req.scene = scene;

        //5.发送分享
        api.sendReq(req);
    }

    @Override
    public void shareImage(Bitmap bitmap) {
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        // 比文本分享多出的一个步骤，设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = WeChatUtil.buildTransaction("image");
        req.message = msg;
        req.scene = scene;
//        req.openId = getOpenId();
        api.sendReq(req);
    }

    @Override
    public void shareImage(String path) {
        File file = new File(path);
        if (!file.exists()) {
            String tip = "File not found!";
            Toast.makeText(shareView, tip + " path = " + path, Toast.LENGTH_LONG).show();
            return;
        }

        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(path);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap bmp = BitmapFactory.decodeFile(path);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = WeChatUtil.buildTransaction("image");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }

    @Override
    public void shareImage(byte[] imageBytes) {
        WXImageObject imgObj = new WXImageObject(imageBytes);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = WeChatUtil.buildTransaction("image");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }

    @Override
    public void shareImageFromUrl(String url) {
        WXImageObject imageObject = new WXImageObject();
        imageObject.setImagePath(url);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;

        InputStream in = null;
        try {
            in = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bmp = BitmapFactory.decodeStream(in);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = WeChatUtil.buildTransaction("image");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }

    @Override
    public void shareMusic(String title,String description,String url) {
        WXMusicObject musicObj = new WXMusicObject();
        musicObj.musicUrl=url;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = musicObj;
        msg.title = title;
        msg.description = description;

        Bitmap thumb = BitmapFactory.decodeResource(shareView.getResources(), R.mipmap.ic_launcher_round);
        msg.thumbData = BitmapUtil.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = WeChatUtil.buildTransaction("music");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }

    @Override
    public void shareVideo(String title,String description,String url) {
        WXVideoObject videoObject = new WXVideoObject();
        videoObject.videoUrl = url;

        WXMediaMessage msg = new WXMediaMessage(videoObject);
        msg.title = title;
        msg.description = title;

        Bitmap thumb = BitmapFactory.decodeResource(shareView.getResources(), R.mipmap.ic_launcher_round);
        msg.thumbData = BitmapUtil.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = WeChatUtil.buildTransaction("video");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }

    @Override
    public void shareWeb(String title,String description,String url) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = title;

        Bitmap thumb = BitmapFactory.decodeResource(shareView.getResources(), R.mipmap.ic_launcher_round);
        msg.thumbData = BitmapUtil.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = WeChatUtil.buildTransaction("webpage");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }

    @Override
    public void shareImageWithNotes(String title, String description, Bitmap bitmap) {

    }


}
