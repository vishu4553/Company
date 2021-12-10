package com.example.ukarsha.hub.activity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ukarsha.hub.R;
import com.example.ukarsha.hub.application.MyApplication;
import com.example.ukarsha.hub.callback.OnOkClick;

public abstract class BaseActivity extends AppCompatActivity {

    private static BaseActivity mInstance;
    private static boolean changeView = false;
    ProgressDialog progressDialog;

    public static synchronized BaseActivity getInstance() {
        return mInstance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mInstance = this;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onUserInteraction() {
        // TODO Auto-generated method stub
        super.onUserInteraction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeView = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //active = false;
       MyApplication.getInstance().clearPreferences();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /* *********************************************** Validation************************************** */


    public boolean isValid(String value) {
        return (value != null) && !value.isEmpty();
    }

    public boolean isValid(View view) {
        return (view != null);
    }

    /**************************
     * NETWORK AVAILABILITY
     *****************************/

    public boolean isConnectedToNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /* for printing Log*/
    public void printLog(String message) {
        Log.e(getTag(), message);
    }

    protected abstract String getTag();

    public void showErrorDialog(Context context, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle(context.getString(R.string.error_title));
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    public void showDialogWithOkButton(String message, final OnOkClick onOkClick) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.layout_dialog_ok, null);
        builder.setView(dialogView);
        final android.support.v7.app.AlertDialog alertDialog = builder.create();
        TextView TextViewMessage = (TextView) dialogView.findViewById(R.id.messageTv);
        TextViewMessage.setText(message);
        Button buttonOk = (Button) dialogView.findViewById(R.id.ok_btn);
        buttonOk.setText(getString(R.string.ok));
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                onOkClick.OnOkClicked();
            }
        });
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wmlp = window.getAttributes();
        wmlp.windowAnimations = R.style.dialog_animation;
        window.setAttributes(wmlp);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }

    public void showDialogWithOkButton(String message) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.layout_dialog_ok, null);
        builder.setView(dialogView);
        final android.support.v7.app.AlertDialog alertDialog = builder.create();
        TextView TextViewMessage = (TextView) dialogView.findViewById(R.id.messageTv);
        TextViewMessage.setText(message);
        Button buttonOk = (Button) dialogView.findViewById(R.id.ok_btn);
        buttonOk.setText(getString(R.string.ok));
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        Window window = alertDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams wmlp = window.getAttributes();
            wmlp.windowAnimations = R.style.dialog_animation;
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setAttributes(wmlp);
        }
        alertDialog.show();
    }

    public void showDialogWithOkCancelButton(String message, final OnOkClick onOkClick) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.layout_dialog_ok_cancel, null);
        builder.setView(dialogView);
        final android.support.v7.app.AlertDialog alertDialog = builder.create();
        TextView TextViewMessage = (TextView) dialogView.findViewById(R.id.messageTv);
        TextViewMessage.setText(message);
        Button yes_btn = (Button) dialogView.findViewById(R.id.yes_btn);
        Button cancel_btn = (Button) dialogView.findViewById(R.id.cancel_btn);
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOkClick.OnOkClicked();
                alertDialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        Window window = alertDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams wmlp = window.getAttributes();
            wmlp.windowAnimations = R.style.dialog_animation;
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setAttributes(wmlp);
        }
        alertDialog.show();
    }


    public void showSnackBar(View container, String message) {
        //TODO scack bar design
        if (message == null) message = "";
        Snackbar snackbar = Snackbar.make(container, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.snac_bar_bg));
        TextView tv = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.snac_bar_text));
        snackbar.show();
    }

    public void showNoNetworkMessage() {
        showDialogWithOkButton(getString(R.string.error_no_network));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



}
