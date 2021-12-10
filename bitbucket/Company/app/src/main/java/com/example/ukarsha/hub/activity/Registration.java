package com.example.ukarsha.hub.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.application.MyApplication;
import com.example.ukarsha.hub.model.RegisterModel;
import com.example.ukarsha.hub.utils.NetworkRequestUtil;
import com.example.ukarsha.hub.utils.VolleyCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.COMPANY_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.COMPANY_NAME;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.PLAYER_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STATUS_SUCCESS;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_NOTIFICATION;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_REGISTER;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_REGISTRATION_NEW;

public class Registration extends BaseActivity {
    private EditText editTextCmyName, editTextEmail, editTextContactNo, editTextPassword, editTextCPassowrd,
            editTextCity, editTextAddress, editTextWebsite, editText;
    private Button buttonRegister, buttonCancel, buttonClear;
    private GoogleApiClient googleApiClient;
    private Location location;
    private LocationRequest locationRequest;
    private static final int REQUEST_LOCATION = 12;
    String locLat;
    HTextView hTextView;
    private String lat = "", longg = "", notificationKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initProgress();
        initializeView();

        getLatLong();
        getID();

        hTextView = (HTextView) findViewById(R.id.text);
//        hTextView.setAnimateType(HTextViewType.ANVIL);
        hTextView.animateText("Sign Up");

    }

    public void getID() {
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);

                if (registrationId != null && userId != null){
                   MyApplication.getInstance().addToSharedPreference(PLAYER_ID, userId);

                    Log.d("debug", "registrationId:" + registrationId);
                }

                //Toast.makeText(Registration.this, "", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private String getString(String userId) {
        return notificationKey;
    }

    private void initializeView() {
        editTextCmyName =  findViewById(R.id.edCmpyName);
        editTextEmail =  findViewById(R.id.edEmail);
        editTextContactNo = findViewById(R.id.edMobile);
        editTextPassword = findViewById(R.id.edpassword);
        editTextCPassowrd = findViewById(R.id.edCpassword);
        editTextCity = findViewById(R.id.edCity);
        editTextAddress =  findViewById(R.id.edAddress);
        editTextWebsite =  findViewById(R.id.edWebsite);
        buttonClear = findViewById(R.id.btnClear);
        buttonRegister =  findViewById(R.id.btnRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (MyApplication.getInstance().isConnectedToNetwork(Registration.this)) {
                        validation();
                        //finish();
                    } else {
                        showMyDiag();
                    }
               /* if (!lat.trim().equals("")) {
                    if (MyApplication.getInstance().isConnectedToNetwork(Registration.this)) {
                        registerUser();
                        finish();
                    } else {
                        // saveMeterReadingLocal();
                        Toast.makeText(Registration.this, "Refresh Your GPS", Toast.LENGTH_SHORT).show();
                        showMyDiag();
                    }

                } else {
                    getLatLong();
                }*/
            }
        });
        buttonCancel = findViewById(R.id.btnCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this, Login.class));
                finish();
            }
        });

    }

    private void notification() {
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
        showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            requestJson.put("comp_id", "1");

            new NetworkRequestUtil(this).postData(URL_NOTIFICATION, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    NotifModel notifModel = new Gson().fromJson(response.toString(), NotifModel.class);
                    if (notifModel != null) {
                     //
                    } else {
                        showDialogWithOkButton(getString(R.string.error_someting_went_wrong));
                    }
                }

                //@Override
                public void onError(VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerUser() {
        final JSONObject requestJson;
        try {
            requestJson = new JSONObject();
            requestJson.put("email", editTextEmail.getText().toString());
            requestJson.put("password", editTextPassword.getText().toString());
            requestJson.put("lat", lat);
            requestJson.put("longg", longg);
            requestJson.put("name", editTextCmyName.getText().toString());
            requestJson.put("contact_no", editTextContactNo.getText().toString());
            requestJson.put("address", editTextAddress.getText().toString());
            requestJson.put("city", editTextCity.getText().toString());
            requestJson.put("website", editTextWebsite.getText().toString());
            requestJson.put("cmpy_img", "cyber.img");
            //requestJson.put("player_id", "3");

            new NetworkRequestUtil(this).postData(URL_REGISTRATION_NEW, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    RegisterModel registerModel = new Gson().fromJson(response.toString(), RegisterModel.class);
                    if (registerModel != null) {
                        if (registerModel.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                           // MyApplication.getInstance().addToSharedPreference(PLAYER_ID, registerModel.getUser().getPlayer_id());
                            MyApplication.getInstance().addToSharedPreference(COMPANY_NAME, registerModel.getUser().getName());
                            //Toast.makeText(Registration.this, registerModel.getMessage(), Toast.LENGTH_SHORT).show();
                            showCustomDialog();
                            showProgress(false);
                            Toast.makeText(Registration.this, "Register", Toast.LENGTH_SHORT).show();
                        } else {
                            showDialogWithOkButton(registerModel.getMessage());
                        }

                    } else {
                        showDialogWithOkButton(getString(R.string.error_someting_went_wrong));
                    }
                }

                //@Override
                public void onError(VolleyError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getTag() {
        return "Register";
    }

    /* Hide key board*/
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void initProgress() {
        if (getApplicationContext() == null) {
            progressDialog = new ProgressDialog(Registration.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(Registration.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        }
    }

    public void showProgress(boolean show) {

        if (show) {
            if (progressDialog != null && !this.isFinishing()) {
                progressDialog.show();
            }

        } else {
            if (progressDialog != null && progressDialog.isShowing() && !this.isFinishing())
                progressDialog.dismiss();
        }

    }

    private void showCustomDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_success, viewGroup, false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        alertDialog.show();
        TextView text = (TextView) dialogView.findViewById(R.id.text);
        text.setText("Registered Successfully");
        Button button = (Button) dialogView.findViewById(R.id.ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // alertDialog.dismiss();
                startActivity(new Intent(Registration.this, Login.class));
                finish();
            }
        });
        PopupWindow mPopupWindow = new PopupWindow(dialogView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //addNotification();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getLatLong() {
        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        Log.d("Location error", "addConnectionCallbacks");
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Log.d("Location error", "onConnectionSuspended");
                        googleApiClient.connect();
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                        Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                    }
                }).build();
        googleApiClient.connect();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.e("success", "yes");
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                        if (location != null) {
                            lat = location.getLatitude() + "";
                            longg = location.getLongitude() + "";
                        }


                        break;

                    case LocationSettingsStatusCodes.CANCELED:
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(Registration.this, REQUEST_LOCATION);

                            //finish();
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                }


            }
        });

    }
    public void clear(){
        editTextCmyName.getText().clear();
        editTextEmail.getText().clear();
        editTextContactNo.getText().clear();
        editTextPassword.getText().clear();
        editTextCPassowrd.getText().clear();
        editTextCity.getText().clear();
        editTextAddress.getText().clear();
        editTextWebsite.getText().clear();
    }


    public void validation() {
        final String cmpyname = editTextCmyName.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String contact = editTextContactNo.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String cpassword = editTextCPassowrd.getText().toString();
        final String city = editTextCity.getText().toString();
        final String address = editTextAddress.getText().toString();
        final String website = editTextWebsite.getText().toString();
      /*  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{1,10})";*/
        String userPatter = "[a-zA-Z]";


        if (TextUtils.isEmpty(cmpyname)) {
            editTextCmyName.setError("Please Enter Company Name");
            editTextCmyName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please Enter Email Address");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(contact)) {
            editTextContactNo.setError("Please Enter Contact Number");
            editTextContactNo.requestFocus();
            return;
        }
        /*if (editTextContactNo.length() != 10) {
            editTextContactNo.setError("Please Enter Valid Contact Number");
            editTextContactNo.requestFocus();
            return;
        }*/
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please Enter Password");
            editTextPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(cpassword)) {
            editTextCPassowrd.setError("Please Confirm your Password");
            editTextCPassowrd.requestFocus();
            return;
        }
        if (!password.equals(cpassword)) {
            Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show();
        } else if (password.length() >= 6) ;

       /* if (password.length() >= 6 || !(password.length() < 17)) {
            editTextPassword.setError("Invalid Password");
        }*/

        if (TextUtils.isEmpty(city)) {
            editTextCity.setError("Please Enter City");
            editTextCity.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            editTextAddress.setError("Please Enter Address");
            editTextAddress.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(website)) {
            editTextWebsite.setError("Please Enter Website");
            editTextWebsite.requestFocus();
            return;
        }

        if (contact.length() < 9 || contact.length()>11){
            editTextContactNo.setError("Invalid Contact Number");
            editTextContactNo.requestFocus();
            return;
        }

        registerUser();
        addNotification();
        //notification();

    }

    //Notification
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle("Notification From ConsultUsToday")
                        .setContentText("You have successfully registered with us")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent notificationIntent = new Intent(this, Login.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("message", "You have successfully registered with us");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private void showMyDiag() {

        final View dialogView = View.inflate(this, R.layout.no_network, null);

        final Dialog dialog = new Dialog(this, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        ImageView imageView = (ImageView) dialog.findViewById(R.id.closeDialogImg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealShow(dialogView, false, dialog);
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    revealShow(dialogView, false, dialog);
                    return true;
                }

                return false;
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void revealShow(View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (buttonRegister.getX() + (buttonRegister.getWidth() / 2));
        int cy = (int) (buttonRegister.getY()) + buttonRegister.getHeight() + 56;


        if (b) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();

        } else {

            Animator anim =
                    null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);
            }

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(700);
            anim.start();
        }
    }

    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
            try {
                if (additionalData != null) {
                    if (additionalData.has("actionSelected"))
                        Log.d("OneSignalExample", "OneSignal notification button with id " + additionalData.getString("actionSelected") + " pressed");

                    Log.d("OneSignalExample", "Full additionalData:\n" + additionalData.toString());
                }
            } catch (Throwable t) {
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