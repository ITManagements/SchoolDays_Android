package com.example.scut.school_days.Network;

import com.example.scut.school_days.Presenter.OnUserListener;

import java.util.Map;

/**
 * Created by nyq on 2016/11/4.
 */

public interface AccessNet {
    String addUserUrl = "http://10.0.2.2:3000/users/add_user";
    String getAccessTokenUrl = "http://10.0.2.2:3000/oauth2/access_token";
    void addUser(Map<String, String> paraMap, OnUserListener.OnAddUserListener userListener);
    void getAccessToken(Map<String, String> paraMap, OnUserListener.OnGetTokenListener userListener);
}
