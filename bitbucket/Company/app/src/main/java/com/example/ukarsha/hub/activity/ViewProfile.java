package com.example.ukarsha.hub.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.application.MyApplication;
import com.example.ukarsha.hub.model.ProfileModel;
import com.example.ukarsha.hub.utils.NetworkRequestUtil;
import com.example.ukarsha.hub.utils.VolleyCallback;
import com.google.gson.Gson;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.COMPANY_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STATUS_SUCCESS;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_PROFILE;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_PROFILE_NEW;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.USER_ID;

public class ViewProfile extends BaseActivity implements View.OnClickListener {
    ProgressDialog progressDialog;
    private TextView editTextCmyName, editTextEmail, editTextContactNo, editTextPassword, editTextCPassowrd,
            editTextCity, editTextAddress, editTextWebsite, editText;
    HTextView hTextView, hTextView1;
    ImageView imageViewBack;
    TextView textViewCName, textViewCEmail;
    JumpingBeans jumpingBeans1;
    TextView textViewMore;
    LinearLayout lnMore;
    CardView imageViewEdit;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        initProgress();
        initializeView();


        hTextView1 = (HTextView) findViewById(R.id.text1);
        hTextView1.setAnimateType(HTextViewType.TYPER);
        hTextView1.animateText("Profile");

        /*hTextView = (HTextView) findViewById(R.id.text);
        hTextView.setAnimateType(HTextViewType.ANVIL);
        hTextView.animateText("Profile");*/

       /* imageViewBack = findViewById(R.id.back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ViewProfile.this, HomeScreen.class));
            }
        });*/

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = "Time:" + mdformat.format(calendar.getTime());
        display(strDate);


        imageViewEdit = findViewById(R.id.editProf);
        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewProfile.this, Profile.class));
            }
        });

        textViewMore = findViewById(R.id.more);
        jumpingBeans1 = JumpingBeans.with(textViewMore)
                .appendJumpingDots()
                .build();


        if (MyApplication.getInstance().isConnectedToNetwork(ViewProfile.this)) {
            profileDetails();
        } else {
            noNetwork();
        }

       /* textViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnMore.setVisibility(View.VISIBLE);
            }
        });*/


    }

    private void display(String num) {
        TextView textView = (TextView) findViewById(R.id.current_time_view);
        textView.setText(num);
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
        textViewCName = findViewById(R.id.name);
        textViewCEmail = findViewById(R.id.email);
        lnMore = findViewById(R.id.linear);
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
                            //Toast.makeText(ViewProfile.this, profileModel.getMessage(), Toast.LENGTH_SHORT).show();
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
                            //
                            textViewCName.setText(profileModel.getResult().getName());
                            textViewCEmail.setText(profileModel.getResult().getEmail());
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
            progressDialog = new ProgressDialog(ViewProfile.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(ViewProfile.this);
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

    public void noNetwork(){
        startActivity(new Intent(ViewProfile.this, NoNetwork.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                startActivity(new Intent(ViewProfile.this, Profile.class));
                return true;
            case R.id.item2:
                Toast.makeText(getApplicationContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

}