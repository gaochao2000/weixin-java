package com.x97.weixin;

import java.util.Date;

/**
 * 微信TOKEN本地内存缓存实现类
 * 这种实现一个公众号只能在一个 JVM 中使用。
 * 如果多个 JVM 中使用，则会导致不同 JVM 中 token 的冲突
 */
public class TokenCacheServiceMemberImpl implements TokenCacheService {
    private static String accessToken;
    private static Date accessTokenExpiryTime;
    private static String jsapiToken;
    private static Date jsapiExpiryTime;

    public boolean saveAccessToken(String accessToken, int expiryTime) {
        this.accessToken = accessToken;
        accessTokenExpiryTime = new Date(new Date().getTime() + expiryTime * 1000L - 120 * 1000);
        return true;
    }


    public boolean saveJSAPIToken(String jsapiToken, int expiryTime) {
        this.jsapiToken = jsapiToken;
        jsapiExpiryTime = new Date(new Date().getTime() + expiryTime * 1000L - 120 * 1000);
        return true;
    }


    public String getAccessToken() {
        if (accessToken == null || new Date().after(accessTokenExpiryTime)) {
            return null;
        } else {
            return accessToken;
        }
    }

    public String getJSAPIToken() {
        if (jsapiToken == null || new Date().after(jsapiExpiryTime)) {
            return null;
        } else {
            return jsapiToken;
        }
    }

}
