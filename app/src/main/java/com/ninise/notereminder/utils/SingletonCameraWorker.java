package com.ninise.notereminder.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

public class SingletonCameraWorker {

    private static SingletonCameraWorker mInstance;

    private static Context mContext;

    private SingletonCameraWorker(Context context) {
        mContext = context;
    }

    public static SingletonCameraWorker getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new SingletonCameraWorker(context);
        }

        return mInstance;
    }

    public Intent openCamera() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }


}
