package com.x97.weixin;

import com.x97.weixin.domain.WeixinUserGroup;
import org.junit.Test;

/**
 * Created by yanggch on 2016/1/30.
 */
public class WeixinUserTest {
    @Test
    public void userGroupCreateTest(){
        try {
            WeixinUserService weixinUserService = (WeixinUserService) SpringInitContext.initContext().getBean("weixinUserService");
            WeixinUserGroup weixinUserGroup = weixinUserService.createUserGroup("功能验证组");
            assert weixinUserGroup!=null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void moveUserToGroupTest(){
        try {
            WeixinUserService weixinUserService = (WeixinUserService) SpringInitContext.initContext().getBean("weixinUserService");
            boolean result= weixinUserService.movenUserToGroup("om-LosoJ6Or26NbKfQPf-6K9lOhY",101);
            assert result;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
