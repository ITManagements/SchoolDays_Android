package com.example.scut.school_days.ModelEntity;

/**
 * Created by nyq on 2016/11/4.
 */

public class Token {
    private int result;
    private String accessToken;
    private String expiresIn;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
