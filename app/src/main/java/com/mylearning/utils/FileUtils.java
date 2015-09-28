package com.mylearning.utils;

import android.os.Environment;

import com.mylearning.common.Constants;

import java.io.File;

public class FileUtils {
	/**
	 * 获取路径
	 *
	 * @return
	 */
	public static File getTempPath() {
		File tempFile = null;
		if (checkSDCard()) {
			File dirFile = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + Constants.CACHE_DIR_PATH);
			tempFile = new File(dirFile, System.currentTimeMillis() + ".jpg");
			if (!tempFile.getParentFile().exists()) {
				tempFile.getParentFile().mkdirs();
			}
		}
		return tempFile;
	}

	/**
	 * 检查sd卡
	 *
	 * @return
	 */
	public static boolean checkSDCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
}
