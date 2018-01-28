package com.zhiyiyunke.lqqq.share.model;

import java.util.ArrayList;

/**
 * 分享到QQ和QQ空间的接口
 * Created by LQQQ1 on 2018/1/26.
 */

public interface QQSahreBase {
    void shareTextAndImage(String title,String summary,String targetUrl,String imageUrl);
    void shareImage(String imageUrl);
    void shareMusic(String title,String summary,String targetUrl,String imageUrl,
                    String audioUrl);
    void shareApp(String title,String summary,String imageUrl);
    void shareToZone(String title,String summary,String targetUrl,ArrayList<String> imageUrls);
}
