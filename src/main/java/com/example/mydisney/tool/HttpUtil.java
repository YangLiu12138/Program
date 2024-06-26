package com.example.mydisney.tool;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class HttpUtil {


    private static OkHttpClient client;

    private static final String DEFAULT_MEDIA_TYPE = "application/json; charset=utf-8";

    private static final int CONNECT_TIMEOUT = 5;

    private static final int READ_TIMEOUT = 7;

    private static final String GET = "GET";

    private static final String POST = "POST";

    /**
     * 单例模式  获取类实例
     *
     * @return client
     */
    private static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (OkHttpClient.class) {
                if (client == null) {
                    client = new OkHttpClient.Builder()
                            .build();
                }
            }
        }
        return client;
    }

    public static String doGet(String url, String callMethod) {

        try {

            Request request = new Request.Builder().url(url).build();
            // 创建一个请求
            Response response = getInstance().newCall(request).execute();
            int httpCode = response.code();
            String result;
            ResponseBody body = response.body();
            if (body != null) {
                result = body.string();
            } else {
                response.close();
                throw new RuntimeException("exception in OkHttpUtil,response body is null");
            }
            return handleHttpResponse(httpCode, result);
        } catch (Exception ex) {
            return StringUtils.EMPTY;
        }
    }


    public static String doPost(String url, String postBody, String mediaType, String callMethod) throws HttpStatusException {
        try {
            long startTime = System.currentTimeMillis();

            MediaType createMediaType = MediaType.parse(mediaType == null ? DEFAULT_MEDIA_TYPE : mediaType);
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(createMediaType, postBody))
                    .build();

            Response response = getInstance().newCall(request).execute();
            int httpCode = response.code();
            String result;
            ResponseBody body = response.body();
            if (body != null) {
                result = body.string();
            } else {
                response.close();
                throw new IOException("exception in OkHttpUtil,response body is null");
            }
            return handleHttpResponse(httpCode, result);
        } catch (Exception ex) {
            return StringUtils.EMPTY;
        }
    }

    public static String doPost(String url, Map<String, String> parameterMap, String callMethod) throws HttpStatusException {
        try {
            List<String> parameterList = new ArrayList<>();
            FormBody.Builder builder = new FormBody.Builder();
            if (parameterMap.size() > 0) {
                for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
                    String parameterName = entry.getKey();
                    String value = entry.getValue();
                    builder.add(parameterName, value);
                    parameterList.add(parameterName + ":" + value);
                }
            }


            FormBody formBody = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            Response response = getInstance().newCall(request).execute();
            String result;
            int httpCode = response.code();
            ResponseBody body = response.body();
            if (body != null) {
                result = body.string();
            } else {
                response.close();
                throw new IOException("exception in OkHttpUtil,response body is null");
            }
            return handleHttpResponse(httpCode, result);
        } catch (Exception ex) {
            return StringUtils.EMPTY;
        }
    }


    private static String handleHttpResponse(int httpCode, String result) throws HttpStatusException {
        if (httpCode == HttpStatus.OK.value()) {
            return result;
        }
        return null;
    }


}



