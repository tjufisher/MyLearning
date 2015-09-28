package com.mylearning.utils;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;


public class IntentUtils {
	/**
	 * 浏览手机相册
	 * 
	 * @return
	 */
	public static Intent createAlbumIntent() {
		if (android.os.Build.VERSION.SDK_INT >= 19) {
			Intent intent = new Intent("android.intent.action.PICK", null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			return intent;
		} else {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			Intent chooseIntent = Intent.createChooser(intent, null);
			return chooseIntent;
		}
	}

	/**
	 * 拍照
	 * 
	 * @return
	 */
	public static Intent createShotIntent(File tempFile) {
		if (isCameraCanUse()) {
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			Uri uri = Uri.fromFile(tempFile);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			return intent;

		} else {
			return null;
		}
	}

	public static boolean isCameraCanUse() {
		boolean canUse = true;
		Camera mCamera = null;
		try {
			mCamera = Camera.open();
			LogUtils.e("camera", "can open");
		} catch (Exception e) {
			LogUtils.e("camera", "can't open");
			canUse = false;
		}
		if (canUse) {
			if (null != mCamera) {
				mCamera.release();
				mCamera = null;
			}
		}
		return canUse;
	}



}
