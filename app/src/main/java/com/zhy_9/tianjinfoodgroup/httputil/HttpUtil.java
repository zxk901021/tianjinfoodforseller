package com.zhy_9.tianjinfoodgroup.httputil;

import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zhy_9.tianjinfoodgroup.encrypt.EncryptParams;
import com.zhy_9.tianjinfoodgroup.util.Constant;
import com.zhy_9.tianjinfoodgroup.util.RandomString;

import java.util.Map;

/**
 * Created by ZHY_9 on 2016/8/30.
 */
public class HttpUtil {

    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;


    private static String noncestr;
    private static String timeStr;
    private static String sign;

    public static Map<String, String> initParam(Map<String, String> params){
        noncestr = RandomString.getRandomString(10);
        params.put("noncestr", noncestr);
        long time = System.currentTimeMillis();
        timeStr = time + "";
        params.put("timestamp", timeStr);
        params.put("salt", Constant.salt);
        String string = EncryptParams.getString(params);
        sign = EncryptParams.md5(EncryptParams.sha1(string));
        params.put("sign", sign);
        return params;
    }

    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 8;
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache(cacheSize));
    }

    public static ImageLoader getImageLoader(){
        if (mImageLoader != null){
            return  mImageLoader;
        }else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }

    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            init(context);
        }
        return mRequestQueue;
    }

    public static void getVolley(Context context, String url,
                                 final VolleyListener listener) {
        RequestQueue queue = getRequestQueue(context);
        StringRequest myReq = new UTF8StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });
        myReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);
    }

    public static void postVolley(Context context, String url,
                                  final Map<String, String> map, final VolleyListener listener) {
        RequestQueue queue = getRequestQueue(context);
        Response.Listener<String> SuccListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onResponse(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        };
        StringRequest myReq = new UTF8StringRequest(Request.Method.POST, url,
                SuccListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);
    }


}
