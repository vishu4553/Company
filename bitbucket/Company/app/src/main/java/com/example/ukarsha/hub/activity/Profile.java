package com.example.ukarsha.hub.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.application.MyApplication;
import com.example.ukarsha.hub.model.CommonModel;
import com.example.ukarsha.hub.model.ProfileModel;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.COMPANY_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.PLAYER_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STATUS_SUCCESS;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_PROFILE;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_PROFILE_NEW;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_UPDATE_PROFILE;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_UPDATE_PROFILE_NEW;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.USER_ID;

public class Profile extends BaseActivity implements View.OnClickListener {
    ProgressDialog progressDialog;
    private EditText editTextCmyName, editTextEmail, editTextContactNo, editTextPassword, editTextCPassowrd,
            editTextCity, editTextAddress, editTextWebsite, editText;
    Button buttonSave, buttonCancel;
    //TextView textViewTime;
    ImageView imageView1, imageView2;
    HTextView hTextView;
    TextView tVHeading;
    String lat = "", longg = "";
    private static final int REQUEST_LOCATION = 12;
    private GoogleApiClient googleApiClient;
    private Location location;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       /* Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);*/

        initProgress();
        initializeView();
        profileDetails();
        //getLatLong();
        getID();

      /*  hTextView = (HTextView) findViewById(R.id.text);
        hTextView.setAnimateType(HTextViewType.ANVIL);
        hTextView.animateText("Edit Profile");
*/
        tVHeading = findViewById(R.id.heading);
        /*tVHeading.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        tVHeading.animate().x(200)
                .setDuration(5000)
                .setStartDelay(1000);*/

    }

    private void initializeView() {
        editTextCmyName = findViewById(R.id.edCmpyName);
        editTextEmail = findViewById(R.id.edEmail);
        editTextContactNo = findViewById(R.id.edMobile);
        //editTextPassword = findViewById(R.id.edpassword);
        //editTextCPassowrd = findViewById(R.id.edCpassword);
        editTextCity = findViewById(R.id.edCity);
        editTextAddress = findViewById(R.id.edAddress);
        editTextWebsite = findViewById(R.id.edWebsite);
        //imageView1 = findViewById(R.id.pswd);
        //imageView1.setOnClickListener(this);
        //imageView2 = findViewById(R.id.cnfrmPswd);
        //imageView2.setOnClickListener(this);

        buttonCancel = findViewById(R.id.btnCancel);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = "" + mdformat.format(calendar.getTime());
        display(strDate);

        buttonSave = findViewById(R.id.btnSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getInstance().isConnectedToNetwork(Profile.this)) {
                   // validation();
                    updateProfileDetails();
                } else {
                   noNetwork();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Login.class));
            }
        });
    }

    private void display(String num) {
        //textViewTime = findViewById(R.id.time);
       // textViewTime.setText(num);
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


    private void profileDetails() {
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            requestJson.put("comp_id", MyApplication.getInstance().getFromSharedPreference(COMPANY_ID) + "");
            //requestJson.put("comp_id", "6");

            new NetworkRequestUtil(this).postData(URL_PROFILE_NEW, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    final ProfileModel profileModel = new Gson().fromJson(response.toString(), ProfileModel.class);
                    if (profileModel != null) {
                        if (profileModel.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            //Toast.makeText(Profile.this, profileModel.getMessage(), Toast.LENGTH_SHORT).show();

                            //MyApplication.getInstance().addToSharedPreference(USER_ID, updatedBasicDetailsModel.getBasic_details().getUser_id());
                            //MyApplication.getInstance().addToSharedPreference("USER", updatedBasicDetailsModel.getBasic_details().getUser_id());
                            editTextCmyName.setText(profileModel.getResult().getName());
                            editTextEmail.setText(profileModel.getResult().getEmail());
                            editTextContactNo.setText(profileModel.getResult().getContact_no());
                            //editTextPassword.setText(profileModel.getResult().getPwsd());
                            //editTextCPassowrd.setText(profileModel.getResult().getPwsd());
                            editTextCity.setText(profileModel.getResult().getCity());
                            editTextAddress.setText(profileModel.getResult().getAddress());
                            editTextWebsite.setText(profileModel.getResult().getWebsite());
                        } else {
                            showDialogWithOkButton(profileModel.getMessage());
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
    //for update api

    private void updateProfileDetails (){
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            requestJson.put("comp_id", MyApplication.getInstance().getFromSharedPreference(COMPANY_ID));
            //requestJson.put("comp_id", "6");
            requestJson.put("email", editTextEmail.getText().toString());
            requestJson.put("password", "pass123");
            requestJson.put("lat", lat);
            requestJson.put("longg", longg);
            requestJson.put("name", editTextCmyName.getText().toString());
            requestJson.put("contact_no", editTextContactNo.getText().toString());
            requestJson.put("address", editTextAddress.getText().toString());
            requestJson.put("city", editTextCity.getText().toString());
            requestJson.put("website", editTextWebsite.getText().toString());
            requestJson.put("cmpy_img", "cyber.img");
            //requestJson.put("player_id", PLAYER_ID);

            new NetworkRequestUtil(this).postData(URL_UPDATE_PROFILE_NEW, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    final CommonModel commonModel = new Gson().fromJson(response.toString(), CommonModel.class);
                    if (commonModel != null) {
                        if (commonModel.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            Toast.makeText(Profile.this, commonModel.getMessage(), Toast.LENGTH_SHORT).show();

                            //MyApplication.getInstance().addToSharedPreference(USER_ID, updatedBasicDetailsModel.getBasic_details().getUser_id());
                            //MyApplication.getInstance().addToSharedPreference("USER", updatedBasicDetailsModel.getBasic_details().getUser_id());
                        } else {
                            showDialogWithOkButton(commonModel.getMessage());
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void initProgress() {
        if (getApplicationContext() == null) {
            progressDialog = new ProgressDialog(Profile.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(Profile.this);
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected String getTag() {
        return "Profile";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
           /* case R.id.pswd:
                show();
                break;

            case R.id.cnfrmPswd:
                show1();
                break;
*/
        }
    }

    public void show() {
        editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    public void show1() {
        editTextCPassowrd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    public void noNetwork(){
        startActivity(new Intent(Profile.this, NoNetwork.class));
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
                            status.startResolutionForResult(Profile.this, REQUEST_LOCATION);

                            //finish();
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                }


            }
        });

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

