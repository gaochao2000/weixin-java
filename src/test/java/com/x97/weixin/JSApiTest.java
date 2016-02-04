package com.x97.weixin;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanggch on 2015/12/18.
 */
public class JSApiTest {
    @Test
    public void testSingn(){
        Map<String,String> config=new HashMap<String, String>();
        config.put("timestamp", "1450432640");
        config.put("noncestr", "2gybIhyrJ9J4sFsSrQ06tw7nZAONNmYS");
        config.put("url", "http://jjvote.analyticservice.net/");
        config.put("jsapi_ticket", "IpK_1T69hDhZkLQTlwsAXzX-p6lffIE9dCTTt-qwJooGnJVEXNQp39om8qMRKggT8feQjOOh_Jb_bgBwKAyd-g");
        WeixinJSAPIService shopMainService = (WeixinJSAPIService) SpringInitContext.initContext().getBean("weixinJSAPIService");
        System.out.println(shopMainService.createJSApiSing(config) );

    }
}
