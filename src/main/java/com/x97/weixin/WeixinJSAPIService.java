package com.x97.weixin;

import com.x97.util.encode.EncryptUtil;
import com.x97.weixin.util.WeixinUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;

import java.util.*;

/**
 * Created by yanggch on 2015/10/26.
 */
public class WeixinJSAPIService {
    private static Log log = LogFactory.getLog(WeixinJSAPIService.class);
    private String appId;
    private String[] jsApiList = new String[]{"onMenuShareTimeline", "onMenuShareAppMessage", "onMenuShareQQ", "onMenuShareWeibo", "startRecord", "stopRecord", "onVoiceRecordEnd", "playVoice", "pauseVoice", "stopVoice", "onVoicePlayEnd", "uploadVoice", "downloadVoice", "chooseImage", "previewImage", "uploadImage", "downloadImage", "translateVoice", "getNetworkType", "openLocation", "getLocation", "hideOptionMenu", "showOptionMenu", "hideMenuItems", "showMenuItems", "hideAllNonBaseMenuItem", "showAllNonBaseMenuItem", "closeWindow", "scanQRCode", "chooseWXPay", "openProductSpecificView", "addCard", "chooseCard", "openCard"};
    private WeixinTokenService weixinTokenService;

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setWeixinTokenService(WeixinTokenService weixinTokenService) {
        this.weixinTokenService = weixinTokenService;
    }

    /**
     * 调用JSAPI的配置配置信息
     *
     * @param url 调用JSAPI页面的URL
     * @return 在HTML5页面上调用微信支付JSAPI的配置信息。
     */
    public String findJSAPIConfig(String url) {
        try {
            if (weixinTokenService != null) {
                Map<String, String> configMap = new HashMap<String, String>();
                JSONObject configJson = new JSONObject();
                //configMap.put("debug","true");

                configJson.put("appId", appId);
                ;
                configJson.put("timestamp", new Date().getTime());
                configJson.put("nonceStr", new WeixinUtil().createRandomString());
                configJson.put("jsApiList", generateJSApiList());

                configMap.put("timestamp", String.valueOf(configJson.getLong("timestamp")));
                configMap.put("noncestr", configJson.getString("nonceStr"));
                configMap.put("url", url);
                configMap.put("jsapi_ticket", weixinTokenService.findJSAPIToken());

                configJson.put("signature", createJSApiSing(configMap));
                return configJson.toString();
            } else {
                log.error("WeixinJSAPIService 必须先初始化 WeixinTokenService");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
        return null;
    }

    public String createJSApiSing(Map<String, String> parameters) {
        StringBuilder paraStr = new StringBuilder();

        List<String> keys = new ArrayList<String>(parameters.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            paraStr.append(key).append("=").append(parameters.get(key)).append("&");
        }
        if (paraStr.length() > 0) {
            paraStr.deleteCharAt(paraStr.length() - 1);
        }
        log.debug("encrypt string : " + paraStr.toString());
        String encryptString = EncryptUtil.encrypt(paraStr.toString(), "SHA-1");
        log.debug("encrypt result :" + encryptString);
        encryptString = EncryptUtil.encryptOther(paraStr.toString(), "SHA-1");
        log.debug("encryother result : " + encryptString);
        return encryptString;

    }

    private String generateJSApiList() {
        StringBuilder jsapiString = new StringBuilder();
        for (String api : jsApiList) {
            jsapiString.append("'").append(api).append("','");
        }
        if (jsapiString.length() > 1) {
            jsapiString.delete(jsapiString.length() - 2, jsapiString.length());
        }
        return jsapiString.toString();
    }
}
