# Zhiyi
公司项目用的插件demo
## 微信分享
### 初期准备
* 在[微信开放平台](https://open.weixin.qq.com)申请账号
* 登陆后，进入[管理中心](https://open.weixin.qq.com/cgi-bin/applist?t=manage/list&lang=zh_CN&token=b3b5e8345f7ddd164fa42371f8787ad83e1ccda1)创建移动应用，参考[微信开发者移动应用创建获取APP ID](https://www.jianshu.com/p/01d549014f0a)
  * 在创建时会要求填写应用包名和应用签名，应用包名不用说，应用签名可以用[微信资源中心]的签名工具生成
  * 需要将工具和你的应用安装到移动设备，运行工具，填写应用包名，就会生成签名；这个签名和你的开发应用的签名相关的
* 待`审核通过`后就会得到一个`APPID`

### 开发
* 参考[微信接入指南](https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=1417751808&token=f4464de1cd69d0cef908b181c6ab61ce4d0f5d29&lang=zh_CN)
* 代码参考如下（以分享文本为例）：
```Java
        //1.创建一个WXTextObject对象
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;

        //2.创建一个WXMediaMessage对象
        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = textObject;

        //3.将分享内容传递给WXMediaMessage对象
        message.description = text;

        //4.创建一个rep对象，设置标签、内容、目标
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = WeChatUtil.buildTransaction("text");//用于标识请求，回到中会用到
        req.message = message;//分享内容
        //分享目标场景,有以下3个值：
        //微信聊天      SendMessageToWX.Req.WXSceneSession = 0
        //微信朋友圈     SendMessageToWX.Req.WXSceneTimeline = 1
        //微信收藏      SendMessageToWX.Req.WXSceneFavorite = 2
        req.scene = scene;

        //5.发送分享
        api.sendReq(req);
```
除了文本分享以外，其他分享还有个缩略图设置代码：
```Java
        //设置缩略图
        Bitmap bmp = BitmapFactory.decodeFile(path);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = BitmapUtil.bmpToByteArray(thumbBmp, true);
```
代码中的第一步中需要创建的对象，都是继承自IMediaObject：
* 文本  WXTextObject
* 图片  WXImageObject
* 音频  WXMusicObject
* 视频  WXVideoObject
* 网页  WXWebpageObject

## QQ分享
### 初期准备
* 在[腾讯开放平台](http://open.qq.com/)注册账号
* 在[管理中心](http://op.open.qq.com/manage_centerv2/android?owner=835460491&uin=835460491)创建APP应用，与微信分享不同的是：创建的APP`不需要审核`就能获得`APPID`，可以直接使用
* 在[资料库](http://wiki.open.qq.com/wiki/mobile/SDK%E4%B8%8B%E8%BD%BD)下载开发所用的SDK，并导入工程依赖
### 开发
* 参考[Android开发指南](http://wiki.open.qq.com/wiki/%E5%88%86%E4%BA%AB%E6%B6%88%E6%81%AF%E5%88%B0QQ%EF%BC%88%E5%AE%9A%E5%90%91%E5%88%86%E4%BA%AB%EF%BC%89)和[Android_API调用说明](http://wiki.open.qq.com/index.php?title=Android_API%E8%B0%83%E7%94%A8%E8%AF%B4%E6%98%8E&=45038#1.13_.E5.88.86.E4.BA.AB.E6.B6.88.E6.81.AF.E5.88.B0QQ.EF.BC.88.E6.97.A0.E9.9C.80QQ.E7.99.BB.E5.BD.95.EF.BC.89)
* 开发配置：
```java
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  
  <activity
      android:name="com.tencent.tauth.AuthActivity"
      android:noHistory="true"
      android:launchMode="singleTask" >
         <intent-filter>
             <action android:name="android.intent.action.VIEW" />
             <category android:name="android.intent.category.DEFAULT" />
             <category android:name="android.intent.category.BROWSABLE" />
             <data android:scheme="tencentAPPID" /> <!-- 获取的APPID -->
          </intent-filter>
  </activity>
  
  <activity android:name="com.tencent.connect.common.AssistActivity"
      android:theme="@android:style/Theme.Translucent.NoTitleBar"
      android:configChanges="orientation|keyboardHidden|screenSize" />
```
`注意`：tencentAPPID,tencent不能省略，APPID就是创建APP获取的APPID
* 代码参考：

1、创建Tencent对象实例
```java
   tencent = Tencent.createInstance(Constant.APP_ID_TENCENT,this);
```

  2、实现分享回调接口
```java
   public abstract class ShareBaseActivity extends AppCompatActivity implements IUiListener{
         @Override
    public void onComplete(Object o) {
        Toast.makeText(this,"分享成功！",Toast.LENGTH_LONG).show();
        Log.e("----------","分享成功！");
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(this,"分享失败！",Toast.LENGTH_LONG).show();
        Log.e("----------","分享失败！");
    }

    @Override
    public void onCancel() {
        Toast.makeText(this,"分享被取消！",Toast.LENGTH_LONG).show();
        Log.e("----------","分享被取消！");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode,resultCode,data,this);
    }
   }
```
`注意`：官方文档中有一处demo在onActivityResult()方法中添加的代码是：
```java
mTencent.onActivityResult(requestCode, resultCode, data);
```
`实际上`要添加的是上文中的:
```java
Tencent.onActivityResultData(requestCode,resultCode,data,this);
```

  3、分享内容添加（通过`Bundle`对象）：
```java
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享类型，默认为图文分享
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);//分享标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  summary);//分享摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  targetUrl);//分享目标地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imageUrl);//分享图片地址
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, shareView.getResources().getString(R.string.app_name));//分享者名称
        //添加后同步分享到qq空间
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        tencent.shareToQQ(shareView, params, shareView);
```
`补充`：分享类型通过以下值区分
```java
  params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);//图片分享
  params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);//音频分享
  params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);//APP分享
  params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );//qq空间分享，目前只支持图文
```
### 图片压缩
* 尺寸压缩：采用的是采样率压缩，为了节约内存使用
```java
public Bitmap scale(File imageFile){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
        options.inSampleSize = calculateInSampleSize(options,maxWidth,maxHeight);
        oldSize = options.inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
    }
```
 主要实现是在options.inSampleSize的值上的修改，具体压缩算法就是在这个值得计算上
* 质量压缩：
```java
   bitmap.compress(Bitmap.CompressFormat.JPEG,quality,baos);
```
 代码很简单就一句，参数quality表示压缩质量（0~100），100表示不压缩，值越小失真越严重
* 工具类使用代码参考
```java
compressor.setMaxWidth(getContent(newWidth, 1000))//宽度设置
                          .setMaxHeight(getContent(newHeight, 1000))//高度设置
                          .setMaxSize(getContent(quality, 500))//最大图片大小
                          .compressImage(file)//压缩
                          .addTo(newImage);//添加到UI
                          .saveImageFile(path, "cache.jpg");//保存图片
```
### 消息推送（极光推送）
官网的[集成文档和API文档](https://docs.jiguang.cn/jpush/client/Android/android_guide/)都十分详尽，下面说一下大概步骤：
* 在[官网](https://www.jiguang.cn/)注册账号并创建应用
* 集成SDK到项目中（ps：AS用户可以直接通过gradle配置jcenter库）
* 在项目中添加注册代码，开启推送服务,一般是在application中
```java
        JPushInterface.setDebugMode(true);//开启Log调试
        JPushInterface.init(this);//注册APP
        JPushInterface.requestPermission(this);//权限
```
* 创建静态广播接收器
```java
  <receiver
            android:name="PushReceiver"
            android:enabled="true">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.REGISTRATION" />
                <action android:name="cn.jpush.android.intent.MESSAGE_RECEIVED" />
                <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED" />
                <action android:name="cn.jpush.android.intent.NOTIFICATION_OPENED" />
                <action android:name="cn.jpush.android.intent.NOTIFICATION_CLICK_ACTION" />
                <action android:name="cn.jpush.android.intent.CONNECTION" />

                <category android:name="com.example.lqqq.push" />//修改为你的包名
            </intent-filter>
        </receiver>
```
```java
  public class PushReceiver extends BroadcastReceiver
```
 在这个receiver中实现各种action逻辑，[官方API](https://docs.jiguang.cn/jpush/client/Android/android_api/)对于各个ACTION的解释和使用非常详细，这里就不赘述了
* 消息回调（主要是对于别名和标签的增删改查，以及对于手机号的相关操作的请求返回）
 ```java
   public class PushCallBackReceiver extends JPushMessageReceiver
 ```
  具体重写JPushMessageReceiver中的几个方法即可
 
