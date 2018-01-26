package com.zhiyiyunke.lqqq.share.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zhiyiyunke.lqqq.share.R;
import com.zhiyiyunke.lqqq.share.bean.Constant;
import com.zhiyiyunke.lqqq.share.model.Share;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button shareText,shareWeb,shareImage,shareImages,shareAudio,shareVedio,shareTo;
    private Share share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        share = new Share(this);
        init();
    }
    private void init(){
        shareText = findViewById(R.id.share_text);
        shareWeb = findViewById(R.id.share_web);
        shareImage = findViewById(R.id.share_image);
        shareImages = findViewById(R.id.share_images);
        shareAudio = findViewById(R.id.share_audio);
        shareVedio = findViewById(R.id.share_vedio);
        shareTo = findViewById(R.id.share_to);

        shareText.setOnClickListener(this);
        shareWeb.setOnClickListener(this);
        shareImage.setOnClickListener(this);
        shareImages.setOnClickListener(this);
        shareAudio.setOnClickListener(this);
        shareVedio.setOnClickListener(this);
        shareTo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_text:
                share.shareText("分享标题","这就是我要分享的内容");
                break;
            case R.id.share_web:
//                share.shareWeb("http://www.baidu.com");
                break;
            case R.id.share_image:
                share.shareImagePrepare(Constant.ACTIVITY_RESULT_CODE_OPEN_ALBUM);
                break;
            case R.id.share_images:
                share.shareImagePrepare(Constant.ACTIVITY_RESULT_CODE_OPEN_ALBUM_MORE);
                break;
            case R.id.share_audio:

                break;
            case R.id.share_vedio:

                break;
            case R.id.share_to:
                share.shareTo();
                break;
                default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData() != null){
            switch (requestCode){
                case Constant.ACTIVITY_RESULT_CODE_OPEN_ALBUM:
                    share.shareImage(data.getData());
                    break;
                case Constant.ACTIVITY_RESULT_CODE_OPEN_ALBUM_MORE:
                    ArrayList<Uri> uris = new ArrayList<>();
                    uris.add(data.getData());
                    uris.add(data.getData());
                    share.shareImages(uris);
                    break;
                    default:
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case Constant.PERMISSION_REQUEST_CODE_OPEN_ALBUM:
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent,Constant.ACTIVITY_RESULT_CODE_OPEN_ALBUM);
                break;
        }
    }

}
