package com.zhiyiyunke.lqqq.share.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.zhiyiyunke.lqqq.share.R;
import com.zhiyiyunke.lqqq.share.presenter.QQSharePresenter;

import java.io.File;
import java.util.ArrayList;

/**
 * 微信分享UI
 */
public class ShareActivity extends ShareBaseActivity implements View.OnClickListener{

    private Button shareTextAndImage,shareImage,shareAudio,shareApp,shareToZone;
    private QQSharePresenter qqSharePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_share_layout);
        qqSharePresenter = new QQSharePresenter(this);
        init();
    }

    private void init(){
        shareTextAndImage = findViewById(R.id.share_text_and_image);
        shareImage = findViewById(R.id.share_image_qq);
        shareAudio = findViewById(R.id.share_audio_qq);
        shareApp = findViewById(R.id.share_app);
        shareToZone = findViewById(R.id.share_to_zone);

        shareTextAndImage.setOnClickListener(this);
        shareImage.setOnClickListener(this);
        shareAudio.setOnClickListener(this);
        shareApp.setOnClickListener(this);
        shareToZone.setOnClickListener(this);
    }

    @Override
    public int getScene() {
        return SendMessageToWX.Req.WXSceneSession;
    }

    @Override
    public String getShareTitle() {
        return "风景图片";
    }

    @Override
    public String getShareDescription() {
        return "或许有音乐";
    }

    @Override
    public String getShareText() {
        return "这就是要分享的内容";
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public String getImagePath() {
        File file = Environment.getExternalStorageDirectory();
        File newFile = new File(file,"shareImage/share.jpg");
        return newFile.getPath();
    }

    @Override
    public String getImageUrl() {
        return "http://img2.3lian.com/2014/f5/63/d/16.jpg";
    }

    @Override
    public ArrayList<String> getImageUrls() {
        ArrayList<String> imageUrls = new ArrayList<>();
        for(int i = 0;i<2;i++){
            imageUrls.add(getImageUrl());
        }
        return imageUrls;
    }

    @Override
    public byte[] getImageData() {
        return new byte[0];
    }

    @Override
    public String getMusicUrl() {
        return "http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3";
    }

    @Override
    public String getVideoUrl() {
        return "https://www.baidu.com";
    }

    @Override
    public String getWebPageUrl() {
        return "https://www.baidu.com";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_text_and_image:
                qqSharePresenter.shareTextAndImage();
                break;
            case R.id.share_image_qq:
                qqSharePresenter.shareImage();
                break;
            case R.id.share_audio_qq:
                qqSharePresenter.shareMusic();
                break;
            case R.id.share_app:
                qqSharePresenter.shareApp();
                break;
            case R.id.share_to_zone:
                qqSharePresenter.shareToQQZone();
                break;

        }
    }
}
