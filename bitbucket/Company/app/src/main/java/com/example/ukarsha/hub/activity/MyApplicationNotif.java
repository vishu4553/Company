package com.example.ukarsha.hub.activity;

import android.app.Application;
import android.content.Context;

import com.onesignal.OneSignal;

/**
 * Created by Ukarsha on 01-Oct-19.
 */

public class MyApplicationNotif extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //MyNotificationOpenedHandler : This will be called when a notification is tapped on.
        //MyNotificationReceivedHandler : This will be called when a notification is received while your app is running.
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                .setNotificationReceivedHandler( new MyNotificationReceivedHandler() )
                .init();
    }


}
