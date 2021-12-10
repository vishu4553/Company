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
import com.example.ukarsha.hub.model.IndustrialViewModel;
import com.example.ukarsha.hub.utils.NetworkRequestUtil;
import com.example.ukarsha.hub.utils.VolleyCallback;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.INDUSTRIAL_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STATUS_SUCCESS;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_CAMPUS_DRIVE_VIEW;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_INDUSTRIAL_VISIT_UPDATE;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_INDUSTRIAL_VISIT_VIEW;

public class RepostIndustrial extends BaseActivity {
    String id, name, position, posted, visitDt, trainInfo;
    EditText textViewTrainInfo;
    TextView textViewPos, textViewName, textViewPosted, textViewVisitDt;
    ProgressDialog progressDialog;
    Button buttonRepost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repost_industrial);

        id = getIntent().getStringExtra("COMPANY_ID");
        //position = getIntent().getStringExtra("POSITION");

        //textViewPos = findViewById(R.id.);
        textViewName = findViewById(R.id.industrial_name);
        textViewPosted = findViewById(R.id.industrial_posted);
        textViewTrainInfo = findViewById(R.id.industrial_traininginfo);
        textViewVisitDt = findViewById(R.id.industrial_visit);

        initProgress();
        initializeView();
        getIndustrialRepost();
    }

    private void initializeView() {
        textViewName = findViewById(R.id.industrial_name);
        textViewPosted = findViewById(R.id.industrial_posted);
        textViewTrainInfo = findViewById(R.id.industrial_traininginfo);
        textViewVisitDt = findViewById(R.id.industrial_visit);
        // buttonClear = findViewById(R.id.btnClear);
        buttonRepost = findViewById(R.id.repost);
        buttonRepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isConnectedToNetwork(RepostIndustrial.this)) {
                    industrialRepost();
                    //finish();
                } else {
                   noNetwork();
                }
            }
        });

    }

    private void getIndustrialRepost() {
        showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            requestJson.put("industrial_id",  MyApplication.getInstance().getFromSharedPreference(INDUSTRIAL_ID));

            new NetworkRequestUtil(this).postData(URL_INDUSTRIAL_VISIT_VIEW, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    IndustrialViewModel industrialViewModel = new Gson().fromJson(response.toString(),IndustrialViewModel.class);
                    if (industrialViewModel != null) {
                        if (industrialViewModel.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            //textViewPos.setText(industrialViewModel.getIndustrial_visit_view().getPost);
                            textViewName.setText(industrialViewModel.getIndustrial_visit_view().getName());
                            textViewPosted.setText(industrialViewModel.getIndustrial_visit_view().getPosted_date());
                            textViewTrainInfo.setText(industrialViewModel.getIndustrial_visit_view().getTraining_info());
                            textViewVisitDt.setText(industrialViewModel.getIndustrial_visit_view().getVisit_date());

                            //Toast.makeText(RepostIndustrial.this, industrialViewModel.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        } else {
                            showDialogWithOkButton(industrialViewModel.getMessage());
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


    private void industrialRepost() {
        showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            requestJson.put("industrial_id", MyApplication.getInstance().getFromSharedPreference(INDUSTRIAL_ID));
            requestJson.put("name", textViewName.getText().toString());
            requestJson.put("posted_date", textViewPosted.getText().toString());
            requestJson.put("training_info", textViewTrainInfo.getText().toString());
            requestJson.put("visit_date", textViewVisitDt.getText().toString());

            new NetworkRequestUtil(this).postData(URL_INDUSTRIAL_VISIT_UPDATE, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    CommonModel1 commonModel1 = new Gson().fromJson(response.toString(), CommonModel1.class);
                    if (commonModel1 != null) {
                        if (commonModel1.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            //Toast.makeText(RepostIndustrial.this, commonModel1.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(RepostIndustrial.this, "Repost Successfully", Toast.LENGTH_SHORT).show();
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
            progressDialog = new ProgressDialog(RepostIndustrial.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(RepostIndustrial.this);
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


    /*public void clear(){
        editTextCmyName.getText().clear();
        editTextEmail.getText().clear();
        editTextContactNo.getText().clear();
        editTextPassword.getText().clear();
        editTextCPassowrd.getText().clear();
        editTextCity.getText().clear();
        editTextAddress.getText().clear();
        editTextWebsite.getText().clear();
    }*/


  /*  public void validation() {
        final String cmpyname = editTextCmyName.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String contact = editTextContactNo.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String cpassword = editTextCPassowrd.getText().toString();
        final String city = editTextCity.getText().toString();
        final String address = editTextAddress.getText().toString();
        final String website = editTextWebsite.getText().toString();
      *//*  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{1,10})";*//*
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
        *//*if (editTextContactNo.length() != 10) {
            editTextContactNo.setError("Please Enter Valid Contact Number");
            editTextContactNo.requestFocus();
            return;
        }*//*
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

       *//* if (password.length() >= 6 || !(password.length() < 17)) {
            editTextPassword.setError("Invalid Password");
        }*//*

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

    }*/

    public void noNetwork(){
        startActivity(new Intent(RepostIndustrial.this, NoNetwork.class));
    }

}

