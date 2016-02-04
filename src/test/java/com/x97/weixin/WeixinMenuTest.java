package com.x97.weixin;

import org.junit.Test;

/**
 * Created by yanggch on 2016/1/30.
 */
public class WeixinMenuTest {
    @Test
    public void createContdtionMenu() {
        String menu = "{\n" +
                "    \"button\": [\n" +
                "        {\n" +
                "            \"name\": \"点餐\",\n" +
                "            \"sub_button\": [\n" +
                "                {\n" +
                "                    \"type\": \"view\",\n" +
                "                    \"name\": \"店内点餐\",\n" +
                "                    \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc1aa452d8fce87a1&redirect_uri=http%3a%2f%2fwww.x9710.cn%2fdiancan%2findex.php%3faction%3deatHere&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": \"view\",\n" +
                "                    \"name\": \"外卖送货\",\n" +
                "                    \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc1aa452d8fce87a1&redirect_uri=http%3a%2f%2fwww.x9710.cn%2fdiancan%2findex.php%3faction%3dselectAddress&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": \"view\",\n" +
                "                    \"name\": \"打包带走\",\n" +
                "                    \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc1aa452d8fce87a1&redirect_uri=http%3a%2f%2fwww.x9710.cn%2fdiancan%2findex.php%3faction%3deatReserve&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"后台\",\n" +
                "            \"sub_button\": [\n" +
                "                {\n" +
                "                    \"type\": \"view\",\n" +
                "                    \"name\": \"后厨\",\n" +
                "                    \"url\": \"http://www.x9710.cn/diancan/kitchen.php?action=index\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"我\",\n" +
                "            \"sub_button\": [\n" +
                "                {\n" +
                "                    \"type\": \"view\",\n" +
                "                    \"name\": \"当日订单\",\n" +
                "                    \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc1aa452d8fce87a1&redirect_uri=http%3a%2f%2fwww.x9710.cn%2fdiancan%2findex.php%3faction%3dcurrentOrders&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\": \"view\",\n" +
                "                    \"name\": \"历史订单\",\n" +
                "                    \"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc1aa452d8fce87a1&redirect_uri=http%3a%2f%2fwww.x9710.cn%2fdiancan%2findex.php%3faction%3dallOrders&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"matchrule\": {\n" +
                "        \"group_id\": \"101\"\n" +
                "    }\n" +
                "}";
        try {

            WeiMenuService weixinMenuService = (WeiMenuService) SpringInitContext.initContext().getBean("weixinMenuService");
            boolean result = weixinMenuService.createConditionMenu(menu);
            assert result;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findMenusTest() {
        try {
            WeiMenuService weixinMenuService = (WeiMenuService) SpringInitContext.initContext().getBean("weixinMenuService");
            String result = weixinMenuService.findMenus();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCondtitionMenu() {
        try {
            WeiMenuService weixinMenuService = (WeiMenuService) SpringInitContext.initContext().getBean("weixinMenuService");
            String result = weixinMenuService.findMenus();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAllMenu() {
        try {
            WeiMenuService weixinMenuService = (WeiMenuService) SpringInitContext.initContext().getBean("weixinMenuService");
            boolean result = weixinMenuService.deleteAllMenu();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
