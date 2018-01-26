package com.zhiyiyunke.lqqq.share.model;

import android.graphics.Bitmap;

/**
 * 微信官方sdk分享接口
 * Created by LQQQ1 on 2018/1/24.
 */

public interface WeChatShareBase {
    /**
     * 分享文本
     * @param text 文本内容
     */
    void shareText(String text);

    /**
     * 通过bitmap资源分享图片
     * @param bitmap
     */
    void shareImage(Bitmap bitmap);

    /**
     * 通过路径分享图片
     * @param path
     */
    void shareImage(String path);

    /**
     * 通过数据分享图片
     * @param imageBytes
     */
    void shareImage(byte[] imageBytes);

    /**
     * 通过url路径分享图片
     * @param url
     */
    void shareImageFromUrl(String url);

    /**
     * 通过音频文件url分享音频
     * @param title  分享标题
     * @param description  分享描述
     * @param url
     */
    void shareMusic(String title,String description,String url);

    /**
     * 通过视频文件url分享视频
     * @param title
     * @param description
     * @param url
     */
    void shareVideo(String title,String description,String url);

    /**
     * 通过网页链接url分享网页
     * @param title
     * @param description
     * @param url
     */
    void shareWeb(String title,String description,String url);

    /**
     * 分享图文
     * @param title
     * @param description
     * @param bitmap
     */
    void shareImageWithNotes(String title,String description,Bitmap bitmap);
}
