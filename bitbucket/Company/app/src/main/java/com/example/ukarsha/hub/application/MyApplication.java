package com.example.ukarsha.hub.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.ukarsha.hub.utils.AppConstantsAndUtils;
import com.orm.SugarContext;

import java.util.Set;

/**
 * Created by Ukarsha on 31-Dec-18.
 */
public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInstance;
    private RequestQueue mRequestQueue;
    private SharedPreferences sharedPreferences;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        SugarContext.init(this);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /* For saving info that are device specific and not user specific*/
    public void addToSharedPreferenceBackup(String key, String value) {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME_BACK_UP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).apply();
    }


    public String getFromSharedPreferenceBackup(String key) {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME_BACK_UP, MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public void addIntToSharedPreference(String key, int value) {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value).apply();
    }

    public int getIntFromSharedPreference(String key) {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }

    public void addLongToSharedPreference(String key, Long value) {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value).apply();
    }

    public Long getLongFromSharedPreference(String key) {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        return sharedPreferences.getLong(key, -1);
    }

    public void addToSharedPreference(String key, String value) {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).apply();
    }

    public String getFromSharedPreference(String key) {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public void addBooleanToSharedPreference(String key, boolean value) {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value).apply();
    }

    public boolean getBooleanFromSharedPreference(String key) {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public void addListToSharedPreference(String key, Set<String> value) {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, value).apply();
    }

    public Set<String> getListFromSharedPreference(String key) {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        return sharedPreferences.getStringSet(key, null);
    }

    public boolean isConnectedToNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        // boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public void clearPreferences() {
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(AppConstantsAndUtils.USER_ID, MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public void clearAllPreferences() {
        try {
            clearPreferences();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
