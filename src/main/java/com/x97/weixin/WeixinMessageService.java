package com.x97.weixin;

import com.x97.util.web.HttpClientWrap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by yanggch on 2015/10/26.
 */
public class WeixinMessageService {

    private static Log log = LogFactory.getLog(WeixinMessageService.class);

    private WeixinTokenService weixinTokenService;


    public void setWeixinTokenService(WeixinTokenService weixinTokenService) {
        this.weixinTokenService = weixinTokenService;
    }


    public String createTextMessage(String toUserOpenId, String content) {
        try {
            JSONObject messageJson = new JSONObject();
            messageJson.put("touser", toUserOpenId);
            messageJson.put("msgtype", "text");
            JSONObject textJson = new JSONObject();
            textJson.put("content", content);
            messageJson.put("text", textJson);
            return messageJson.toString();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
        return null;
    }

    /**
     * 主动给一个微信用户发一个文本消息
     *
     * @param toUserOpenId 要发送用户的openid
     * @param content      要发送的内容
     * @return 发送成功返回true，否则返回false
     */
    public boolean sendTextMessage(String toUserOpenId, String content) {
        try {
            return sendMessage(createTextMessage(toUserOpenId, content));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
        return false;
    }

    /**
     * 发送一个客服消息
     *
     * @param message 发送消息的内容。根据内容不同，可能是文本消息，图文消息，多媒体消息等
     * @return 发送成功返回true，否则返回false;
     * @throws Exception
     */
    public boolean sendMessage(String message) throws Exception {
        if (weixinTokenService != null) {
            if (message != null) {
                String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + weixinTokenService.findAccessToken();
                log.debug("【发起微信请求】:" + url);
                log.debug("【微信请求数据】:" + message);
                String responseContext = new HttpClientWrap().httpPostRequest(url, message);
                log.debug("【接收微信回应】 :" + responseContext);
                JSONObject responseJson = new JSONObject(responseContext);
                if (responseJson.getLong("errcode") == 0) {
                    return true;
                }
            } else {
                log.warn("要发送的微信消息内容为空");
            }
        } else {
            log.error("MessageService必须先初始化TokenService");
        }
        return false;

    }
}
