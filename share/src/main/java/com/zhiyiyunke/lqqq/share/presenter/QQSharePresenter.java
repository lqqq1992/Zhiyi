package com.zhiyiyunke.lqqq.share.presenter;

import com.zhiyiyunke.lqqq.share.model.QQShareModel;
import com.zhiyiyunke.lqqq.share.view.ShareBaseActivity;

/**
 * Created by LQQQ1 on 2018/1/26.
 */

public class QQSharePresenter {
    private ShareBaseActivity shareView;//分享UI
    private QQShareModel share;//分享功能对象

    public QQSharePresenter(ShareBaseActivity shareView){
        this.shareView = shareView;
        this.share = new QQShareModel(shareView);
    }
    //分享文本
    public void shareTextAndImage(){
        share.shareTextAndImage(shareView.getShareTitle(),shareView.getShareDescription()
                ,shareView.getWebPageUrl(),shareView.getImageUrl());
    }
    //分享图片
    public void shareImage(){
        share.shareImage(shareView.getImagePath());
    }
    //分享音频
    public void shareMusic(){
        share.shareMusic(shareView.getShareTitle(),shareView.getShareDescription()
                ,shareView.getWebPageUrl(),shareView.getImageUrl(),shareView.getMusicUrl());
    }
    //分享APP
    public void shareApp(){
        share.shareApp(shareView.getShareTitle(),shareView.getShareDescription(),shareView.getImageUrl());
    }
    //分享到QQ空间
    public void shareToQQZone(){
        share.shareToZone(shareView.getShareTitle(),shareView.getShareDescription()
                ,shareView.getWebPageUrl(),shareView.getImageUrls());
    }
}
