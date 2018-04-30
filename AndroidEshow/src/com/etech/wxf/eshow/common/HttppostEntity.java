package com.etech.wxf.eshow.common;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class HttppostEntity {
	
	
	public static String doPost(JSONObject obj, String url)
            throws Exception {
        String result = null;
        // 获取HttpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        // 新建HttpPost对象
        HttpPost httpPost = new HttpPost(url);
        
        if (obj != null) {
            // 设置字符�?
            StringEntity entity = new StringEntity(obj.toString(), "utf-8");
            // 设置参数实体
            httpPost.setEntity(entity);
        }

        /*// 连接超时
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        // 请求超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                3000);*/
        // 获取HttpResponse实例
        HttpResponse httpResp = httpClient.execute(httpPost);
        Log.e("status",httpResp.getStatusLine().getStatusCode()+" ");
        // 判断是够请求成功
        if (httpResp.getStatusLine().getStatusCode() == 200) {
            // 获取返回的数�?
            result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
        } else {
            Log.e("HttpPost", "HttpPost方式请求失败");
        }

        return result;
    }
	
	public static String doPostA(JSONArray obj, String url)
            throws Exception {
        String result = null;
        // 获取HttpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        // 新建HttpPost对象
        HttpPost httpPost = new HttpPost(url);
        
        if (obj != null) {
            // 设置字符�?
            StringEntity entity = new StringEntity(obj.toString(), "utf-8");
            Log.e("entity", obj.toString());
            // 设置参数实体
            httpPost.setEntity(entity);
        }

        /*// 连接超时
        httpClient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
        // 请求超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                3000);*/
        // 获取HttpResponse实例
        HttpResponse httpResp = httpClient.execute(httpPost);
        Log.e("status",httpResp.getStatusLine().getStatusCode()+" ");
        // 判断是够请求成功
        if (httpResp.getStatusLine().getStatusCode() == 200) {
            // 获取返回的数�?
            result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
        } else {
            Log.e("HttpPost", "HttpPost方式请求失败");
        }

        return result;
    }

}
