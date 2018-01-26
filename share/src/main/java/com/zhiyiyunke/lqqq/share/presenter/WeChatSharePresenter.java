package com.zhiyiyunke.lqqq.share.presenter;

import com.zhiyiyunke.lqqq.share.model.WeChatShare;
import com.zhiyiyunke.lqqq.share.view.ShareBaseActivity;

/**
 * Created by LQQQ1 on 2018/1/25.
 */

public class WeChatSharePresenter {
    private ShareBaseActivity shareView;//微信分享UI
    private WeChatShare share;//微信分享功能model

    public WeChatSharePresenter(ShareBaseActivity shareView){
        this.shareView = shareView;
        this.share = new WeChatShare(shareView,shareView.getScene());
    }
    //分享文本
    public void shareText(){
        share.shareText(shareView.getShareText());
    }
    //分享图片
    public void shareImageFromBitmap(){
        share.shareImage(shareView.getBitmap());
    }
    public void shareImageFromPath(){
        share.shareImage(shareView.getImagePath());
    }
    public void shareImageFromUrl(){
        share.shareImageFromUrl(shareView.getImageUrl());
    }
    public void shareImageFromData(){
        share.shareImage(shareView.getImageData());
    }
    //分享音频
    public void shareMusic(){
        share.shareMusic(shareView.getShareTitle(),shareView.getShareDescription(),shareView.getMusicUrl());
    }
    //分享视频
    public void shareVideo(){
        share.shareVideo(shareView.getShareTitle(),shareView.getShareDescription(),shareView.getVideoUrl());
    }
    //分享网页
    public void shareWebPage(){
        share.shareWeb(shareView.getShareTitle(),shareView.getShareDescription(),shareView.getWebPageUrl());
    }
}
