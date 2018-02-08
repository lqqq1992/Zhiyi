package com.zhiyiyunke.lqqq.share.model;

import java.util.ArrayList;

/**
 * 分享到QQ和QQ空间的接口
 * Created by LQQQ1 on 2018/1/26.
 */

public interface QQSahreBase {
    /**
     * 分享文本和图片，默认模式
     * @param title 标题
     * @param summary 摘要
     * @param targetUrl 连接地址
     * @param imageUrl 图片路径
     */
    void shareTextAndImage(String title,String summary,String targetUrl,String imageUrl);

    /**
     * 分享图片
     * @param imageUrl 图片路径
     */
    void shareImage(String imageUrl);

    /**
     * 分享音频
     * @param title 标题
     * @param summary 摘要
     * @param targetUrl 链接地址
     * @param imageUrl 图片路径
     * @param audioUrl 音频路径
     */
    void shareMusic(String title,String summary,String targetUrl,String imageUrl,
                    String audioUrl);

    /**
     * 分享APP
     * @param title 标题
     * @param summary 摘要
     * @param imageUrl 缩略图路径
     */
    void shareApp(String title,String summary,String imageUrl);

    /**
     * 分享到QQ空间
     * @param title 标题
     * @param summary 摘要
     * @param targetUrl 链接地址
     * @param imageUrls 图片路径（是一个地址集合，最多9张图）
     */
    void shareToZone(String title,String summary,String targetUrl,ArrayList<String> imageUrls);
}
