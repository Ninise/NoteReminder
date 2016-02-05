package com.ninise.notereminder.utils;


import android.widget.TextView;

public class Utils {

    public static boolean isTextViewEmpty(TextView tv) {
        return tv.getText().toString().equals("");
    }

}
