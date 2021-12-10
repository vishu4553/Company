package com.example.donorapp.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import com.example.donorapp.Activity.Login;
import com.example.donorapp.R;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CommonMethods {
    public static void logOut(Activity activity) {
        SharedPreferences preferences =activity.getSharedPreferences("PSI_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(activity, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    public static void displayResposeToast(Context applicationContext, String message) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show();
    }

    public static String getTextFromView(EditText view) {
        return view.getText().toString().trim();
    }

    public static String getLocalToUTCDateSurvey(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date time = calendar.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFmt = new SimpleDateFormat("dd/MM/yyyy");
        outputFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        return outputFmt.format(time);
    }

    public static String getLocalToUTCDateSurveyChangeFormat(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date time = calendar.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFmt = new SimpleDateFormat("MM/dd/yyyy");
        outputFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        return outputFmt.format(time);
    }
    public static String getLocalToUTCDateSurveyChangeFormatWithTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date time = calendar.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFmt = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        outputFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        return outputFmt.format(time);
    }

    public static String utcToLocalDateTime(String dateTime)
    {

        String dateToReturn = dateTime;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date gmt = null;

        SimpleDateFormat sdfOutPutToSend = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        sdfOutPutToSend.setTimeZone(TimeZone.getDefault());

        try {

            gmt = sdf.parse(dateToReturn);
            dateToReturn = sdfOutPutToSend.format(gmt);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateToReturn;
    }

    public static String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static void DialogCall(Activity activity,String title, String message)
    {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

            alertDialogBuilder.setTitle(title);
            alertDialogBuilder
                    .setMessage(message)
                    .setCancelable(false)


                    .setNegativeButton(activity.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    }).setPositiveButton(activity.getResources().getString(R.string.Yes_Title),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            //   alertDialog.getWindow().setBackgroundDrawableResource(R.color.colorLineGray);

            alertDialog.show();
        }

    public static void logout(String title, String message, Activity  admin) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(admin);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)


                .setNegativeButton(admin.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).setPositiveButton(admin.getResources().getString(R.string.Yes_Title),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CommonMethods.logOut(admin);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        //   alertDialog.getWindow().setBackgroundDrawableResource(R.color.colorLineGray);

        alertDialog.show();
    }

    public static String getCurrentDate(String format) {
        return new SimpleDateFormat(format).format(new Date());

    }

    public static String getLastMonthDate(String mmDdYyyy) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(mmDdYyyy);
        String dateOutput = format.format(date);
        return dateOutput;
    }
    public static String get12HrFrom24(String time){
        String converted = "";

        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdf.parse(time);
            converted =  new SimpleDateFormat("K:mm a").format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return converted;
    }


    public static String getPlaneDate(String invoiceDate) {
        String time = invoiceDate.substring(11,invoiceDate.length()-1);
        return formatDate(invoiceDate.substring(0,10),"yyyy-MM-dd","dd-MM-yyyy")+" "+get12HrFrom24(time);
    }

    public static String formatDate (String date, String initDateFormat, String endDateFormat) {

        String parsedDate = "";
        try {
            Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
            parsedDate = formatter.format(initDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return parsedDate;
    }
}
