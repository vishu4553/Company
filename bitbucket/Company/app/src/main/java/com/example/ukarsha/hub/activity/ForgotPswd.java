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
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.example.ukarsha.hub.model.FrgtPswdModel;
import com.example.ukarsha.hub.utils.NetworkRequestUtil;
import com.example.ukarsha.hub.utils.VolleyCallback;
import com.google.gson.Gson;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import org.json.JSONObject;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.EMAIL;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STATUS_SUCCESS;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_FORGOT_PASSWORD;

public class ForgotPswd extends BaseActivity {
    ProgressDialog progressDialog;
    Button buttonSave, buttonCancel;
    EditText editTextEmail;
    public int counter;
    Button button;
    TextView textView;
    ImageView imageViewSmile, imageViewPuz;
    HTextView hTextView;
    TextView textView1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pswd);
        initProgress();
        initializeView();

        textView = findViewById(R.id.textView);
        imageViewSmile = findViewById(R.id.imageView);
        imageViewPuz = findViewById(R.id.imageView1);

        hTextView = findViewById(R.id.text);
        hTextView.setAnimateType(HTextViewType.ANVIL);
        hTextView.animateText("Forgot Password ");
    }

    public void countDown() {
        new CountDownTimer(10000, 500) {
            public void onTick(long millisUntilFinished) {
                textView.setText(String.valueOf(counter));
                counter++;


            }

            public void onFinish() {
                textView.setText(R.string.password_send);
                imageViewSmile.setVisibility(View.GONE);
                imageViewPuz.setVisibility(View.VISIBLE);
                imageViewSmile.animate().y(100);
            }
        }.start();
    }

    private void initializeView() {
        editTextEmail = findViewById(R.id.edEmail);
        textView1 = findViewById(R.id.text1);
        textView1.setText("");
        buttonSave = findViewById(R.id.btnSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getInstance().isConnectedToNetwork(ForgotPswd.this)) {
                    validation();
                    //finish();
                } else {
                  noNetwork();
                }

            }
        });

        buttonCancel = findViewById(R.id.btnCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPswd.this, Login.class));
            }
        });

    }

    // method for forgot password
    private void forgotPassword() {
        // showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            //requestJson.put("email", MyApplication.getInstance().getFromSharedPreference(EMAIL));
            requestJson.put("email", MyApplication.getInstance().getFromSharedPreference(EMAIL));

            new NetworkRequestUtil(this).postData(URL_FORGOT_PASSWORD, requestJson, new VolleyCallback() {

                @Override
                public void onSuccess(JSONObject response) {
                    FrgtPswdModel frgtPswdModel = new Gson().fromJson(response.toString(), FrgtPswdModel.class);
                    if (frgtPswdModel != null) {
                        if (frgtPswdModel.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            Toast.makeText(getApplicationContext(), frgtPswdModel.getMessage(), Toast.LENGTH_SHORT).show();
                            //onFinish();
                            //showProgress(false);
                        } else showDialogWithOkButton(frgtPswdModel.getMessage());
                    } else {
                        showDialogWithOkButton(getString(R.string.error_someting_went_wrong));
                    }
                }

                //@Override
                public void onError(VolleyError error) {
                    printLog("error Response:" + error);
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    protected String getTag() {
        return "";
    }

    //Hide key board
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void initProgress() {
        if (getApplicationContext() == null) {
            progressDialog = new ProgressDialog(ForgotPswd.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(ForgotPswd.this);
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

    public void validation() {
        final String email = editTextEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        //validating inputs
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your username");
            editTextEmail.requestFocus();
            return;
        }

        forgotPassword();
        countDown();
    }
    public void noNetwork(){
        startActivity(new Intent(ForgotPswd.this, NoNetwork.class));
    }
}

