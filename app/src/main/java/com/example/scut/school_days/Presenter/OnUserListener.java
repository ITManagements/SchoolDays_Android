package com.example.scut.school_days.Presenter;

import com.example.scut.school_days.ModelEntity.Token;

/**
 * Created by nyq on 2016/11/4.
 */

public interface OnUserListener {
    interface OnAddUserListener {
        void onSuccess();
        void onFail();
    }
    interface OnGetTokenListener {
        void onSuccess(Token token);
        void onFail();
    }
}
