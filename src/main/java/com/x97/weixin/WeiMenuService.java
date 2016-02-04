package com.x97.weixin;

import com.x97.util.web.HttpClientWrap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;

/**
 * 微信公众号才到服务
 */
public class WeiMenuService {
    private Log log = LogFactory.getLog(this.getClass());
    private WeixinTokenService weixinTokenService;

    public void setWeixinTokenService(WeixinTokenService weixinTokenService) {
        this.weixinTokenService = weixinTokenService;
    }

    public String findMenus() throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + weixinTokenService.findAccessToken();
        log.debug("【发起微信请求】:" + url);
        String responseContext = new HttpClientWrap().httpGetRequest(url);
        log.debug("【接收微信回应】 :" + responseContext);
        return responseContext;
    }

    public boolean createConditionMenu(String menus) throws Exception {

        String url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=" + weixinTokenService.findAccessToken();
        JSONObject moveUserToGroupJson = new JSONObject();

        log.debug("【发起微信请求】:" + url);
        log.debug("【微信请求数据】:" + menus);
        String responseContext = new HttpClientWrap().httpPostRequest(url, menus);
        log.debug("【接收微信回应】 :" + responseContext);
        JSONObject resultJson = new JSONObject(responseContext);
        if (resultJson.has("menuid")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteConditionMenu(Integer menuId) throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=" + weixinTokenService.findAccessToken();
        JSONObject responseJson = new JSONObject();
        responseJson.put("menuid", String.valueOf(menuId));
        log.debug("【微信请求数据】:" + responseJson.toString());
        log.debug("【发起微信请求】:" + url);
        String responseContext = new HttpClientWrap().httpPostRequest(url, responseJson.toString());
        log.debug("【接收微信回应】 :" + responseContext);
        JSONObject resultJson = new JSONObject(responseContext);
        if (resultJson.has("errcode") && resultJson.getInt("errcode") == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAllMenu() throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + weixinTokenService.findAccessToken();
        log.debug("【发起微信请求】:" + url);
        String responseContext = new HttpClientWrap().httpGetRequest(url);
        log.debug("【接收微信回应】 :" + responseContext);
        JSONObject resultJson = new JSONObject(responseContext);
        if (resultJson.has("errcode") && resultJson.getInt("errcode") == 0) {
            return true;
        } else {
            return false;
        }
    }
}
