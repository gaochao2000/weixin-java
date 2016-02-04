package com.x97.weixin;

import com.x97.util.web.HttpClientWrap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;

/**
 * 获取微信 API 的 Token 及 JSAPI 的 Token 的服务类
 */
public class WeixinTokenService {
    private Log log = LogFactory.getLog(this.getClass());

    private TokenCacheService tokenCacheService;

    /**
     * 微信服务号的APPID，是微信公众平台中开发者中心的AppId
     */
    private String appId;
    /**
     * 微信服务号的secret，是微信公众平台中开发者中心的AppSecret
     */
    private String secret;

    public void setTokenCacheService(TokenCacheService tokenCacheService) {
        this.tokenCacheService = tokenCacheService;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * @return 返回有效的api调用的Token
     * @throws Exception
     */
    public synchronized String findAccessToken() throws Exception {
        if (tokenCacheService != null) {
            if (tokenCacheService.getAccessToken() == null) {
                refreshAccessToken();
            }
            return tokenCacheService.getAccessToken();
        } else {
            log.error("TokenService必须先初始化TokenCacheService");
            return null;
        }
    }

    private void refreshAccessToken() throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + secret;
        log.debug("【发起微信请求】:" + url);
        String responseText = new HttpClientWrap().httpGetRequest(url);
        log.debug("【接收微信回应】:" + responseText);
        JSONObject accessTokenJson = new JSONObject(responseText);
        if (!accessTokenJson.has("errmsg")) {
            tokenCacheService.saveAccessToken(accessTokenJson.getString("access_token"), accessTokenJson.getInt("expires_in"));
        } else {
            log.error("【接收参数错误】:" + responseText);
            tokenCacheService.saveAccessToken(null, 0);
        }
    }

    /**
     * @return 调用JSAPI的Token
     * @throws Exception
     */
    public synchronized String findJSAPIToken() throws Exception {
        if (tokenCacheService != null) {
            if (tokenCacheService.getJSAPIToken() == null) {
                refreshJSAPIToken();
            }
            return tokenCacheService.getJSAPIToken();
        } else {
            log.error("TokenService必须先初始化TokenCacheService");
            return null;
        }
    }

    private void refreshJSAPIToken() throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + findAccessToken() + "&type=jsapi";
        log.debug("【发起微信请求】:" + url);
        String responseText = new HttpClientWrap().httpGetRequest(url);
        log.debug("【接收微信回应】:" + responseText);
        JSONObject jsapiTokenJson = new JSONObject(responseText);
        if (jsapiTokenJson.getInt("errcode") == 0 && jsapiTokenJson.getString("errmsg").equals("ok")) {
            tokenCacheService.saveJSAPIToken(jsapiTokenJson.getString("ticket"), jsapiTokenJson.getInt("expires_in"));

        } else {
            tokenCacheService.saveJSAPIToken(null, 0);
            log.error("【接收参数错误】:" + responseText);
        }
    }
}
