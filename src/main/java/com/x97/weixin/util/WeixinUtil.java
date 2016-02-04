package com.x97.weixin.util;

import com.x97.util.encode.EncryptUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by yanggch on 2015/6/22.
 */
public class WeixinUtil {
    private static Log log = LogFactory.getLog(WeixinUtil.class);

    public static String createRandomString() {
        String s = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            int startIndex = Math.abs(random.nextInt()) % s.length();
            randomString.append(s.substring(startIndex, startIndex + 1));
        }
        return randomString.toString();
    }

}
