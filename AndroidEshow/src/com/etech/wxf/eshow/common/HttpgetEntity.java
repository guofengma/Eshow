package com.etech.wxf.eshow.common;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.util.Log;

public class HttpgetEntity {

	public static String doGet(String url)
            throws Exception {
        String result = null;
        // 获取HttpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        // 新建HttpPost对象
        HttpGet httpGet = new HttpGet(url);

        /*// 连接超时
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        // 请求超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                3000);*/
        // 获取HttpResponse实例
        HttpResponse httpResp = httpClient.execute(httpGet);
        Log.e("status",httpResp.getStatusLine().getStatusCode()+" ");
        // 判断是够请求成功
        if (httpResp.getStatusLine().getStatusCode() == 200) {
            // 获取返回的数�?
            result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
        } else {
            Log.e("HttpGet", "HttpGet方式请求失败");
        }

        return result;
    }
}
