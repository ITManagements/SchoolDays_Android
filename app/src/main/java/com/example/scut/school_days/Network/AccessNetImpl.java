package com.example.scut.school_days.Network;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.scut.school_days.ModelEntity.ResultState;
import com.example.scut.school_days.ModelEntity.Token;
import com.example.scut.school_days.Presenter.OnUserListener;
import com.example.scut.school_days.util.Volley.VolleyRequest;
import com.android.volley.Response;

import java.util.Map;

/**
 * Created by nyq on 2016/11/4.
 */

public class AccessNetImpl implements AccessNet {

    @Override
    public void addUser(Map<String, String> paraMap, final OnUserListener.OnAddUserListener userListener) {
        VolleyRequest.newInstance().newGsonRequest(Request.Method.POST, addUserUrl, paraMap,
                ResultState.class, new Response.Listener<ResultState>() {
                    @Override
                    public void onResponse(ResultState resultState) {
                        if(resultState.getResult() == 0) {
                            userListener.onSuccess();
                        }
                        else {
                            userListener.onFail();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        userListener.onFail();
                    }
                });

    }

    @Override
    public void getAccessToken(Map<String, String> paraMap, final OnUserListener.OnGetTokenListener userListener) {
        VolleyRequest.newInstance().newGsonRequest(Request.Method.POST, getAccessTokenUrl, paraMap,
                Token.class, new Response.Listener<Token>() {
                    @Override
                    public void onResponse(Token token) {
                        if(token.getResult() == 0) {
                            userListener.onSuccess(token);
                        }
                        else {
                            userListener.onFail();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        userListener.onFail();
                    }
                });
    }
}
