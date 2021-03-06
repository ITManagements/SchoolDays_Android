package com.example.scut.school_days.util.Volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by Administrator on 2015/2/6.
 */
public class VolleyRequest {
    private static RequestQueue mRequestQueue;

    private VolleyRequest() {
    }

    /**
     * @param context ApplicationContext
     */
    public static void buildRequestQueue(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        //... do something
    }

    public static VolleyRequest newInstance() {
        if (mRequestQueue == null) {
            throw new NullPointerException("Call buildRequestQueue method first.");
        }
        //...
        return new VolleyRequest();
    }

    /**
     * @param url
     * @param clazz
     * @param listener
     * @param errorListener
     * @return
     */
    public <T> GsonRequest<T> newGsonRequest(String url, Class<T> clazz, Response.Listener<T> listener,
                                             Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(url, clazz, listener, errorListener);
        mRequestQueue.add(request);
        return request;
    }

    public <T> GsonRequest<T> newGsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener,
                                             Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(method, url, clazz, listener, errorListener);
        mRequestQueue.add(request);
        return request;
    }

    public <T> GsonRequest<T> newGsonRequest(int method, String url, Map<String, String> paraMap, Class<T> clazz, Response.Listener<T> listener,
                                             Response.ErrorListener errorListener) {
        GsonRequest<T> request = new GsonRequest<T>(method, url, paraMap, clazz, listener, errorListener);
        mRequestQueue.add(request);
        return request;
    }

}
