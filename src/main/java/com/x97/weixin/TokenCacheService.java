package com.x97.weixin;

/**
 * 微信Token缓存接口
 */
public interface TokenCacheService {

    /**
     * 保存 api 调用的 AccessToken
     *
     * @param accessToken 要保存的 AccessToken
     * @param expiryTime  过期时间。不应该大于微信允许的该 Token 的过期时间
     * @return 保存成功返回 true ，否则返回 false
     */
    boolean saveAccessToken(String accessToken, int expiryTime);

    /**
     * 保存 JSAPI 调用的 JSAPIToken
     *
     * @param jsapiToken 要保存的 JSAPIToken
     * @param expiryTime 过期时间。不应该大于微信允许的该 Token 的过期时间
     * @return 保存成功返回 true ，否则返回 false
     */
    boolean saveJSAPIToken(String jsapiToken, int expiryTime);


    /**
     * 获取 AccessToken
     *
     * @return 如果获取成功，返回该 Token ，否则返回 null
     */
    String getAccessToken();

    /**
     * 获取 jsapiToken
     *
     * @return 如果获取成功，返回该 Token ，否则返回 null
     */
    String getJSAPIToken();


}
