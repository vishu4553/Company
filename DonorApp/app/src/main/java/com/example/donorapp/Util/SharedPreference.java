package com.example.donorapp.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference {

    private static SharedPreference   sharedPreference;
    public static final String PREFS_NAME = "PSI_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String";



    public static SharedPreference getInstance()
    {
        if (sharedPreference == null)
        {
            sharedPreference = new SharedPreference();
        }
        return sharedPreference;
    }

    public SharedPreference() {
        super();
    }

    public void saveString(Context context, String Key , String text) {
        SharedPreferences settings;
        Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putString(Key, text); //3
        editor.commit(); //4
    }

    public String getStringValue(Context context , String Key) {
        SharedPreferences settings;
        String text ="";
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(Key,"null");
        return text;
    }

    public void saveBoolean(Context context, String Key , boolean text) {
        SharedPreferences settings;
        Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putBoolean(Key, text); //3
        editor.commit(); //4
    }

    public int getIntValue(Context context , String Key) {
        SharedPreferences settings;
        int text =1;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getInt(Key,0);
        return text;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(Context context , String value) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(value);
        editor.commit();
    }

    public boolean getBoolean(Context context, String isLoggedIn) {
        SharedPreferences settings;
        boolean text =false;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getBoolean(isLoggedIn,false);
        return text;
    }
}