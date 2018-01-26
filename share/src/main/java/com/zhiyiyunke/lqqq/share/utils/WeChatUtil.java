package com.zhiyiyunke.lqqq.share.utils;

/**
 * 微信分享相关的工具类
 * Created by LQQQ1 on 2018/1/25.
 */

public class WeChatUtil {
    //创建一个标签，微信分享回调会用到
    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
