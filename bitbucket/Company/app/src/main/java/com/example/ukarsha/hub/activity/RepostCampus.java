package com.example.ukarsha.hub.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.application.MyApplication;
import com.example.ukarsha.hub.model.CampusViewModel;
import com.example.ukarsha.hub.model.CommonModel1;
import com.example.ukarsha.hub.utils.NetworkRequestUtil;
import com.example.ukarsha.hub.utils.VolleyCallback;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.CAMPUS_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STATUS_SUCCESS;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_CAMPUS_DRIVE_UPDATE;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_CAMPUS_DRIVE_VIEW;

public class RepostCampus extends BaseActivity {
    EditText textViewDriveInfo;
    TextView textViewPos, textViewName, textViewPosted, textViewWebiste, textViewEndDt;
    Button buttonRepost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repost_campus);
        initProgress();
        initializeView();
        getcampusRepost();
    }

    private void initializeView() {
        textViewPos = findViewById(R.id.cmp_position1);
        textViewName = findViewById(R.id.cmp_name1);
        textViewPosted = findViewById(R.id.cmp_posted1);
        textViewDriveInfo = findViewById(R.id.cmp_driveinfo);
        textViewPos = findViewById(R.id.cmp_position1);
        textViewName = findViewById(R.id.cmp_name1);
        textViewPosted = findViewById(R.id.cmp_posted1);
        textViewDriveInfo = findViewById(R.id.cmp_driveinfo);
        textViewWebiste = findViewById(R.id.cmp_link1);
        textViewEndDt = findViewById(R.id.endDt);
        // buttonClear = findViewById(R.id.btnClear);
        buttonRepost = findViewById(R.id.repost);
        buttonRepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isConnectedToNetwork(RepostCampus.this)) {
                    campusRepost();
                   // finish();
                } else {
                    noNetwork();
                }
            }
        });

    }

    private void getcampusRepost() {
        showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            requestJson.put("campus_id", MyApplication.getInstance().getFromSharedPreference(CAMPUS_ID));

            new NetworkRequestUtil(this).postData(URL_CAMPUS_DRIVE_VIEW, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    CampusViewModel campusViewModel = new Gson().fromJson(response.toString(),CampusViewModel.class);
                    if (campusViewModel != null) {
                        if (campusViewModel.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            textViewPos.setText(campusViewModel.getView_campus_drive().getPosition());
                            textViewName.setText(campusViewModel.getView_campus_drive().getName());
                            textViewPosted.setText(campusViewModel.getView_campus_drive().getPosted_date());
                            textViewDriveInfo.setText(campusViewModel.getView_campus_drive().getDrive_info());
                            textViewWebiste.setText(campusViewModel.getView_campus_drive().getRegistration_link());
                            textViewEndDt.setText(campusViewModel.getView_campus_drive().getEnd_date());

                            //Toast.makeText(RepostCampus.this, campusViewModel.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        } else {
                            showDialogWithOkButton(campusViewModel.getMessage());
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

    private void campusRepost() {
        showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            requestJson.put("campus_id", MyApplication.getInstance().getFromSharedPreference(CAMPUS_ID));
            requestJson.put("name", textViewName.getText().toString());
            requestJson.put("posted_date", textViewPosted.getText().toString());
            //requestJson.put("contact_no", "9856231456");
            requestJson.put("registration_link", textViewWebiste.getText().toString());
            requestJson.put("position", textViewPos.getText().toString());
            requestJson.put("drive_info", textViewDriveInfo.getText().toString());
            requestJson.put("end_date", textViewEndDt.getText().toString());

            new NetworkRequestUtil(this).postData(URL_CAMPUS_DRIVE_UPDATE, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    CommonModel1 commonModel1 = new Gson().fromJson(response.toString(), CommonModel1.class);
                    if (commonModel1 != null) {
                        if (commonModel1.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            //Toast.makeText(RepostCampus.this, commonModel1.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(RepostCampus.this, "Repost Successfully", Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        } else {
                            showDialogWithOkButton(commonModel1.getMessage());
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
            progressDialog = new ProgressDialog(RepostCampus.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(RepostCampus.this);
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


    public void noNetwork(){
        startActivity(new Intent(RepostCampus.this, NoNetwork.class));
    }

}
