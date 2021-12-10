package com.example.donorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.donorapp.Activity.DonarActivity;
import com.kyanogen.signatureview.SignatureView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Signature extends AppCompatActivity {
    SignatureView signatureView;
    Button clear, save;
    ImageView img_arrow;
    String path;
    Bitmap bitmap;
    public static final String IMAGE_DIRECTORY = "/signdemo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        clear = findViewById(R.id.clearSignature);
        save = findViewById(R.id.saveSignature);
        signatureView = findViewById(R.id.signature_view);
        img_arrow = findViewById(R.id.arrow_back);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();
                grantPermission();
                Intent intent = new Intent();
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                intent.putExtra("byteArray", bs.toByteArray());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signature.this, DonarActivity.class));
            }
        });
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.PNG, 50, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
            Log.d("hhhhh",wallpaperDirectory.toString());
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(Signature.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("Name", "MyImage");
            contentValues.put("Image", String.valueOf(bytes));
            // sqLiteDatabase.insert("ImageMore", null, contentValues);
            fo.close();
            //Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            //  Toast.makeText(this, "Image save to local ", Toast.LENGTH_SHORT).show();

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }
    private void grantPermission(){
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {
                path = saveImage(bitmap);

            } else {
                requestPermission(); // Code for permission
            }
        }
        else
        {

        }
    }

    private final  int PERMISSION_REQUEST_CODE=100;
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Signature.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Signature.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(Signature.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(Signature.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                    path = saveImage(bitmap);
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public void validationSignature() {
        if (null!=signatureView) {
            Toast.makeText(this, "Enter Signature", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Intent i=new Intent(this,DonarActivity.class);
        finish();
    }
}
