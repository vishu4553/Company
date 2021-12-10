package com.example.ukarsha.hub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.ukarsha.hub.R;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneSignal.startInit(this).setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .init();
        setContentView(R.layout.activity_main);

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);

            }
        });
    }
    // This fires when a notification is opened by tapping on it or one is received while the app is running.
    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
            try {
                if (additionalData != null) {
                    if (additionalData.has("actionSelected"))
                        Log.d("OneSignalExample", "OneSignal notification button with id " + additionalData.getString("actionSelected") + " pressed");

                    Log.d("OneSignalExample", "Full additionalData:\n" + additionalData.toString());
                }} catch (Throwable t) {
                t.printStackTrace();
            }
        }

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            String activityToBeOpened;
            if (data != null) {
                activityToBeOpened = data.optString("activityToBeOpened", null);
                if (activityToBeOpened != null && activityToBeOpened.equals("AnotherActivity")) {
                    Log.i("OneSignalExample", "customkey set with value: " + activityToBeOpened);
                    Intent intent = new Intent(MyApplicationNotif.getContext(), Another.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplicationNotif.getContext().startActivity(intent);
                } else if (activityToBeOpened != null && activityToBeOpened.equals("MainActivity")) {
                    Log.i("OneSignalExample", "customkey set with value: " + activityToBeOpened);
                    Intent intent = new Intent(MyApplicationNotif.getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplicationNotif.getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(MyApplicationNotif.getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplicationNotif.getContext().startActivity(intent);
                }

            }

            //If we send notification with action buttons we need to specidy the button id's and retrieve it to
            //do the necessary operation.
            if (actionType == OSNotificationAction.ActionType.ActionTaken) {
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
                if (result.action.actionID.equals("ActionOne")) {
                    Toast.makeText(MyApplicationNotif.getContext(), "ActionOne Button was pressed", Toast.LENGTH_LONG).show();
                } else if (result.action.actionID.equals("ActionTwo")) {
                    Toast.makeText(MyApplicationNotif.getContext(), "ActionTwo Button was pressed", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
