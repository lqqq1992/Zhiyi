package com.zhiyiyunke.lqqq.share.model;

import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

/**
 * 原生分享model接口
 * Created by LQQQ1 on 2018/1/23.
 */

public interface ShareBase {
    Intent setComponents();
    void shareText(String contentTitle,String content);
    void shareWeb(String url);
    void shareImagePrepare(int code);
    void shareImage(Uri uri);
    void shareImages(ArrayList<Uri> uris);
    void shareAudio(Uri uri);
    void shareVideo(Uri uri);
    void shareTo();
    void openAlbum(int code);
}
