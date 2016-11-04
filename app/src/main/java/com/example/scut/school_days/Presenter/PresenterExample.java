package com.example.scut.school_days.Presenter;

import android.util.Log;

import com.example.scut.school_days.ModelEntity.Token;
import com.example.scut.school_days.Network.AccessNet;
import com.example.scut.school_days.Network.AccessNetImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nyq on 2016/11/4.
 */


// first call "VolleyRequest.buildRequestQueue(getApplicationContext());" in onCreate() in the MainActivity.java
// pay attention to manifest to configure permission of network
// pay attention to configure the ip

public class PresenterExample {
    public static String TAG = "PresenterExample";
    public PresenterExample() {

    }
    public void test(){
        AccessNet accessNetImpl = new AccessNetImpl();

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("name", "y2k");
        paraMap.put("password", "123456");
        accessNetImpl.addUser(paraMap, new OnUserListener.OnAddUserListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "add a user successfully");
            }

            @Override
            public void onFail() {
                Log.d(TAG, "fail to add a user");
            }
        });
        accessNetImpl.getAccessToken(paraMap, new OnUserListener.OnGetTokenListener() {
            @Override
            public void onSuccess(Token token) {
                Log.d(TAG, "get a token successfully");
            }

            @Override
            public void onFail() {
                Log.d(TAG, "fail to get a token");
            }
        });
    }
}
