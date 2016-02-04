package com.x97.weixin;

import org.junit.Test;

/**
 * Created by yanggch on 2015/11/23.
 */
public class TokenServiceTest {

    @Test
    public void testTokenCache(){
        String ip="123.56.158.135";
        int port=7543;
        String context="slsldkfjsd";
        TokenCacheService tokenCacheService= (TokenCacheService)SpringInitContext.initContext().getBean("tokenCacheService");
        tokenCacheService.saveAccessToken(context,240);
        assert context.equals(tokenCacheService.getAccessToken());
    }
}
