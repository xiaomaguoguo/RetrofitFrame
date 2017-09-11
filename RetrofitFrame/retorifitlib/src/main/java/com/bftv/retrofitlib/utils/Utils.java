package com.bftv.retrofitlib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;

import java.lang.reflect.Method;

/**
 * Created by MaZhihua on 2017/9/8.
 * 使用到的一些基础工具
 */
public class Utils {

    /**
     * get App versionCode
     * @param context
     * @return
     */
    public static String getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionName="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 是否支持VR
     * @return 0 不支持
     *         1 支持
     */
    public static int isSupportVR(){
        int vr_support = 0;
        try {
            Method getMethod = Class.forName("android.os.SystemProperties").getMethod("get", String.class, String.class);
            String str = (String) (getMethod.invoke(null, "baofengtv.en.VR", "false"));
            if("true".equals(str)){
                return 1;
            }
            return vr_support;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vr_support;
    }

    /**
     * 屏幕分辨率
     * @param context
     * @return
     */
    public static String getScreenPixel(Context context){
        String pixel = "";
        try{
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int widthPixels = displayMetrics.widthPixels;
            int heightPixels = displayMetrics.heightPixels;
            StringBuilder sb = new StringBuilder();
            sb.append(widthPixels).append("*").append(heightPixels);
            pixel = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return pixel;
    }

}
