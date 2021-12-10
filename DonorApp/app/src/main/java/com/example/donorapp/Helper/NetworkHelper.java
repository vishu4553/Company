package com.example.donorapp.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.donorapp.Constant.ConstatsValue;


public class NetworkHelper {

    public static final int NETWORK_STATUS_NOT_CONNECTED = 0, NETWORK_STAUS_WIFI = 1, NETWORK_STATUS_MOBILE = 2;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return ConstatsValue.ResponseCodes.TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return ConstatsValue.ResponseCodes.TYPE_MOBILE;
        }
        return ConstatsValue.ResponseCodes.TYPE_NOT_CONNECTED;
    }

    public static int getConnectivityStatusString(Context context) {
        int conn = NetworkHelper.getConnectivityStatus(context);
        int status = 0;
        if (conn == ConstatsValue.ResponseCodes.TYPE_WIFI) {
            status = NETWORK_STAUS_WIFI;
        } else if (conn == ConstatsValue.ResponseCodes.TYPE_MOBILE) {
            status = NETWORK_STATUS_MOBILE;
        } else if (conn == ConstatsValue.ResponseCodes.TYPE_NOT_CONNECTED) {
            status = NETWORK_STATUS_NOT_CONNECTED;
        }
        return status;
    }

    public static boolean isInternetOn(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}