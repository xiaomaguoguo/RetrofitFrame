package com.bftv.retrofitlib;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.baofengtv.middleware.tv.BFTVCommonManager;
import com.baofengtv.middleware.tv.BFTVFactoryManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MaZhihua on 2017/9/5.
 * 基础参数配置与请求参数拦截打印在这里控制
 */
public class ParamsInterceptor implements Interceptor {

    public static final String TAG = ParamsInterceptor.class.getSimpleName();

    private Context context = null;

    private boolean isDebug = false;

    public ParamsInterceptor(Context context,boolean isDebug) {
        this.context = context;
        this.isDebug = isDebug;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.body() != null) {
            Request.Builder requestBuilder = request.newBuilder();
            FormBody.Builder newFormBody = new FormBody.Builder();
            Map<String, String> paramsMap = null;
            if (request.body() instanceof FormBody) {
                paramsMap = new HashMap<>();
                FormBody oldFormBody = (FormBody) request.body();
                for (int i = 0; i < oldFormBody.size(); i++) {
                    if(isDebug){
                        Log.d(TAG,"------请求参数-key:" + oldFormBody.encodedName(i) + "-->value:" + oldFormBody.encodedValue(i));
                    }
                    if (!TextUtils.isEmpty(oldFormBody.encodedValue(i))) {//过滤掉空value
                        paramsMap.put(oldFormBody.encodedName(i), URLDecoder.decode(oldFormBody.encodedValue(i), "utf-8"));
                    }
                }
            } else if (request.body() instanceof MultipartBody) {
//                用到了再说
            } else {

            }

            //在请求参数中加入通用参数
            for (Map.Entry<String, String> entry : configCommonParams(paramsMap).entrySet()) {
                newFormBody.add(entry.getKey(), entry.getValue());
            }
            requestBuilder.method(request.method(), newFormBody.build());
            return convertResponseCode(chain.proceed(requestBuilder.build()));
        }

        return convertResponseCode(chain.proceed(request));
    }


    /**
     * 对请求接口后，服务端的反馈码做统一转换，比如：404：页面未找到，xxx: 服务器错误类似
     * @param response
     */
    private Response convertResponseCode(Response response){
        // 这个需要用的时候再做更细致的实现，暂时不做多余处理
        /*try {
            if(response != null && response.code() == 400){
                Response.Builder builder = response.newBuilder();
                builder.code(200);
                builder.body(response.body());
                builder.headers(response.headers());
                builder.message(response.message());
                return builder.build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return response;
    }


    /**
     * 在所有接口请求中统一配置通用参数,即：所有接口都会包含以下配置的参数
     * @return
     */
    private Map<String, String> configCommonParams(Map<String, String> paramsMap) {
        HashMap<String,String> commonMap = new HashMap<>();
        if(paramsMap != null && !paramsMap.isEmpty()){
            commonMap.putAll(paramsMap);
        }
        commonMap.put(CommonParams.APP_TOKEN, CommonParams.DEFAULT_APP_TOKEN);//apptoken
        commonMap.put(CommonParams.API_VERSION_PARAMS, CommonParams.API_VERSION_VALUE);//version
        commonMap.put(CommonParams.FORM, CommonParams.PLATE_FORM_TV);//from
        try {
            commonMap.put(CommonParams.VR_SUPPORT_PARAMS, isSupportVR() + "");
            commonMap.put(CommonParams.PLATFORM, BFTVCommonManager.getInstance(context).getPlatform());//platform 平台（例MST_6A338、MST_6A639、AML_T866）
            commonMap.put(CommonParams.SYS_VERSION, BFTVCommonManager.getInstance(context).getVersion());//sys_version 系统版本 (例 V1.0.24)
            commonMap.put(CommonParams.SOFTID, BFTVCommonManager.getInstance(context).getSoftwareID());//softid 软件号（例11161301）
            commonMap.put(CommonParams.REQUEST_PLATFORM, "tv");
            commonMap.put(CommonParams.UUID, Build.SERIAL);//uuid
            commonMap.put(CommonParams.SN, BFTVFactoryManager.getInstance(context).getSerialNumber());//uuid 号
        }catch (Exception e){
            e.printStackTrace();
            if(isDebug){
                Log.d(TAG,"BFTVCommonManager取参异常");
            }
        }
        return commonMap;
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
}
