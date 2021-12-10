package com.example.ukarsha.hub.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.ukarsha.hub.R;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;

import java.math.BigInteger;

/**
 * Created by Ukarsha on 01-Oct-19.
 */

public class MyNotificationExtenderService extends NotificationExtenderService {
    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {
        OverrideSettings overrideSettings = new OverrideSettings();
        overrideSettings.extender = new NotificationCompat.Extender() {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                // Sets the background notification color to Red on Android 5.0+ devices.
                Bitmap icon = BitmapFactory.decodeResource(MyApplicationNotif.getContext().getResources(),
                        R.drawable.ic_notifications);
                builder.setLargeIcon(icon);
                return builder.setColor(new BigInteger("FF0000FF", 16).intValue());
            }
        };

        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
        Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);

        return true;
    }
}
