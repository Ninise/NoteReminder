package com.ninise.notereminder.utils;


import android.widget.TextView;

import java.util.Random;

public class Utils {

    public static boolean isTextViewEmpty(TextView tv) {
        return tv.getText().toString().equals("");
    }

    public static int generateRequest() {
        return new Random().nextInt(10000) - 1;
    }

}
