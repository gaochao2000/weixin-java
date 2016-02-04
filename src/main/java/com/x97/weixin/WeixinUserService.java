package com.x97.weixin;

import com.x97.util.web.HttpClientWrap;
import com.x97.weixin.domain.WeixinUserGroup;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by yanggch on 2016/1/30.
 */
public class WeixinUserService {

    private Log log = LogFactory.getLog(this.getClass());

    private WeixinTokenService weixinTokenService;
    private String appId;
    private String secret;

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setWeixinTokenService(WeixinTokenService weixinTokenService) {
        this.weixinTokenService = weixinTokenService;
    }


    public WeixinUserGroup createUserGroup(String name) throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=" + weixinTokenService.findAccessToken();
        JSONObject createUserGroupJson = new JSONObject();

        JSONObject nameJson = new JSONObject();
        nameJson.put("name", name);
        createUserGroupJson.put("group", nameJson);
        log.debug("【发起微信请求】:" + url);
        log.debug("【微信请求数据】:" + createUserGroupJson.toString());
        String responseContext = new HttpClientWrap().httpPostRequest(url, createUserGroupJson.toString());
        log.debug("【接收微信回应】 :" + responseContext);
        JSONObject resultJson = new JSONObject(responseContext);
        if (resultJson.has("errcode")) {
            log.error("create user group error: " + resultJson.toString());
            return null;
        } else {
            WeixinUserGroup weixinUserGroup = new WeixinUserGroup();
            weixinUserGroup.setId(resultJson.getJSONObject("group").getInt("id"));
            weixinUserGroup.setName(resultJson.getJSONObject("group").getString("name"));
            return weixinUserGroup;
        }
    }

    public boolean movenUserToGroup(String openId, Integer groupId) throws Exception {
        String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=" + weixinTokenService.findAccessToken();
        JSONObject moveUserToGroupJson = new JSONObject();
        moveUserToGroupJson.put("openid", openId);
        moveUserToGroupJson.put("to_groupid", groupId);
        log.debug("【发起微信请求】:" + url);
        log.debug("【微信请求数据】:" + moveUserToGroupJson.toString());
        String responseContext = new HttpClientWrap().httpPostRequest(url, moveUserToGroupJson.toString());
        log.debug("【接收微信回应】 :" + responseContext);
        JSONObject resultJson = new JSONObject(responseContext);
        if (resultJson.has("errcode") && resultJson.getInt("errcode") == 0) {
            return true;
        } else {
            return false;
        }
    }
}
