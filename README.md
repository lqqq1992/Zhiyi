# Zhiyi
公司项目用的插件demo
## 微信分享
### 初期准备
* 在[微信开放平台](https://open.weixin.qq.com)申请账号
* 登陆后，进入[管理中心](https://open.weixin.qq.com/cgi-bin/applist?t=manage/list&lang=zh_CN&token=b3b5e8345f7ddd164fa42371f8787ad83e1ccda1)创建移动应用，参考[微信开发者移动应用创建获取APP ID](https://www.jianshu.com/p/01d549014f0a)
  * 在创建时会要求填写应用包名和应用签名，应用包名不用说，应用签名可以用[微信资源中心]的签名工具生成
  * 需要将工具和你的应用安装到移动设备，运行工具，填写应用包名，就会生成签名；这个签名和你的开发应用的签名相关的
* 待审核通过后就会得到一个`APPID`

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
