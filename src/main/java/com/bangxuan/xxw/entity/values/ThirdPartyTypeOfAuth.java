package com.bangxuan.xxw.entity.values;

/**
 * 第三方授权类型
 */
public enum ThirdPartyTypeOfAuth {
    WeChat(0),//微信
    QQ(1),//腾讯QQ
    AliPay(2),//支付宝
    Weibo(3),//微博
    WechatApplet(4),//微信小程序
    WechatPublic(5);//微信公众号

    ThirdPartyTypeOfAuth(int value) {
    }
}
