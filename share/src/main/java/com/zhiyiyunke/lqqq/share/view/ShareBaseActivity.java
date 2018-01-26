package com.zhiyiyunke.lqqq.share.view;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhiyiyunke.lqqq.share.bean.Constant;

/**
 * 分享UI基类
 * Created by LQQQ1 on 2018/1/25.
 */

public abstract class ShareBaseActivity extends AppCompatActivity{

    private IWXAPI api;

    /**
     * 获取注册的api对象
     * @return
     */
    public IWXAPI getApi(){
        //创建IWXAPI实例
        this.api = WXAPIFactory.createWXAPI(this, Constant.APP_ID,true);
        //将应用appid注册到微信
        api.registerApp(Constant.APP_ID);
        return api;
    }

    /**
     * 从微信注销
     */
    public void unregisterApi(){
        if(api != null){
            api.unregisterApp();
        }
    }

    /**
     * 获取分享目标场景
     * @return 0：微信聊天；1：微信朋友圈；2：微信收藏
     */
    public abstract int getScene();

    /**
     * 获取分享标题
     * @return
     */
    public abstract String getShareTitle();

    /**
     * 获取分享描述
     * @return
     */
    public abstract String getShareDescription();

    /**
     * 获取分享文本内容
     * @return
     */
    public abstract String getShareText();

    /**
     * 获取bitmap对象资源
     * @return
     */
    public abstract Bitmap getBitmap();

    /**
     * 获取图片路径
     * @return
     */
    public abstract String getImagePath();

    /**
     * 获取图片url
     * @return
     */
    public abstract String getImageUrl();

    /**
     * 获取图片数据
     * @return
     */
    public abstract byte[] getImageData();

    /**
     * 获取音频文件url
     * @return
     */
    public abstract String getMusicUrl();

    /**
     * 获取视频文件url
     * @return
     */
    public abstract String getVideoUrl();

    /**
     * 获取网页链接url
     * @return
     */
    public abstract String getWebPageUrl();
}
