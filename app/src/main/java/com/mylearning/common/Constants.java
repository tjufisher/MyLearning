package com.mylearning.common;

import android.os.Environment;

/**
 * Created by user on 2015/8/20.
 */
public class Constants {

    public static final String MESSAGE_NAME = "messageName";
    public static Boolean IS_HOME = false;

    public static final String ENCODING = "UTF-8";
    public static final String REQUEST_METHOD = "POST";

    public static final String HTTP_SAFETY_SCHEME = "https";
    public static final String HTTP_SCHEME = "http";

//    public static final String IP = "127.0.0.1";
    public static final String IP = "192.168.115.230";
    public static final String IP_HOME = "192.168.1.104";

    public static final String PORT = "8080";
    public static final String METHOD = "/HTTP/GetDataServlet";

    private static String HTTP_URL = HTTP_SCHEME + "://" + IP + ":" + PORT + METHOD;

    /** SD卡目录 */
    public static final String SD_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
    /** 根目录 */
    public static final String ROOT_DIR_PATH = "/mylearning/res";
    /** 缓存目录 */
    public static final String CACHE_DIR_PATH = ROOT_DIR_PATH + "/cache";
    /** 图片缓存目录 */
    public static final String PIC_CACHE_DIR_PATH = CACHE_DIR_PATH + "/pic_cache";
    /** 收藏图片缓存目录 */
    public static final String STORE_PIC_CACHE_DIR_PATH = CACHE_DIR_PATH + "/store_pic_cache";

    /** 列表缓存目录 */
    public static final String STORE_LIST_CACHE_DIR_PATH = CACHE_DIR_PATH + "/list_cache";

    public static final String CACHE_WAP_AND_IMG = CACHE_DIR_PATH + "/wap/";

    /**
     * 闪屏页广告缓存
     */
    public static final String SPLASH_CACHE_AD = CACHE_DIR_PATH + "/splash_ads";

    /** 多媒体沟通，图片缓存目录 */
    public static final String MM_PIC_CACHE_DIR_PATH = CACHE_DIR_PATH + "/mm_pic_cache";
    /** 多媒体沟通，大图片缓存目录 */
    public static final String MM_PIC_LARGE_CACHE_DIR_PATH = MM_PIC_CACHE_DIR_PATH + "/large";
    /** 多媒体沟通，视频缓存目录 */
    public static final String MM_VIDEO_CACHE_DIR_PATH = CACHE_DIR_PATH + "/mm_video_cache";
    /** 多媒体沟通，音频缓存目录 */
    public static final String MM_VOICE_CACHE_DIR_PATH = CACHE_DIR_PATH + "/mm_voice_cache";

    public static String getURL(){
        if(IS_HOME){
            HTTP_URL = HTTP_SCHEME + "://" + IP_HOME + ":" + PORT + METHOD;
        }else{
            HTTP_URL = HTTP_SCHEME + "://" + IP + ":" + PORT + METHOD;
        }
        return HTTP_URL;
    }
}
