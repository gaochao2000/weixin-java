package com.x97.weixin;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2014/6/16 0016.
 */
public class SpringInitContext {
    public static ApplicationContext initContext() {
        return new ClassPathXmlApplicationContext(new String[]{"/weixing_service.xml"});
    }
}
