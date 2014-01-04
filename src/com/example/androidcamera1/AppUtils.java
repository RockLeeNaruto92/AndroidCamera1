package com.example.androidcamera1;

import android.os.Environment;

public class AppUtils {
	public static boolean chechSDCard(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
}
