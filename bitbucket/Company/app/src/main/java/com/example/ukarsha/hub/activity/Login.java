package com.example.ukarsha.hub.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.application.MyApplication;
import com.example.ukarsha.hub.model.LoginModel;
import com.example.ukarsha.hub.utils.NetworkRequestUtil;
import com.example.ukarsha.hub.utils.VolleyCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

import net.frakbot.jumpingbeans.JumpingBeans;

import org.json.JSONObject;
;

import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.COMPANY_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.COMPANY_NAME;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.EMAIL;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.MOBILE;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.PLAYER_ID;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.STATUS_SUCCESS;
import static com.example.ukarsha.hub.utils.AppConstantsAndUtils.URL_LOGIN_NEW;

public class Login extends BaseActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    LinearLayout linearEd1, linearEd2;
    private EditText editTextUserName, editTextPassword;
    TextView textView, textViewForgot;
    Button buttonLogin, buttonRegister;
    ImageView imageView, buttonShow;
    CheckBox checkBoxRem;
    JumpingBeans jumpingBeans1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    int currentProgress = 80;
    private static int splashTimeOut = 3000;
    String user;
    HTextView hTextView;
    private FirebaseAuth mAuth;
    FirebaseUser user1;
    String LoggedIn_User_Email, notificationKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        OneSignal.startInit(this).setNotificationOpenedHandler(new Login.ExampleNotificationOpenedHandler())
                .init();

        initProgress();
        initializeView();

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(KEY_REMEMBER, false))
            checkBoxRem.setChecked(true);
        else
            checkBoxRem.setChecked(false);

        editTextUserName.setText(sharedPreferences.getString(KEY_USERNAME, ""));
        editTextPassword.setText(sharedPreferences.getString(KEY_PASS, ""));

        editTextUserName.addTextChangedListener(this);
        editTextPassword.addTextChangedListener(this);
        checkBoxRem.setOnCheckedChangeListener(this);

        String message = getIntent().getStringExtra("message");
        //textView.setText(message);
        editTextUserName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom));
        editTextPassword.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom));


        Animation swipe4 = AnimationUtils.loadAnimation(Login.this, R.anim.trans_right);
        linearEd1.startAnimation(swipe4);


        Animation swipe6 = AnimationUtils.loadAnimation(Login.this, R.anim.trans_left);
        linearEd2.startAnimation(swipe6);

        Animation animation = new TranslateAnimation(0, 0, 500, 0);
        animation.setDuration(1500);
        buttonLogin.startAnimation(animation);

        jumpingBeans1 = JumpingBeans.with(checkBoxRem)
                .makeTextJump(0, checkBoxRem.getText().toString().indexOf(' '))
                .setIsWave(true)
                .setLoopDuration(1000)  // ms
                .build();

        hTextView = (HTextView) findViewById(R.id.text);
       // hTextView.setAnimateType(HTextViewType.ANVIL);
        hTextView.animateText("Login");

        getID();
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

    private void initializeView() {
        editTextUserName = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        checkBoxRem = findViewById(R.id.check);
        linearEd1 = findViewById(R.id.ed1);
        linearEd2 = findViewById(R.id.ed2);
        buttonLogin = findViewById(R.id.login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vibrate();
                if (MyApplication.getInstance().isConnectedToNetwork(Login.this)) {
                    validation();
                    OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
                    status.getSubscriptionStatus().getUserId();
                } else {
                    showMyDiag();
                }

            }
        });
        buttonRegister = (Button) findViewById(R.id.register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.animate().alpha(0f).setDuration(200);
                startActivity(new Intent(Login.this, Registration.class));


            }
        });

        textViewForgot = findViewById(R.id.forgot);
        textViewForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgotPswd.class));
            }
        });
    }

    public void gotoHome() {
        //startActivity(new Intent(Login.this, HomeScreen.class));
        startActivity(new Intent(Login.this, Loading.class));
    }


    private void authenticateUser() {
        //showProgress(true);
        final JSONObject requestJson;

        try {
            requestJson = new JSONObject();
           /* requestJson.put("email", "tcs@gmail.com");
            requestJson.put("password",  "pass123");*/
            requestJson.put("email", editTextUserName.getText().toString().trim());
            requestJson.put("password",  editTextPassword.getText().toString().trim());
            requestJson.put("player_id", PLAYER_ID);

            new NetworkRequestUtil(this).postData(URL_LOGIN_NEW, requestJson, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    LoginModel loginModel = new Gson().fromJson(response.toString(), LoginModel.class);
                    if (loginModel != null) {
                        if (loginModel.getSuccess().equalsIgnoreCase(STATUS_SUCCESS)) {
                            //Toast.makeText(getApplicationContext(), loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                            /*showCustomDialog();*/
                            gotoHome();
                            MyApplication.getInstance().addToSharedPreference(COMPANY_ID, loginModel.getResult().getComp_id());
                            MyApplication.getInstance().addToSharedPreference(EMAIL, loginModel.getResult().getEmail());
                            MyApplication.getInstance().addToSharedPreference(COMPANY_NAME, loginModel.getResult().getName());
                            MyApplication.getInstance().addToSharedPreference(MOBILE, loginModel.getResult().getContact_no());
                            //Toast.makeText(Login.this, loginModel.getResult().getComp_id() + "", Toast.LENGTH_SHORT).show();
                        } else showDialogWithOkButton(loginModel.getMessage());
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

  /*  //adding validation to edittexts
        awesomeValidation.addValidation(this, R.id.editTextName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.editTextPassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.editTextMobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);*/

    @Override
    protected String getTag() {
        return "Login";
    }

    /* Hide key board*/
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void initProgress() {
        if (getApplicationContext() == null) {
            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setMessage(getApplicationContext().getString(R.string.message_loading));
        } else {
            progressDialog = new ProgressDialog(Login.this);
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
        final String username = editTextUserName.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUserName.setError("Please enter your username");
            editTextUserName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        authenticateUser();

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
        text.setText("Login Successfully");
        Button button = (Button) dialogView.findViewById(R.id.ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //startActivity(new Intent(Login.this, HomeScreen.class));
                //authenticateUser();
                finish();
            }
        });
        PopupWindow mPopupWindow = new PopupWindow(dialogView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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

        int cx = (int) (buttonLogin.getX() + (buttonLogin.getWidth() / 2));
        int cy = (int) (buttonLogin.getY()) + buttonLogin.getHeight() + 56;


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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        managePrefs();
    }

    private void managePrefs() {
        if (checkBoxRem.isChecked()) {
            editor.putString(KEY_USERNAME, editTextUserName.getText().toString().trim());
            editor.putString(KEY_PASS, editTextPassword.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        } else {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
           /* case R.id.show:
                show();
                break;*/
        }
    }

    public void show() {
        editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        //edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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


