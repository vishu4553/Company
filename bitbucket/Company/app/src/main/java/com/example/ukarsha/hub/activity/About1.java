package com.example.ukarsha.hub.activity;

import android.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.codesgood.views.JustifiedTextView;
import com.example.ukarsha.hub.*;
import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.application.MyApplication;
import com.example.ukarsha.hub.model.CommonModel;
import com.example.ukarsha.hub.utils.NetworkRequestUtil;
import com.example.ukarsha.hub.utils.VolleyCallback;
import com.google.gson.Gson;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import org.json.JSONObject;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.COMPANY_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.EMAIL;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.PLAYER_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STATUS_SUCCESS;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_FEEDBACK;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_UPDATE_PROFILE_NEW;

public class About1 extends BaseActivity implements View.OnClickListener {
    TextView textViewAbout, textViewContactUs, textViewFaq;
    JustifiedTextView textViewExp1, textViewExp12, textViewExp13, textViewExp14;
    HTextView hTextView, hTextView1, hTextView2;
    HorizontalScrollView mscrollView;
    LinearLayout textViewContact, textViewEmail;
    ImageButton plus11, minus11, plus12, minus12, plus13, minus13, plus14, minus14;
    Button buttonSave;
    EditText editTextEmail, editTextComment;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ukarsha.hub.R.layout.activity_about1);

        TextView marque = (TextView) this.findViewById(R.id.marque_scrolling_text);
        marque.setSelected(true);

        initialiseView();


        hTextView = (HTextView) findViewById(R.id.text);
        hTextView.setAnimateType(HTextViewType.TYPER);
        hTextView.animateText("About Us"); // anim

        hTextView1 = (HTextView) findViewById(R.id.text1);
        hTextView1.setAnimateType(HTextViewType.TYPER);
        hTextView1.animateText("Glances"); // anim

        textViewFaq = findViewById(R.id.what);
        textViewFaq.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
        textViewFaq.animate().x(20)
                .setDuration(5000)
                .setStartDelay(1000);

        textViewExp1 = findViewById(R.id.txt11);
        plus11 = (ImageButton) findViewById(R.id.plus11);
        minus11 = (ImageButton) findViewById(R.id.minus11);
        plus11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                plus11.setVisibility(View.GONE);
                minus11.setVisibility(View.VISIBLE);
                textViewExp1.setMaxLines(Integer.MAX_VALUE);

            }
        });
        minus11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                minus11.setVisibility(View.GONE);
                plus11.setVisibility(View.VISIBLE);
                textViewExp1.setMaxLines(0);

            }
        });

        /*FAQ 1*/

       /* textViewExp12 = findViewById(R.id.txt);
        plus12 = (ImageButton) findViewById(R.id.plus);
        minus12 = (ImageButton) findViewById(R.id.minus);
        plus12.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                plus12.setVisibility(View.GONE);
                minus12.setVisibility(View.VISIBLE);
                textViewExp12.setMaxLines(Integer.MAX_VALUE);

            }
        });
        minus12.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                minus12.setVisibility(View.GONE);
                plus12.setVisibility(View.VISIBLE);
                textViewExp12.setMaxLines(0);

            }
        });*/

      /*  *//*FAQ 2*//*

        textViewExp13 = findViewById(R.id.txt2);
        plus13 = (ImageButton) findViewById(R.id.plus2);
        minus13 = (ImageButton) findViewById(R.id.minus2);
        plus13.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                plus13.setVisibility(View.GONE);
                minus13.setVisibility(View.VISIBLE);
                textViewExp13.setMaxLines(Integer.MAX_VALUE);

            }
        });
        minus13.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                minus13.setVisibility(View.GONE);
                plus13.setVisibility(View.VISIBLE);
                textViewExp13.setMaxLines(0);

            }
        });

        *//*FAQ 3*//*

        textViewExp14 = findViewById(R.id.txt3);
        plus14 = (ImageButton) findViewById(R.id.plus3);
        minus14 = (ImageButton) findViewById(R.id.minus3);
        plus14.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                plus14.setVisibility(View.GONE);
                minus14.setVisibility(View.VISIBLE);
                textViewExp14.setMaxLines(Integer.MAX_VALUE);

            }
        });
        minus14.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                minus14.setVisibility(View.GONE);
                plus14.setVisibility(View.VISIBLE);
                textViewExp14.setMaxLines(0);

            }
        });
*/
    }

    private void initialiseView() {
        textViewAbout = (TextView) findViewById(R.id.aboutUs);
        textViewContactUs = (TextView) findViewById(R.id.contactUs);

        final LinearLayout linearAbout = findViewById(R.id.linearAboutUs);
        final LinearLayout linearContact = findViewById(R.id.linearContactUs);
        linearContact.setVisibility(View.GONE);
        linearAbout.setVisibility(View.VISIBLE);

        textViewAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewAbout.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                textViewContactUs.setTextColor(getApplicationContext().getResources().getColor(R.color.fin_light));
                linearContact.setVisibility(View.GONE);
                linearAbout.setVisibility(View.VISIBLE);

            }
        });

        textViewContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewContactUs.setTextColor(getApplicationContext().getResources().getColor(R.color.white));
                textViewAbout.setTextColor(getApplicationContext().getResources().getColor(R.color.fin_light));
                linearContact.setVisibility(View.VISIBLE);
                linearAbout.setVisibility(View.GONE);

            }
        });

        mscrollView = findViewById(R.id.horizontalScrollView);
        RelativeLayout relativeLayout = findViewById(R.id.rel);
        relativeLayout.startAnimation((Animation) AnimationUtils.loadAnimation(this, R.anim.card));

        editTextEmail = findViewById(R.id.edEmail);
        editTextEmail.setText(MyApplication.getInstance().getFromSharedPreference(EMAIL));
        editTextComment = findViewById(R.id.edComment);
        buttonSave = findViewById(R.id.save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getInstance().isConnectedToNetwork(About1.this)) {
                    validation();
                } else {
                    Toast.makeText(About1.this, "Network Problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getFeedback (){
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
            requestJson.put("comp_id", MyApplication.getInstance().getFromSharedPreference(COMPANY_ID));
            //requestJson.put("comp_id", "6");
            requestJson.put("comments", editTextComment.getText().toString());

            new NetworkRequestUtil(this).postData(URL_FEEDBACK, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    final CommonModel commonModel = new Gson().fromJson(response.toString(), CommonModel.class);
                    if (commonModel != null) {
                        if (commonModel.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            Toast.makeText(About1.this, commonModel.getMessage(), Toast.LENGTH_SHORT).show();

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
            progressDialog = new ProgressDialog(About1.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(About1.this);
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
        return "About1";
    }

    @Override
    public void onClick(View view) {

    }

    public void validation() {
        final String comment = editTextComment.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(comment)) {
            editTextComment.setError("Please enter your username");
            editTextComment.requestFocus();
            return;
        }

        getFeedback();

    }


}