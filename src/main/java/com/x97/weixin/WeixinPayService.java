package com.x97.weixin;

import com.x97.util.encode.MD5Util;
import com.x97.util.web.HttpClientWrap;
import com.x97.weixin.domain.PayNotifyResponse;
import com.x97.weixin.domain.UnifiedOrderRequest;
import com.x97.weixin.domain.UnifiedOrderResponse;
import com.x97.weixin.util.WeixinUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by yanggch on 2015/10/26.
 */
public class WeixinPayService {
    private String appId;
    private String mchId;
    private String mchKey;
    private String payNotifyURL;
    private static Log log = LogFactory.getLog(WeixinPayService.class);

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public void setPayNotifyURL(String payNotifyURL) {
        this.payNotifyURL = payNotifyURL;
    }

    /**
     * 微信支付前进行的统一下单操作
     *
     * @param unifiedOrderRequest 微信统一下单请求对象，一般根据订单信息生成
     * @return
     */
    public UnifiedOrderResponse webUnifiedOrder(UnifiedOrderRequest unifiedOrderRequest) {

        unifiedOrderRequest.setAppid(appId);
        unifiedOrderRequest.setMch_id(mchId);
        unifiedOrderRequest.setNotify_url(payNotifyURL);
        unifiedOrderRequest.setNonce_str(WeixinUtil.createRandomString());
        unifiedOrderRequest.setSign(createPaySign(createUnifiedOrderMap(unifiedOrderRequest)));

        try {
            String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            log.debug("【发起微信请求】:" + url);
            String requestData = unifiedOrderRequest.createUnifiedOrderPostContent();
            log.debug("【微信请求数据】:" + requestData);
            String responseText = new HttpClientWrap().httpPostRequest(url, requestData);
            log.debug("【接收微信回应】:" + responseText);
            return generateUnifiedOrderResponse(responseText);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }

        return null;

    }

    public String createJSAPIPayInfo(String prepay_id, Float amount) throws Exception {
        Map<String, String> signMap = new HashMap<String, String>();
        JSONObject payJson = new JSONObject();
        payJson.put("appId", appId);
        payJson.put("timestamp", new Date().getTime());
        payJson.put("nonceStr", WeixinUtil.createRandomString());
        payJson.put("package", "prepay_id=" + prepay_id);
        payJson.put("signType", "MD5");

        signMap.put("appId", appId);
        signMap.put("timeStamp", String.valueOf(payJson.getLong("timestamp")));
        signMap.put("nonceStr", payJson.getString("nonceStr"));
        signMap.put("package", payJson.getString("package"));
        signMap.put("signType", payJson.getString("signType"));
        payJson.put("paySign", createPaySign(signMap));
        payJson.put("amount", amount);
        return payJson.toString();
    }


    private UnifiedOrderResponse generateUnifiedOrderResponse(String weixinUnifiedOrderResponse) throws Exception {
        UnifiedOrderResponse unifiedOrderResponse = new UnifiedOrderResponse();
        Document document = DocumentHelper.parseText(weixinUnifiedOrderResponse);
        Element root = document.getRootElement();

        unifiedOrderResponse.setReturn_code(root.elementText("return_code"));
        if (unifiedOrderResponse.getReturn_code().equals("SUCCESS")) {
            unifiedOrderResponse.setAppid(root.elementText("appid"));
            unifiedOrderResponse.setMch_id(root.elementText("mch_id"));
            unifiedOrderResponse.setDevice_info(root.elementText("device_info"));
            unifiedOrderResponse.setNonce_str(root.elementText("nonce_str"));
            unifiedOrderResponse.setSign(root.elementText("sign"));
            unifiedOrderResponse.setResult_code(root.elementText("result_code"));
            if (unifiedOrderResponse.getResult_code().equals("SUCCESS")) {
                unifiedOrderResponse.setTrade_type(root.elementText("trade_type"));
                unifiedOrderResponse.setPrepay_id(root.elementText("prepay_id"));
                unifiedOrderResponse.setCode_url(root.elementText("code_url"));
            } else {
                unifiedOrderResponse.setErr_code(root.elementText("err_code"));
                unifiedOrderResponse.setErr_code_des(root.elementText("err_code_des"));
            }
        } else {
            unifiedOrderResponse.setReturn_msg(root.elementText("return_msg"));
        }

        return unifiedOrderResponse;
    }

    private Map<String, String> createUnifiedOrderMap(UnifiedOrderRequest unifiedOrderRequest) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("appid", unifiedOrderRequest.getAppid());
        parameters.put("mch_id", unifiedOrderRequest.getMch_id());
        parameters.put("device_info", unifiedOrderRequest.getDevice_info());
        parameters.put("nonce_str", unifiedOrderRequest.getNonce_str());
        parameters.put("body", unifiedOrderRequest.getBody());

        if (unifiedOrderRequest.getDetail() != null) {
            parameters.put("detail", unifiedOrderRequest.getDetail());
        }

        if (unifiedOrderRequest.getAttach() != null) {
            parameters.put("attach", unifiedOrderRequest.getAttach());
        }
        parameters.put("out_trade_no", unifiedOrderRequest.getOut_trade_no());
        parameters.put("fee_type", unifiedOrderRequest.getFee_type());
        parameters.put("total_fee", unifiedOrderRequest.getTotal_fee());
        parameters.put("spbill_create_ip", unifiedOrderRequest.getSpbill_create_ip());
        parameters.put("time_start", unifiedOrderRequest.getTime_start());
        parameters.put("time_expire", unifiedOrderRequest.getTime_expire());
        parameters.put("notify_url", unifiedOrderRequest.getNotify_url());
        parameters.put("trade_type", unifiedOrderRequest.getTrade_type());
        parameters.put("openid", unifiedOrderRequest.getOpenid());

        return parameters;
    }


    /**
     * 服务器收到微信的支付信息后，调用该接口通知微信服务器已经收到该信息
     *
     * @param payNotifyResponse 微信支付通知反馈信息对象
     * @return
     */
    public boolean responesPayNotify(PayNotifyResponse payNotifyResponse) {
        return false;
    }


    private String createPaySign(Map<String, String> parameters) {

        StringBuilder paraStr = new StringBuilder();

        List<String> keys = new ArrayList<String>(parameters.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            paraStr.append(key).append("=").append(parameters.get(key)).append("&");
        }
        paraStr.append("key=").append(mchKey);
        log.debug("ori string : " + paraStr.toString());
        String md5String = MD5Util.MD5Encode(paraStr.toString(), "UTF-8").toUpperCase();
        log.debug("md5 string : " + md5String);
        return md5String;
    }


}
