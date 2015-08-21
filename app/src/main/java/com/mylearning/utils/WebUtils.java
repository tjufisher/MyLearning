package com.mylearning.utils;

import com.mylearning.common.Constanse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by user on 2015/8/20.
 */
public class WebUtils {
    private static String url = Constanse.HTTP_URL;
    private static HttpURLConnection connection = null;

    public static String doPost(Map<String, String> map){
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(Constanse.REQUEST_METHOD);
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(outputStream, Constanse.ENCODING);
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
                    new InputStreamReader(in));
            while ((strLine = reader.readLine()) != null)
            {
                strResponse += strLine + "\n";
            }
            LogUtils.e("json", "WebUtils response:" + strResponse);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strResponse;
    }

    public static String buildParameters(Map<String,String> map){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> entry : map.entrySet()){
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sb.deleteCharAt(sb.length()-1);
        LogUtils.e("json","WebUtils parameters:" + sb.toString());
        return sb.toString();
    }
}
