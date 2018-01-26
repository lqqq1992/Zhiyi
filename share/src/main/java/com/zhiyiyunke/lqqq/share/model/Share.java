package com.zhiyiyunke.lqqq.share.model;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;

import com.zhiyiyunke.lqqq.share.bean.Constant;
import com.zhiyiyunke.lqqq.share.bean.ShareTargetMessage;

import java.io.File;
import java.util.ArrayList;

/**
 * android原生分享model
 * Created by LQQQ1 on 2018/1/23.
 */

public class Share implements ShareBase {

    private Context context;
    private Activity activity;
    private Intent intent;
    private final String TITLE = "分享";

    public Share(Context context) {
        this.context = context;
        if(context instanceof Activity){
            this.activity = (Activity) context;
        }
    }

    @Override
    public Intent setComponents() {
        ArrayList<Intent> intents = new ArrayList<>();

        Intent it1 = new Intent(intent);
        it1.setPackage(ShareTargetMessage.WECHAT_P);
        it1.setClassName(ShareTargetMessage.WECHAT_P, ShareTargetMessage.WEMOMENTS_N);
        intents.add(it1);

        Intent it2 = new Intent(intent);
        it2.setPackage(ShareTargetMessage.MOBILEQQ_P);
        it2.setClassName(ShareTargetMessage.MOBILEQQ_P, ShareTargetMessage.MOBILEQQ_N);
        intents.add(it2);

        // 选择分享时的标题
        Intent chooserIntent = Intent.createChooser(intents.remove(0), "选择分享");
        if (chooserIntent == null) {
            return null;
        }
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toArray(new Parcelable[]{}));

        return chooserIntent;
    }

    @Override
    public void shareText(String contentTitle, String content) {
        intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");//设置分享内容的类型
        intent.putExtra(Intent.EXTRA_SUBJECT, contentTitle);//添加分享内容标题
        intent.putExtra(Intent.EXTRA_TEXT, content);//添加分享内容
        context.startActivity(Intent.createChooser(intent, TITLE));
    }

    @Override
    public void shareWeb(String url) {
        intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_TEXT, Uri.parse(url));
        context.startActivity(Intent.createChooser(intent, TITLE));
    }

    @Override
    public void shareImagePrepare(int code) {
        openAlbum(code);
    }

    @Override
    public void shareImage(Uri uri) {
        intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra("Kdescription", "hello");
//        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
//        intent.setComponent(comp);
        context.startActivity(Intent.createChooser(intent, TITLE));
    }

    @Override
    public void shareImages(ArrayList<Uri> uris) {
        intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        context.startActivity(Intent.createChooser(intent, TITLE));
    }

    @Override
    public void shareAudio(Uri uri) {

    }

    @Override
    public void shareVideo(Uri uri) {

    }

    @Override
    public void shareTo() {
        intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");//设置分享内容的类型
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享标题");//添加分享内容标题
        intent.putExtra(Intent.EXTRA_TEXT, "需要分享的内容");//添加分享内容
        context.startActivity(setComponents());
    }

    public Uri getUri() {
        File genFile = Environment.getExternalStorageDirectory();
        File file = new File(genFile, "shareImage/share.jpg");
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = FileProvider.getUriForFile(context, "com.example.lqqq.share.fileprovider", file);
        return uri;
    }

    @Override
    public void openAlbum(int code) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constant.PERMISSION_REQUEST_CODE_OPEN_ALBUM);
        } else {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            activity.startActivityForResult(intent, code);
        }
    }
}
