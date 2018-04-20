package com.sdyijia.wxapp.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class HttpUtil {
    /**
     * 访问指定的接口返回相对应的数据
     *
     * @param url
     * @return
     */
    public static JSON request(String url) {

        HttpClient client = new HttpClient();
        try {
            URL wxlogin = new URL(url);
            URLConnection a = wxlogin.openConnection();
            GetMethod get = new GetMethod(url);
            int code = client.executeMethod(get);
            if (code == 200) {
                return JSON.parseObject(get.getResponseBodyAsString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.parseObject("");
    }

    public static void main(String[] args) {
        System.out.println(HttpUtil.request("http://localhost/user/login"));
    }
}
