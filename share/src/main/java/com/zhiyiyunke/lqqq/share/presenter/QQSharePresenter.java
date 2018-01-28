package com.zhiyiyunke.lqqq.share.presenter;

import com.zhiyiyunke.lqqq.share.model.QQShareModel;
import com.zhiyiyunke.lqqq.share.view.ShareBaseActivity;

/**
 * Created by LQQQ1 on 2018/1/26.
 */

public class QQSharePresenter {
    private ShareBaseActivity shareView;
    private QQShareModel share;

    public QQSharePresenter(ShareBaseActivity shareView){
        this.shareView = shareView;
        this.share = new QQShareModel(shareView);
    }
    public void shareTextAndImage(){
        share.shareTextAndImage(shareView.getShareTitle(),shareView.getShareDescription()
                ,shareView.getWebPageUrl(),shareView.getImageUrl());
    }
    public void shareImage(){
        share.shareImage(shareView.getImagePath());
    }
    public void shareMusic(){
        share.shareMusic(shareView.getShareTitle(),shareView.getShareDescription()
                ,shareView.getWebPageUrl(),shareView.getImageUrl(),shareView.getMusicUrl());
    }
    public void shareApp(){
        share.shareApp(shareView.getShareTitle(),shareView.getShareDescription(),shareView.getImageUrl());
    }
    public void shareToQQZone(){
        share.shareToZone(shareView.getShareTitle(),shareView.getShareDescription()
                ,shareView.getWebPageUrl(),shareView.getImageUrls());
    }
}
