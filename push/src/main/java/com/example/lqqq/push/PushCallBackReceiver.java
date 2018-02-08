package com.example.lqqq.push;

import android.content.Context;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Set;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * Created by LQQQ1 on 2018/2/7.
 */

public class PushCallBackReceiver extends JPushMessageReceiver {

    /**
     * 别名相关操作回调
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        Toast.makeText(context,"别名回调结果："+jPushMessage.getAlias(),Toast.LENGTH_LONG).show();
    }

    /**
     * 标签的增删改查操作回调
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onTagOperatorResult(context, jPushMessage);
        Set<String> tags = jPushMessage.getTags();
        String[] tagArray = new String[tags.size()];
        tagArray = tags.toArray(tagArray);
        Toast.makeText(context,"标签回调结果："+ Arrays.toString(tagArray),Toast.LENGTH_LONG).show();
    }

    /**
     * 查询某个标签与用户绑定状态的回调
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    /**
     * 设置手机号码的回调
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }
}
