package com.ninise.notereminder.settings.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SingletonSharedPreferences {

    private static SingletonSharedPreferences mInstance = null;

    private static SharedPreferences preferences;

    private static final String APP_PREFERENCES = "settings";

    private static final String APP_PREFERENCES_VIBRATE = "vibrate";
    private static final String APP_PREFERENCES_SOUND = "sound";

    private SingletonSharedPreferences(Context context) {
        preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static SingletonSharedPreferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SingletonSharedPreferences(context);
        }

        return mInstance;
    }

    public void setVibrateStatus(boolean status) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(APP_PREFERENCES_VIBRATE, status);
        editor.apply();
    }

    public boolean getVibrateStatus() {
        return preferences.contains(APP_PREFERENCES_VIBRATE) && preferences.getBoolean(APP_PREFERENCES_VIBRATE, false);
    }

    public void setSoundStatus(boolean status) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(APP_PREFERENCES_SOUND, status);
        editor.apply();
    }

    public boolean getSoundStatus() {
        return preferences.contains(APP_PREFERENCES_SOUND) && preferences.getBoolean(APP_PREFERENCES_SOUND, false);
    }
}
