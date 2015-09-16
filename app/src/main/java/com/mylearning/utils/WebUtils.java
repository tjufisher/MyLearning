package com.mylearning.utils;

import com.mylearning.common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by user on 2015/8/20.
 */
public class WebUtils {
    private static String url = Constants.getURL();
    private static HttpURLConnection connection = null;

    public static String doGet(Map<String, String> map){
        url = Constants.getURL();
        try {
            url += "?" + buildParameters(map);
            URL u = new URL(url);
            LogUtils.e("get_url",url);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod(Constants.REQUEST_GET);
            connection.setConnectTimeout(5 * 1000);
            connection.setRequestProperty("contentType", Constants.ENCODING);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String strLine = "";
        String strResponse = "";
        InputStream in;
        try {
            in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, Constants.ENCODING));
            while ((strLine = reader.readLine()) != null)
            {
                strResponse += strLine + "\n";
            }
            LogUtils.e("json", "WebUtils response:" + strResponse);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return changeResponse(strResponse);
    }

    public static String doPost(Map<String, String> map){
        url = Constants.getURL();
        try {
            URL u = new URL(url);
            LogUtils.e("url",url);
            connection = (HttpURLConnection) u.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(Constants.REQUEST_POST);
            connection.setConnectTimeout(5 * 1000);
            connection.setRequestProperty("contentType", Constants.ENCODING);
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(outputStream, Constants.ENCODING);
            out.write(buildParameters(map));
            out.flush();
            out.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String strLine = "";
        String strResponse = "";
        InputStream in;
        try {
            in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, Constants.ENCODING));
            while ((strLine = reader.readLine()) != null)
            {
                strResponse += strLine + "\n";
            }
            LogUtils.e("json", "WebUtils response:" + strResponse);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return changeResponse(strResponse);
    }

    public static String changeResponse(String strResponse){
        if(Constants.DEVELOP_POSITION == Constants.DEVELOP_POSITION_DEFAULT){

        }else if(Constants.DEVELOP_POSITION == Constants.DEVELOP_POSITION_WORK) {
            strResponse = strResponse.replace(Constants.IP_DEFAULT, Constants.IP_WORK+":"+Constants.PORT);
        }else if(Constants.DEVELOP_POSITION == Constants.DEVELOP_POSITION_HOME){
            strResponse = strResponse.replace(Constants.IP_DEFAULT, Constants.IP_HOME+":"+Constants.PORT);
        }else{

        }
        return strResponse;
    }

    public static String buildParameters(Map<String,String> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> entry : map.entrySet()){
            sb.append(URLEncoder.encode(entry.getKey(), Constants.ENCODING))
                    .append("=").
                    append(URLEncoder.encode(entry.getValue(), Constants.ENCODING))
                    .append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        LogUtils.e("json", "WebUtils parameters:" + sb.toString());
        return sb.toString();
    }
}
