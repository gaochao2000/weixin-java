package com.x97.weixin.domain;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 微信统一下单对象
 * 根据文档https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1 中对统一下单的参数说明生成
 */
public class UnifiedOrderRequest {
    private String appid;
    private String mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String body;
    private String detail;
    private String attach;
    private String out_trade_no;
    private String fee_type;
    private String total_fee;
    private String spbill_create_ip;
    private String time_start;
    private String time_expire;
    private String goods_tag;
    private String notify_url;
    private String trade_type;
    private String product_id;
    private String limit_pay;
    private String openid;

    private String[] parameterNames = new String[]{"appid", "mch_id", "device_info", "nonce_str", "sign", "body", "detail", "attach", "out_trade_no", "fee_type", "total_fee", "spbill_create_ip", "time_start", "time_expire", "goods_tag", "notify_url", "trade_type", "product_id", "openid"};

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;

    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * 生成统一下单post的内容
     * 必须保证未判断是否为空的参数有值
     *
     * @return 统一下单post的xml内容字符串
     */
    public String createUnifiedOrderPostContent() throws Exception {

        if (appid != null && mch_id != null && device_info != null && nonce_str != null && sign != null && body != null && out_trade_no != null && total_fee != null && spbill_create_ip != null && notify_url != null && trade_type != null && openid != null) {
            Element root = DocumentHelper.createElement("xml");

            Element element = DocumentHelper.createElement("appid");
            element.setText(appid);
            root.add(element);

            element = DocumentHelper.createElement("mch_id");
            element.setText(mch_id);
            root.add(element);

            element = DocumentHelper.createElement("device_info");
            element.setText(device_info);
            root.add(element);

            element = DocumentHelper.createElement("nonce_str");
            element.setText(nonce_str);
            root.add(element);

            element = DocumentHelper.createElement("sign");
            element.setText(sign);
            root.add(element);

            element = DocumentHelper.createElement("body");
            element.setText(body);
            root.add(element);

            if (detail != null) {
                element = DocumentHelper.createElement("detail");
                element.setText(detail);
                root.add(element);
            }

            if (attach != null) {
                element = DocumentHelper.createElement("attach");
                element.setText(attach);
                root.add(element);
            }

            element = DocumentHelper.createElement("out_trade_no");
            element.setText(out_trade_no);
            root.add(element);

            if (fee_type != null) {
                element = DocumentHelper.createElement("fee_type");
                element.setText(fee_type);
                root.add(element);
            }

            element = DocumentHelper.createElement("total_fee");
            element.setText(total_fee);
            root.add(element);

            element = DocumentHelper.createElement("spbill_create_ip");
            element.setText(spbill_create_ip);
            root.add(element);

            if (time_start != null) {
                element = DocumentHelper.createElement("time_start");
                element.setText(time_start);
                root.add(element);
            }

            if (time_expire != null) {
                element = DocumentHelper.createElement("time_expire");
                element.setText(time_expire);
                root.add(element);
            }

            if (goods_tag != null) {
                element = DocumentHelper.createElement("goods_tag");
                element.setText(goods_tag);
                root.add(element);
            }

            element = DocumentHelper.createElement("notify_url");
            element.setText(notify_url);
            root.add(element);

            element = DocumentHelper.createElement("trade_type");
            element.setText(trade_type);
            root.add(element);

            if (product_id != null) {
                element = DocumentHelper.createElement("product_id");
                element.setText(product_id);
                root.add(element);
            }

            if (limit_pay != null) {
                element = DocumentHelper.createElement("limit_pay");
                element.setText(limit_pay);
                root.add(element);
            }

            if (openid != null) {
                element = DocumentHelper.createElement("openid");
                element.setText(openid);
                root.add(element);
            }
            return root.asXML();
        } else {
            throw new Exception("有必填的参数值为空");
        }
    }
}
