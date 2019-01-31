package com.liulei.fly;

import com.nimbusds.jose.JOSEException;
import com.telek.business.jwtUtil.JwtUtil;
import com.telek.business.message.Param;
import com.telek.business.protoUtil.SerializeUtil;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liu_l
 * @email: liu_lei_programmer@163.com
 * @time 2019/1/21 17:00
 * @Description: 描述: 发送http请求测试类
 */
public class Test {

    private static final Log log = LogFactory.getLog(Test.class);

    private static final String charssetName = "ISO-8859-1";

    public static void main(String[] args) throws IOException {
        MyHttpClient myHttpClient = new MyHttpClient("192.168.1.96", 3333);
        try {
            myHttpClient.setCookies(getCookie());
        } catch (JOSEException e) {
            log.error("创建cookie失败");
            e.printStackTrace();
        }
        Map<String,String> paras = new HashMap<String,String>();
        String param = getParam();
        paras.put("param", param);
        String url = "/sanlv/getContructionItem";
        String result = myHttpClient.doPost(url,paras);
        System.out.println(result);
    }

    private static String getCookie() throws JOSEException {
        JSONObject payload=new JSONObject();
        Date date=new Date();
        payload.put("cookie", "291969452");//用户id
        payload.put("userName", "41311016120100001");//登录账号
        payload.put("loginCompcode", "41311016120100001");//查询单位
        payload.put("compcode", "41311016120100001");//查询单位
        payload.put("ext",date.getTime()+1000*60*60);//过期时间1小时
        String token=JwtUtil.createToken(payload);
        return token;
    }

    private static String getParam() throws UnsupportedEncodingException {
        SerializeUtil.initProtoParam(false);
        Param param = new Param();
        //param.setLoginCompcode("41311016120100001");
        //param.setCompcode("41311016120100001");
        param.setItemCode("asdf");
        param.setItemName("我是你爸爸");
        byte[] bytes = SerializeUtil.serialize(param);
        String bytes2String = new String(bytes, charssetName);
        return bytes2String;
    }
}
