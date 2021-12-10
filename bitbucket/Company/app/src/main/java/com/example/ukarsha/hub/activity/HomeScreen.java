package com.example.ukarsha.hub.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.application.MyApplication;
import com.example.ukarsha.hub.model.ProfileModel;
import com.example.ukarsha.hub.utils.NetworkRequestUtil;
import com.example.ukarsha.hub.utils.VolleyCallback;
import com.google.gson.Gson;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.COMPANY_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.LAST_SIGNIN_TIME;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.PREF_USER_LOGE_IN;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STATUS_SUCCESS;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_PROFILE_NEW;

public class HomeScreen extends BaseActivity implements View.OnClickListener {
    ProgressDialog progressDialog;
    CardView cardView, cardViewOne, scrollOne, scrollTwo, scrollThree, scrollFour;
    JumpingBeans jumpingBeans1, jumpingBeans2, jumpingBeans3, jumpingBeans11, jumpingBeans12;
    TextView textViewMore1, textViewMore2, textViewMore3, textViewMore4, textViewTime,
            textViewCmpyName, textViewEmail, textViewContact;
    ImageView textView1, textView2, textView3, coming1;
    FloatingActionButton fab;
    CardView cvProfile, cvjob, cvCom, cvPosting, cvHistory, cvApplied, cd1, cd2, cd3, cd4, cd5;
    boolean doubleBackToExitPressedOnce = false;
    TextView textViewHeading;
    HorizontalScrollView mscrollView;
    LinearLayout linearLayout;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
       /* Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);*/

        casting();
        profileDetails();
        jumpingBeans1 = JumpingBeans.with(textViewMore1)
                .appendJumpingDots()
                .build();

        jumpingBeans2 = JumpingBeans.with(textViewMore2)
                .appendJumpingDots()
                .build();

        jumpingBeans11 = JumpingBeans.with(textViewMore3)
                .appendJumpingDots()
                .build();


        textViewHeading = findViewById(R.id.companyName);
        jumpingBeans3 = JumpingBeans.with(textViewHeading)
                .makeTextJump(0, textViewHeading.getText().toString().indexOf(' '))
                .setIsWave(true)
                .setLoopDuration(1000)  // ms
                .build();


        coming1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
        textView2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
        //textView3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));

        Animation card1 = AnimationUtils.loadAnimation(HomeScreen.this, R.anim.trans_left);
        cd1.startAnimation(card1);

        //Animation card2 = AnimationUtils.loadAnimation(HomeScreen.this, R.anim.trans_right);
        //cvjob.startAnimation(card2);

        Animation card4 = AnimationUtils.loadAnimation(HomeScreen.this, R.anim.trans_right);
        cd2.startAnimation(card4);

        Animation card5 = AnimationUtils.loadAnimation(HomeScreen.this, R.anim.trans_left);
        cd3.startAnimation(card5);

        Animation card6 = AnimationUtils.loadAnimation(HomeScreen.this, R.anim.trans_right);
        cd4.startAnimation(card6);

        Animation card3 = AnimationUtils.loadAnimation(HomeScreen.this, R.anim.trans_left);
        cd5.startAnimation(card3);

