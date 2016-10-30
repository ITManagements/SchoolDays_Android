package com.example.scut.school_days;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/10/6.
 */
public class List_item extends TextView {
    public List_item(Context context){
        super(context);
    }

    public List_item(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    public List_item(Context context, AttributeSet attributeSet, int defstyle){
        super(context,attributeSet,defstyle);
    }

    private String title;
    private String content;

    public void setTitle(String title){
        this.title=title;
        this.setText(this.title+'\n'+this.content);
    }

    public void setContent(String content){
        this.content=content;
        this.setText(this.title+'\n'+this.content);
    }


}
