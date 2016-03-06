package com.x97.weixin;

import com.x97.util.encode.EncryptUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 微信Auth 服务类
 */
public class WeixinOAuthService {


    private Log log = LogFactory.getLog(this.getClass());
    private String appId;


    private String serverAuthenToken;


    public void setAppId(String appId) {
        this.appId = appId;
    }


    public void setServerAuthenToken(String serverAuthenToken) {
        this.serverAuthenToken = serverAuthenToken;
    }


    /**
     * 对用户在公众平台上的开发者中心进行服务器配置时微信服务器返回来的消息进行签名。
     * 该签名应该和服务器返回消息中的 signature 参数进行比较，
     * 来判断是否是合法的微信服务器发来的消息
     * 根据微信开发者文档 http://mp.weixin.qq.com/wiki/16/1e87586a83e0e121cc3e808014375b74.html 中第二步：验证服务器地址的有效性
     *
     * @param timestamp 服务器设置时，微信服务器请求我们设置的URL的timestamp参数，签名字段
     * @param nonce     服务器设置时，微信服务器请求我们设置的URL的noce参数，签名字段
     * @return 通过维系服务器请求的 timestamp,nonce 参数以及配置的 serverAuthenToken 进行签名的结果
     */
    public String serverAuthenSign(String timestamp, String nonce) {
        StringBuilder autheMessage = new StringBuilder();
        List<String> parameters = new ArrayList<String>();
        parameters.add(timestamp);
        parameters.add(nonce);
        parameters.add(serverAuthenToken);
        Collections.sort(parameters);
        for (String parameter : parameters) {
            autheMessage.append(parameter);
        }
        log.debug("autho string = " + autheMessage.toString());
        String encryptMessage = EncryptUtil.encrypt(autheMessage.toString(), "SHA-1");
        log.debug("encrypt message = " + encryptMessage);
        return encryptMessage;
    }


    /**
     * 生成网页授权获取用户基本信息的URL
     * 根据微信公众平台开发者文档 http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html 中第一步：用户同意授权，获取code
     *
     * @param redirect_url
     * @return
     */
    public String generateOAuthUserInfoURL(String redirect_url) {
        try {
            StringBuilder url = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
            url.append(appId).append("&redirect_uri=").append(URLEncoder.encode(redirect_url, "utf-8")).append("&response_type=code&scope=snsapi_userinfo&state=snsapi_userinfo#wechat_redirect");
            return url.toString();
        } catch (Exception e) {
            log.error(e);
        }
        return null;

    }

    /**
     * 生成网页授权获取OPENID
     * 根据微信公众平台开发者文档 http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html 中第一步：用户同意授权，获取code
     *
     * @param redirect_url
     * @return
     */
    public String generateOAuthBaseURL(String redirect_url) {
        try {
            StringBuilder url = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
            url.append(appId).append("&redirect_uri=").append(URLEncoder.encode(redirect_url, "utf-8")).append("&response_type=code&scope=snsapi_base&state=snsapi_base#wechat_redirect");
            return url.toString();
        } catch (Exception e) {
            log.error(e);
        }
        return null;

    }

}
