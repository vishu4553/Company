package com.example.ukarsha.hub.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.example.ukarsha.hub.R;


/**
 * Created by Satyam on 4/21/2018.
 */

public class MessageUtil {

    private Context context;

    public MessageUtil(Context context) {
        this.context = context;
    }

    public void showSnackBar(View container, String message) {
        //TODO scack bar design
        Snackbar snackbar = Snackbar.make(container, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.snac_bar_bg));
        TextView tv = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(context, R.color.white));
        snackbar.show();
    }
}
