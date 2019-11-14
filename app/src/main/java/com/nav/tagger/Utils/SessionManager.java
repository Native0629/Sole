package com.nav.tagger.Utils;


import android.content.Context;
import android.preference.PreferenceManager;


public class SessionManager {



    public static String getPassword(Context context) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("password", "0000");
    }
    public static void setPassword(Context context,String phone_verified) {
        android.content.SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("password", phone_verified);
        prefsEditor.commit();
    }

}
