package com.x97.weixin;

import com.x97.util.web.HttpClientWrap;
import com.x97.weixin.domain.WeixinUser;
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

    /**
     * 通过微信Auth授权得到的code获取用户信息
     *
     * @param code 通过微信Auth授权跳转的url中获取的code参数
     * @return
     */
    public WeixinUser findUserInfo(String code) {
        WeixinUser weixinUser = null;
        try {
            String initUserInfo = initUserInfo(code, appId, secret);
            if (initUserInfo != null) {
                JSONObject oInitUserInfo = new JSONObject(initUserInfo);
                if (oInitUserInfo.has("access_token") && oInitUserInfo.has("openid")) {
                    weixinUser = new WeixinUser();
                    weixinUser.setOpenId(oInitUserInfo.getString("openid"));
                    weixinUser.setScope(oInitUserInfo.getString("scope"));
                    if (oInitUserInfo.has("unionid")) {
                        weixinUser.setUnionId(oInitUserInfo.getString("unionid"));
                    }
                    if (WeixinUser.SCOPE_USERINFO.equals(weixinUser.getScope())) {
                        String userInfo = findUserInfoFromServer(oInitUserInfo.getString("access_token"), oInitUserInfo.getString("openid"));
                        if (userInfo != null) {
                            JSONObject oUserInfo = new JSONObject(userInfo);
                            if (!oUserInfo.has("errcode")) {
                                weixinUser.setNickName(oUserInfo.getString("nickname"));
                                weixinUser.setCountry(oUserInfo.getString("country"));
                                weixinUser.setProvince(oUserInfo.getString("province"));
                                weixinUser.setCity(oUserInfo.getString("city"));
                                weixinUser.setPrivilege(oUserInfo.getString("privilege"));
                                weixinUser.setHeadIcon(oUserInfo.getString("headimgurl"));
                            } else {
                                log.warn("微信没有正确获取用户信息");
                            }
                        } else {
                            log.warn("微信获取用户信息返回数据为空");
                        }
                    } else {
                        log.debug("oauth type is not snsapi_userinfo,do not find user info");
                    }
                } else {
                    log.warn("微信没有获取到用户的openid");
                }
            } else {
                log.warn("微信获取用户openid返回数据为空");
            }
        } catch (Exception e) {
            log.error("【程序执行错误】");
            log.error(e);
        }
        return weixinUser;
    }

    private String initUserInfo(String code, String appId, String secret) {

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId
                + "&secret=" + secret
                + "&code=" + code
                + "&grant_type=authorization_code";
        log.debug("【发起微信请求】:" + url);
        String response = new HttpClientWrap().httpGetRequest(url);
        log.debug("【接收微信回应】:" + response);
        return response;

    }

    private String findUserInfoFromServer(String userAccesstoken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + userAccesstoken
                + "&openid=" + openId;
        log.debug("【发起微信请求】 :" + url);
        String response = new HttpClientWrap().httpGetRequest(url);
        log.debug("【接收微信回应】 :" + response);
        return response;
    }
}
