package com.x97.util.web;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * http请求包装类
 */
public class HttpClientWrap {

    /**
     * 发起一个 http get 请求
     * @param url
     * @return
     */
    public String httpGetRequest(String url) {

        try {
            HttpClient httpclient = new HttpClient();
            GetMethod method = new GetMethod(url);
            method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(3, false));

            int statusCode = httpclient.executeMethod(method);
            if (statusCode == 200) {
                return parseInputStream(method.getResponseBodyAsStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发起一个 http post 请求. post 数据为原始数据格式
     * @param url http 请求地址
     * @param data post 的原始数据
     * @return
     */
    public String httpPostRequest(String url, String data) {

        try {
            HttpClient httpclient = new HttpClient();
            PostMethod method = new PostMethod(url);
            method.setRequestHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");

            method.setRequestEntity(new StringRequestEntity(data, "json", "utf-8"));


            int statusCode = httpclient.executeMethod(method);
            if (statusCode == 200) {
                return parseInputStream(method.getResponseBodyAsStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String httpPostRequest(String url, Map<String, String> parameters) {

        try {
            HttpClient httpclient = new HttpClient();
            PostMethod method = new PostMethod(url);
            method.setRequestHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");

            for (String key : parameters.keySet()) {
                method.addParameter(key, parameters.get(key));
            }

            int statusCode = httpclient.executeMethod(method);
            if (statusCode == 200) {
                return parseInputStream(method.getResponseBodyAsStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void writeJsonResponse(HttpServletResponse response, String json) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(json);
    }

    public void writeXMLResponse(HttpServletResponse response, String xml) throws Exception {
        response.setContentType("application/xml");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(xml);
    }

    public void writeResponse(HttpServletResponse response, String value) throws Exception {
        System.out.println("response text : " + value);
        response.getWriter().print(value);
    }

    public String parseInputStream(InputStream is) throws Exception {
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder respongseContext = new StringBuilder();

        while ((line = rd.readLine()) != null) {
            respongseContext.append(line).append("\n");
        }
        rd.close();
        if (respongseContext.length() > 0) {
            respongseContext.deleteCharAt(respongseContext.length() - 1);
        }
        return respongseContext.toString();
    }
}
