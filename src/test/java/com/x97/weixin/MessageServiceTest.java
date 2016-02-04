package com.x97.weixin;

import org.junit.Test;

/**
 * Created by yanggch on 2015/11/23.
 */
public class MessageServiceTest {

    @Test
    public void sentTextMessageTest(){
        String toUser="om-LosoJ6Or26NbKfQPf-6K9lOhY";
        String message="吐槽者死";
        WeixinMessageService weixinMessageService=(WeixinMessageService) SpringInitContext.initContext().getBean("weixinMessageService");
        weixinMessageService.sendTextMessage(toUser,message);
    }

}