        mscrollView = findViewById(R.id.horizontalScrollView);
        RelativeLayout relativeLayout = findViewById(R.id.rel);
        relativeLayout.startAnimation((Animation) AnimationUtils.loadAnimation(this, R.anim.card));

    }

    public void casting() {
       /* fab = findViewById(R.id.home);
        fab.setOnClickListener(this);*/
        cvProfile = findViewById(R.id.cVProfile);
        cvProfile.setOnClickListener(this);
        //cvjob = findViewById(R.id.cVJobApp);
        //cvjob.setOnClickListener(this);
        coming1 = findViewById(R.id.cs1);
        cvCom = findViewById(R.id.cVComm);
        cvPosting = findViewById(R.id.cVPost);
        cvPosting.setOnClickListener(this);
        cvHistory = findViewById(R.id.cVHistory);
        cvHistory.setOnClickListener(this);
        cvApplied = findViewById(R.id.cVApplied);
        cvApplied.setOnClickListener(this);
        cd1 = findViewById(R.id.cardDesign1);
        cd2 = findViewById(R.id.cardDesign2);
        cd3 = findViewById(R.id.cardDesign3);
        cd4 = findViewById(R.id.cardDesign4);
        cd5 = findViewById(R.id.cardDesign5);
        textViewMore1 = findViewById(R.id.more1);
        textViewMore2 = findViewById(R.id.more2);
        textViewMore3 = findViewById(R.id.more3);
        //textViewMore4 = findViewById(R.id.more4);
        //textView1 = findViewById(R.id.cs1);
        textView2 = findViewById(R.id.cs2);
        //textView3 = findViewById(R.id.cs3);
        scrollOne = findViewById(R.id.cardOne);
        scrollOne.setOnClickListener(this);
        scrollTwo = findViewById(R.id.cardTwo);
        scrollTwo.setOnClickListener(this);
        scrollThree = findViewById(R.id.cardThree);
        scrollThree.setOnClickListener(this);
        scrollFour = findViewById(R.id.cardFour);
        scrollFour.setOnClickListener(this);
        linearLayout = findViewById(R.id.linear);
        textViewCmpyName = findViewById(R.id.companyName);
        textViewEmail = findViewById(R.id.email);
        textViewContact = findViewById(R.id.contact);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cVProfile:
                startActivity(new Intent(HomeScreen.this, ViewProfile.class));
                break;

            case R.id.cVPost:
                startActivity(new Intent(HomeScreen.this, Posts.class));
                break;


            case R.id.cVHistory:
                startActivity(new Intent(HomeScreen.this, List3.class));
                break;

            /*case R.id.cVApplied:
                startActivity(new Intent(HomeScreen.this, AppliedList3.class));
                break;*/

            case R.id.cardOne:
                startActivity(new Intent(HomeScreen.this, Promotion.class));
                break;

            case R.id.cardTwo:
                startActivity(new Intent(HomeScreen.this, Promotion.class));
                break;

            case R.id.cardThree:
                startActivity(new Intent(HomeScreen.this, Promotion.class));
                break;

            case R.id.cardFour:
                startActivity(new Intent(HomeScreen.this, Promotion.class));
                break;

                
           /* case R.id.home:
                startActivity(new Intent(HomeScreen.this, Login.class));
                break;*/
        }
    }


    /* @Override
     public void onBackPressed() {
         if (doubleBackToExitPressedOnce) {
             super.onBackPressed();
             return;
         }

         this.doubleBackToExitPressedOnce = true;
         Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

         new Handler().postDelayed(new Runnable() {

             @Override
             public void run() {
                 doubleBackToExitPressedOnce=false;
             }
         }, 2000);
     }
 */

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
                            //Toast.makeText(HomeScreen.this, profileModel.getMessage(), Toast.LENGTH_SHORT).show();
                            textViewCmpyName.setText(profileModel.getResult().getName());
                            textViewEmail.setText(profileModel.getResult().getEmail());
                            textViewContact.setText(profileModel.getResult().getContact_no());
                            MyApplication.getInstance().addBooleanToSharedPreference(PREF_USER_LOGE_IN, true);
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String formattedDate = df.format(c.getTime());
                            MyApplication.getInstance().addToSharedPreference(LAST_SIGNIN_TIME, formattedDate);
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
    protected void onResume() {
        super.onResume();
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        // .... other stuff in my onResume ....
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.exit_press_back_twice_message, Toast.LENGTH_SHORT).show();
    }*/


    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void initProgress() {
        if (getApplicationContext() == null) {
            progressDialog = new ProgressDialog(HomeScreen.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(HomeScreen.this);
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
    protected String getTag() {
        return "HomeScreen";
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showConfirmationMessage();

    }

    private void showConfirmationMessage() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(HomeScreen.this);
        builder.setTitle(getString(R.string.title))
                .setMessage(getString(R.string.do_you))
                .setCancelable(true)
                .setPositiveButton("LogOut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyApplication.getInstance().clearAllPreferences();
                        MyApplication.getInstance().addBooleanToSharedPreference(PREF_USER_LOGE_IN, false);
                        Intent intent = new Intent(HomeScreen.this, Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        /* dialogInterface.dismiss();
                        DashboardMain.super.onBackPressed();
                        finish();*/
                    }
                }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        android.support.v7.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}
