package com.zhiyiyunke.lqqq.share.view;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.zhiyiyunke.lqqq.share.R;

/**
 * 微信分享UI
 */
public class WeChatShareActivity extends ShareBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_share_layout);
    }

    @Override
    public int getScene() {
        return SendMessageToWX.Req.WXSceneSession;
    }

    @Override
    public String getShareTitle() {
        return "Title";
    }

    @Override
    public String getShareDescription() {
        return "Description";
    }

    @Override
    public String getShareText() {
        return "Content";
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public String getImagePath() {
        return "***/***/demo.jpeg";
    }

    @Override
    public String getImageUrl() {
        return "www.baidu.com";
    }

    @Override
    public byte[] getImageData() {
        return new byte[0];
    }

    @Override
    public String getMusicUrl() {
        return "www.baidu.com";
    }

    @Override
    public String getVideoUrl() {
        return "www.baidu.com";
    }

    @Override
    public String getWebPageUrl() {
        return "www.baidu.com";
    }
}
