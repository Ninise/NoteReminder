package com.ninise.notereminder.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class SingletonPhotoLoader {

    private static SingletonPhotoLoader mInstance = null;
    private Context mContex;

    public SingletonPhotoLoader(Context contex) {
        this.mContex = contex;
    }

    public static SingletonPhotoLoader getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new SingletonPhotoLoader(context);
        }

        return mInstance;
    }

    @SuppressLint("SimpleDateFormat")
    public Uri save() {
         File file = new File(Environment.getExternalStorageDirectory(),
                new SimpleDateFormat("dd_MMM_hh_mm").format(System.currentTimeMillis()) + ".jpg");
        return Uri.fromFile(file);
    }

    public Bitmap load(String path) throws IOException {
        return MediaStore.Images.Media.getBitmap(mContex.getContentResolver(), Uri.parse(path));
    }
}
