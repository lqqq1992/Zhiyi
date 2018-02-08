package com.zhiyiyunke.lqqq.share.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * bitmap相关的工具类
 * Created by LQQQ1 on 2018/1/25.
 */

public class BitmapUtil {
    /**
     * 图片转化为byte[]
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
