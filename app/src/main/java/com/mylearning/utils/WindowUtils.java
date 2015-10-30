package com.mylearning.utils;

import android.content.Context;
import android.os.Environment;
import android.view.WindowManager;

import com.mylearning.common.Constants;

import java.io.File;

public class WindowUtils {
	public static WindowManager getWindowManager(Context context){
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		return wm;
	}

}
