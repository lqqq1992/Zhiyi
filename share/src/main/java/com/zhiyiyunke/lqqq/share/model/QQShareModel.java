package com.zhiyiyunke.lqqq.share.model;

import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.Tencent;
import com.zhiyiyunke.lqqq.share.R;
import com.zhiyiyunke.lqqq.share.view.ShareBaseActivity;

import java.util.ArrayList;

/**
 * Created by LQQQ1 on 2018/1/26.
 */

public class QQShareModel implements QQSahreBase{
    private ShareBaseActivity shareView;
    private Tencent tencent;
    public QQShareModel(ShareBaseActivity shareView){
        this.shareView = shareView;
        this.tencent = shareView.getTencent();
    }
    @Override
    public void shareTextAndImage(String title,String summary,String targetUrl,String imageUrl) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享类型，默认为图文分享
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);//分享标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  summary);//分享摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  targetUrl);//分享目标地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imageUrl);//分享图片地址
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, shareView.getResources().getString(R.string.app_name));//分享者名称
        //添加后同步分享到qq空间
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        tencent.shareToQQ(shareView, params, shareView);
    }

    @Override
    public void shareImage(String imageUrl) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);//图片分享
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, shareView.getResources().getString(R.string.app_name));
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        tencent.shareToQQ(shareView, params, shareView);
    }

    @Override
    public void shareMusic(String title,String summary,String targetUrl,String imageUrl,
                           String audioUrl) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);//音频分享
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, audioUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  shareView.getResources().getString(R.string.app_name));
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        tencent.shareToQQ(shareView, params, shareView);
    }
    @Override
    public void shareApp(String title,String summary,String imageUrl){
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  summary);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  shareView.getResources().getString(R.string.app_name));
        tencent.shareToQQ(shareView, params, shareView);
    }

    @Override
    public void shareToZone(String title,String summary,String targetUrl,ArrayList<String> imageUrls) {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );//qq空间分享，目前只支持图文
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        tencent.shareToQzone(shareView, params, shareView);
    }
}
