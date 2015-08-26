package com.mylearning.common;

/**
 * Created by user on 2015/8/20.
 */
public class Constanse {

    public static final String MESSAGE_NAME = "messageName";
    public static Boolean IS_HOME = false;

    public static final String ENCODING = "UTF-8";
    public static final String REQUEST_METHOD = "POST";

    public static final String HTTP_SAFETY_SCHEME = "https";
    public static final String HTTP_SCHEME = "http";

//    public static final String IP = "127.0.0.1";
    public static final String IP = "192.168.115.230";
    public static final String IP_HOME = "192.168.1.108";

    public static final String PORT = "8080";
    public static final String METHOD = "/HTTP/GetDataServlet";

    private static String HTTP_URL = HTTP_SCHEME + "://" + IP + ":" + PORT + METHOD;
//    private String HTTP_URL = "http://127.0.0.1:8080/HTTP/GetDataServlet";

    public static String getURL(){
        if(IS_HOME){
            HTTP_URL = HTTP_SCHEME + "://" + IP_HOME + ":" + PORT + METHOD;
        }else{
            HTTP_URL = HTTP_SCHEME + "://" + IP + ":" + PORT + METHOD;
        }
        return HTTP_URL;
    }
}
