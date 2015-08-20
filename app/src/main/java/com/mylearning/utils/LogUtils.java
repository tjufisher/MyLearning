package com.mylearning.utils;

import android.os.Environment;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LogUtils {
	/**
	 * 是否开启日志
	 */
	public static boolean isTest = true;

	public static boolean isLog = false;// 是否保存log日志到本地目录/soufun/log

	public static boolean deaultConfig = true;

	public static boolean deault = isTest ? deaultConfig : false;

	/**
	 * 正式/测试接口
	 */
	public static String url = deault ? "http://testchat.client.3g.fang.com/HttpConnection"
			: "http://chat.client.3g.fang.com/HttpConnection"; // 新聊天接口
	public static String HTTP_HOST = deault ? "42.62.33.17:9082/http/"
			: "soufunapp.3g.fang.com/http/";// 南北方服务器
	public static String HTTP_HOST_ESF = deault ? "42.62.33.17:9082/http/"
			: "soufunappesf.3g.fang.com/http/";// 二手房接口
	public static String HTTP_HOST_ZF = deault ? "42.62.33.17:9082/http/"
			: "soufunappzf.3g.fang.com/http/";// // 租房接口
	public static String URL_JIFEN_STORE = deault ? "http://m.store.test.fang.com/index.html?&src=client"
			: "http://m.store.fang.com/index.html?&src=client";// 积分商城的URL
	public static String URL_YOUHUIQUAN = deault ? "http://coupon.test.fang.com/m/MyCoupon.html?From=APP&City=bj"
			: "http://coupon.fang.com/m/MyCoupon.html?From=APP&City=bj";//优惠券wap页URL
	public static String URL_ENTRUST_FINANCE = deault ? "http://m.test.fang.com/my/?c=myesf&a=getOpenCityList&"
			: "http://m.fang.com/my/?c=myesf&a=jrfwIndex&";// 二手房委托列表金融服务的URL
	public static String URL_MYPURSE_LICAI=deault?"http://wap.test.txdai.com/MyAccount/MyWealthManage.html?from=android"
			:"http://wap.txdai.com/MyAccount/MyWealthManage.html?from=android";//我的钱--理财url
	public static String URL_MYPURSE_TIANXIADAI=deault?"http://m.test.txdai.com/AssetManagement/AssetManagementIndex.html"
			:"http://m.txdai.com/AssetManagement/AssetManagementIndex.html";//我的钱--天下贷资产url
		
	public static void switchInterface(boolean interfaceSwitch) {
		url = interfaceSwitch ? "http://testchat.client.3g.fang.com/HttpConnection"
				: "http://chat.client.3g.fang.com/HttpConnection";
		HTTP_HOST = interfaceSwitch ? "42.62.33.17:9082/http/" : "soufunapp.3g.fang.com/http/";
		HTTP_HOST_ESF = interfaceSwitch ? "42.62.33.17:9082/http/"
				: "soufunappesf.3g.fang.com/http/";
		HTTP_HOST_ZF = interfaceSwitch ? "42.62.33.17:9082/http/" : "soufunappzf.3g.fang.com/http/";
		URL_JIFEN_STORE = interfaceSwitch ? "http://m.store.test.fang.com/index.html?"
				: "http://m.store.fang.com/index.html?";
		URL_YOUHUIQUAN = interfaceSwitch ? "http://coupon.test.fang.com/m/MyCoupon.html?From=APP&City=bj"
				: "http://coupon.fang.com/m/MyCoupon.html?From=APP&City=bj";//优惠券wap页URL
		URL_ENTRUST_FINANCE = interfaceSwitch ? "http://m.test.fang.com/my/?c=myesf&a=getOpenCityList&"
				: "http://m.fang.com/my/?c=myesf&a=jrfwIndex&";
//		NetConstants.HTTP_URL = NetConstants.HTTP_SCHEME + "://" + LogUtils.HTTP_HOST;
//		NetConstants.HTTP_URL_ESF = NetConstants.HTTP_SCHEME + "://" + LogUtils.HTTP_HOST_ESF;
//		NetConstants.HTTP_URL_ZF = NetConstants.HTTP_SCHEME + "://" + LogUtils.HTTP_HOST_ZF;
//		NetConstants.HTTP_URL_HTTPS = NetConstants.HTTP_SAFETY_SCHEME + "://" + LogUtils.HTTP_HOST;
	}

	/**
	 * 是否使用资讯测试地址
	 */
	/*
	 * public static final boolean isTestNews = false; public static final
	 * String HTTP_NEWS_TEST = "http://m.test.fang.com/";// 资讯测试地址 // public
	 * static final String HTTP_NEWS_TEST = //
	 * "http://linju.testclient.3g.soufun.com/"; public static final String
	 * HTTP_NEWS = "http://m.fang.com/"; // 资讯正式地址
	 */
	public static void d(String key, String value) {
		if (isTest) {
			android.util.Log.d(key, value);
		}
	}

	public static void i(String key, String value) {
		if (isTest) {
			android.util.Log.i(key, value);
		}
	}

	public static void e(String key, String value) {
		if (isTest) {
			android.util.Log.e(key, value);
		}
	}

	public static void w(String key, String value) {
		if (isTest) {
			android.util.Log.w(key, value);
		}
	}

	public static void w(String key, Throwable tr) {
		if (isTest) {
			android.util.Log.w(key, tr);
		}
	}

	public static void w(String key, String value, Throwable tr) {
		if (isTest) {
			android.util.Log.w(key, value, tr);
		}
	}

	public static void log(String tag, String info) {
		StackTraceElement[] ste = new Throwable().getStackTrace();
		int i = 1;
		if (isTest) {
			StackTraceElement s = ste[i];
			android.util.Log.e(tag, String.format("======[%s][%s][%s]=====%s", s.getClassName(),
					s.getLineNumber(), s.getMethodName(), info));
		}
	}

	/**
	 * 
	 * @param content
	 *            保存到本地的内容
	 */
	public static void writeLog(String content) {
		if (isTest && isLog) {
			// log("write", "=====");
			int day, month;
			File localFile;
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			content += "====" + formatter.format(date) + "====";
			Calendar calendar = Calendar.getInstance();
			day = calendar.get(Calendar.DAY_OF_MONTH);
			month = calendar.get(Calendar.MONTH);
			localFile = new File(getTxtFile(), month + "月" + day + "日" + ".txt");
			try {
				// if (localFile.exists()) {
				FileWriter fileWriter = new FileWriter(localFile, true);
				BufferedWriter bWriter = new BufferedWriter(fileWriter);
				bWriter.write(content);
				bWriter.newLine();
				bWriter.flush();
				bWriter.close();
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static File getTxtFile() {
		File voiceFile = new File(Environment.getExternalStorageDirectory().toString() + "/"
				+ "SouFun" + "/" + "Log" + "/");

		if (!voiceFile.exists()) {
			voiceFile.mkdirs();
		}
		return voiceFile;
	}
}
