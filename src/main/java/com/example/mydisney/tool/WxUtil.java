package com.example.mydisney.tool;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

@Service
public class WxUtil {

    public String URLConnection(String url, String method, JSONObject params){
        try {
            URL connectionUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) connectionUrl.openConnection();
            // 设置请求方法（GET是获取页面内容的常用方法）
            connection.setRequestMethod(method);
            if(params !=null){
                // 设置请求方法为POST
                connection.setRequestMethod("POST");

                // 设置请求头，指定JSON格式
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                // 启用输入流，以便写入JSON数据
                connection.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());

                // 将JSON数据写入请求体
                out.writeBytes(params.toJSONString());
                out.flush();
                out.close();

            }
            // 从输入流读取返回内容
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            StringBuilder buffer = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            connection.disconnect();
            return buffer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }






}
