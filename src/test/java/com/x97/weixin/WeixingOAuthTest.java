package com.x97.weixin;

import org.junit.Test;

/**
 * Created by yanggch on 2015/11/23.
 */
public class WeixingOAuthTest {
    @Test
    public void oAuthURLTest(){
        String redirect="http://localhost:8080/administrator/sss.html?ss=1&sdf=1";
        WeixinOAuthService tokenCacheService= (WeixinOAuthService) SpringInitContext.initContext().getBean("weixinOAuthService");
        System.out.println(tokenCacheService.generateOAuthUserInfoURL(redirect));
    }
}
