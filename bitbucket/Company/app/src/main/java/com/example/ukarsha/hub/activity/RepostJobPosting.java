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
import com.example.ukarsha.hub.model.CommonModel1;
import com.example.ukarsha.hub.model.JobPostingViewModel;
import com.example.ukarsha.hub.utils.NetworkRequestUtil;
import com.example.ukarsha.hub.utils.VolleyCallback;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.JOB_POSTING_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STATUS_SUCCESS;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_INDUSTRIAL_VISIT_VIEW;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_JOB_POSTING_UPDATE;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_JOB_POSTING_VIEW;

public class RepostJobPosting extends BaseActivity {
    String id, name, tech, jobTitle, type, vacancy, postedOn, other;
    //EditText  textViewOther;
    TextView textViewName, textViewTech, textViewJob, textViewType, textViewVacancy, textViewPosted, textViewEndDt, textViewOther;
    ProgressDialog progressDialog;
    Button buttonRepost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repost_job_posting);

        id = getIntent().getStringExtra("COMPANY_ID");

        initProgress();
        initializeView();
        getJobRepost();
    }

    private void initializeView() {
        textViewName = findViewById(R.id.name);
        textViewTech = findViewById(R.id.technology);
        textViewJob = findViewById(R.id.jobTitle);
        textViewType = findViewById(R.id.type);
        textViewVacancy = findViewById(R.id.vacancy);
        textViewPosted = findViewById(R.id.postDt);
        //textViewOther = findViewById(R.id.other);
        textViewName = findViewById(R.id.name);
        textViewTech = findViewById(R.id.technology);
        textViewJob = findViewById(R.id.jobTitle);
        textViewType = findViewById(R.id.type);
        textViewVacancy = findViewById(R.id.vacancy);
        textViewPosted = findViewById(R.id.postDt);
        textViewOther = findViewById(R.id.other);
        textViewEndDt = findViewById(R.id.endDt);

        // buttonClear = findViewById(R.id.btnClear);
        buttonRepost = findViewById(R.id.repost);
        buttonRepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().isConnectedToNetwork(RepostJobPosting.this)) {
                    jobRepost();
                    //finish();
                } else {
                   noNetwork();
                }
            }
        });

    }

    private void getJobRepost() {
        showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            requestJson.put("posting_id", MyApplication.getInstance().getFromSharedPreference(JOB_POSTING_ID));

            new NetworkRequestUtil(this).postData(URL_JOB_POSTING_VIEW, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    JobPostingViewModel jobPostingViewModel = new Gson().fromJson(response.toString(),JobPostingViewModel.class);
                    if (jobPostingViewModel != null) {
                        if (jobPostingViewModel.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            textViewName.setText(jobPostingViewModel.getJob_posting_view().getName());
                            textViewTech.setText(jobPostingViewModel.getJob_posting_view().getTechnology());
                            textViewJob.setText(jobPostingViewModel.getJob_posting_view().getJob_title());
                            textViewType.setText(jobPostingViewModel.getJob_posting_view().getType_id());
                            textViewVacancy.setText(jobPostingViewModel.getJob_posting_view().getVacancies());
                            textViewPosted.setText(jobPostingViewModel.getJob_posting_view().getPosted_date());
                            textViewOther.setText(jobPostingViewModel.getJob_posting_view().getOther());
                            textViewEndDt.setText(jobPostingViewModel.getJob_posting_view().getEnd_date());

                            //Toast.makeText(RepostJobPosting.this, jobPostingViewModel.getMessage(), Toast.LENGTH_SHORT).show();

                            showProgress(false);
                        } else {
                            showDialogWithOkButton(jobPostingViewModel.getMessage());
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


    private void jobRepost() {
        showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            requestJson.put("posting_id", MyApplication.getInstance().getFromSharedPreference(JOB_POSTING_ID));
            requestJson.put("name", textViewName.getText().toString());
            requestJson.put("posted_date", textViewPosted.getText().toString());
            requestJson.put("end_date", textViewEndDt.getText().toString());
            requestJson.put("job_title", textViewJob.getText().toString());
            requestJson.put("technology", textViewTech.getText().toString());
            requestJson.put("type_id", textViewType.getText().toString());
            requestJson.put("other", textViewOther.getText().toString());
            requestJson.put("vacancies", textViewVacancy.getText().toString());

            new NetworkRequestUtil(this).postData(URL_JOB_POSTING_UPDATE, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    CommonModel1 commonModel1 = new Gson().fromJson(response.toString(), CommonModel1.class);
                    if (commonModel1 != null) {
                        if (commonModel1.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            //Toast.makeText(RepostJobPosting.this, commonModel1.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(RepostJobPosting.this, "Repost Successfully", Toast.LENGTH_SHORT).show();
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
        return "RepostJobPosting";
    }

    /* Hide key board*/
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void initProgress() {
        if (getApplicationContext() == null) {
            progressDialog = new ProgressDialog(RepostJobPosting.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(RepostJobPosting.this);
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
        startActivity(new Intent(RepostJobPosting.this, NoNetwork.class));
    }

}
