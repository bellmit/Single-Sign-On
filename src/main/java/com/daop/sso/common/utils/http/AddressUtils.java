package com.daop.sso.common.utils.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @BelongsProject: iot
 * @BelongsPackage: com.xinye.iot.common.utils
 * @Description: di
 * @DATE: 2020-06-08
 * @AUTHOR: Administrator
 **/
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";


    public static String getRealAddressByIp(String ip) {
        String address = "XX XX";
        StringBuilder sb = new StringBuilder("ip=" + ip + "&level=3&json=true");
        String charsetName = "GBK";

        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        String rspStr = HttpUtils.sendPost(IP_URL, sb.toString(), charsetName);
        if (StringUtils.isEmpty(rspStr)) {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }
        try {
            Map map = JSONObject.parseObject(rspStr, Map.class);

            address = map.get("pro") + " " + map.get("city");
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return address;
    }
}
