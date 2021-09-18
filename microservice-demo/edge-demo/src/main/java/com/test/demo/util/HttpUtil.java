package com.test.demo.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @description:
 * @author: luofang
 * @create: 2019-09-25 20:25
 **/
public class HttpUtil {

    public static String sendHttpPostJsonData(String requestUrl, String json) {
        String result = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost httpPost = new HttpPost(requestUrl);
            if (StringUtils.isNotBlank(json)) {
//                httpPost.addHeader("Content-type", "application/json;charset=UTF-8");
//                StringEntity entity = new StringEntity(json);
//                entity.setContentType("application/json;charset=UTF-8");
                StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                httpPost.setEntity(entity);
            }

            setRequestConfig(httpPost);
            response = httpClient.execute(httpPost);
            result = getResult(response);
        } catch (ClientProtocolException var25) {
            var25.printStackTrace();
        } catch (ParseException var26) {
            var26.printStackTrace();
        } catch (IOException var27) {
            var27.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException var24) {
                    var24.printStackTrace();
                }
            }

            try {
                httpClient.close();
            } catch (IOException var23) {
                var23.printStackTrace();
            }

        }

        return result;
    }

    public static void setRequestConfig(HttpPost httpPost) {
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectionRequestTimeout(100000);
        builder.setConnectTimeout(100000);
        builder.setSocketTimeout(100000);
        httpPost.setConfig(builder.build());
    }

    public static String getResult(CloseableHttpResponse response) throws ParseException, IOException {
        String result = null;
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() == 200) {
            HttpEntity httpEntity = response.getEntity();
            if (null != httpEntity) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }

        return result;
    }
}
