package com.example.ukarsha.hub.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by satyam on 16/11/2017.
 */

public class ImageUtil {

    public static final String SUB_FOLDER_NAME_NEWS = "/CaptureImage/";
    public static final String APP_FOLDER = "/OCW";
    /**
     * returning image / video
     */
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    /******************* Getting Camera Imag with Rotatting **********************/
    private static final String IMAGE_DIRECTORY_NAME = APP_FOLDER + SUB_FOLDER_NAME_NEWS;

    public static boolean isExternalStorageFound() {
        return Environment.isExternalStorageEmulated();
    }

    public static void printLog(String message) {
        Log.d("ImageUtil:", message + "");
    }

    public static Uri getOutputMediaFileUri(int type, Context context) {
        if (!Environment.isExternalStorageEmulated()) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            // Create a media file name
           /* return LegacyCompatFileProvider.getUriForFile(context, context.getApplicationContext().getPackageName()
                    + ".my.package.name.provider", getOutputMediaFile(type));*/
        }
        //return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".provider", getOutputMediaFile(type));
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public static File getOutputMediaFile(int type) {
        // External sdcard location


        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);


        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Error", "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage, Context context) throws IOException {
        if (selectedImage.getScheme().equals("content")) {
            String[] projection = {MediaStore.Images.ImageColumns.ORIENTATION};
            Cursor c = context.getContentResolver().query(selectedImage, projection, null, null, null);
            if (c.moveToFirst()) {
                final int rotation = c.getInt(0);
                c.close();
                return rotateImage(img, rotation);
            }
            return img;
        } else {
            ExifInterface ei = new ExifInterface(selectedImage.getPath());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(img, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(img, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(img, 270);
                default:
                    return img;

            }
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);

        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        return rotatedImg;
    }

    private static void showImageInfo(Bitmap img) {
        printLog("ImageHeight:" + img.getHeight());
        printLog("ImageWidth:" + img.getWidth());
        printLog("ImageInfo:" + img.getConfig());
        printLog("ImageDensity:" + img.getDensity());
        printLog("__________________________");
    }

    public static byte[] rotateImageIfRequired(Uri uri, byte[] fileBytes, Context context) {
        byte[] data = null;
        Bitmap bitmap = BitmapFactory.decodeByteArray(fileBytes, 0, fileBytes.length);
        ByteArrayOutputStream outputStream = null;
        try {
            bitmap = rotateImageIfRequired(bitmap, uri, context);
            showImageInfo(bitmap);
            outputStream = new ByteArrayOutputStream();
            int compressBy = 1, compressValue = 50;
            if (bitmap.getWidth() > 4000) {
                compressBy = 6;
                compressValue = 80;
            } else if (bitmap.getWidth() > 3000) {
                compressBy = 5;
                compressValue = 70;
            } else if (bitmap.getWidth() > 2000) {
                compressBy = 5;
                compressValue = 60;
            } else if (bitmap.getWidth() > 1000) {
                compressBy = 2;
                compressValue = 50;
            }
            printLog("ImageValue:Compress:" + compressBy + "||CompressValue:" + compressValue);
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / compressBy, bitmap.getHeight() / compressBy, false);
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressValue, outputStream);

            showImageInfo(bitmap);
            data = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                // Intentionally blank
            }
        }
        return data;
    }

    /********************************** Get File Information**************************************/
    /* for upload files from gallery */
    public static byte[] getDataByte(Uri uri, Context context) {
        byte[] data = null;
        Bitmap bitmap = null;
        ByteArrayOutputStream outputStream = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            showImageInfo(bitmap);
            outputStream = new ByteArrayOutputStream();
            int compressBy = 1, compressValue = 50;
            if (bitmap.getWidth() > 4000) {
                compressBy = 6;
                compressValue = 80;
            } else if (bitmap.getWidth() > 3000) {
                compressBy = 5;
                compressValue = 70;
            } else if (bitmap.getWidth() > 2000) {
                compressBy = 5;
                compressValue = 60;
            } else if (bitmap.getWidth() > 1000) {
                compressBy = 2;
                compressValue = 50;
            }
            printLog("ImageValue:Compress:" + compressBy + "||CompressValue:" + compressValue);
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / compressBy, bitmap.getHeight() / compressBy, false);
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressValue, outputStream);

            showImageInfo(bitmap);
            data = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static byte[] getDataByte(Intent data, String name, Context context) {
        byte[] byteData = null;
        Uri uri = null;
        try {
            uri = data.getData();
           /* File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getPackageName() + "/media/videos");
            String filePath = SiliCompressor.with(CommunityImageUploadActivity.this).compressVideo(Uri.parse(data.getData().toString()), f.getPath());*/
            String fileType = getMimeType(name);
            printLog("fileType:" + fileType);
            try {
                InputStream iStream = context.getContentResolver().openInputStream(uri);

                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int len = 0;
                while ((len = iStream.read(buffer)) != -1) {
                    byteBuffer.write(buffer, 0, len);
                }
                byteData = byteBuffer.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getSizeOfFile(byteData);
        return byteData;
    }

    public static String getFileName(Intent data, boolean isCapture, Context context) {
        printLog("DataIntent:" + data);
        String name = null;
        try {
            Uri uri = data.getData();
            name = getContentName(context.getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (name == null) {
            try {
                Uri uri = data.getData();
                name = getFilename(uri, context);
                if (!name.contains(".")) {
                    name = name + ".jpg";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (name == null) {
            if (isCapture)
                name = System.currentTimeMillis() + ".jpg";
            else name = System.currentTimeMillis() + ".jpg";
        }
        return name;

    }

    public static String getFileName(Uri data, boolean isCapture, Context context) {
        printLog("DataIntent:" + data);
        String name = null;
        try {
            Uri uri = data;
            name = getContentName(context.getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (name == null) {
            try {
                Uri uri = data;
                name = getFilename(uri, context);
                if (!name.contains(".")) {
                    name = name + ".jpg";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (name == null) {
            if (isCapture)
                name = System.currentTimeMillis() + ".jpg";
            else name = System.currentTimeMillis() + ".jpg";
        }
        return name;

    }

    public static String getFilename(Uri uri, Context contextTemp) {
/*  Intent intent = getIntent();
    String name = intent.getData().getLastPathSegment();
    return name;*/

        String fileName = null;
        Context context = contextTemp.getApplicationContext();
        String scheme = uri.getScheme();
        String extension = getMimeType(context, uri);
        printLog("Extension:" + extension);
        if (scheme.equals("file")) {
            fileName = uri.getLastPathSegment();
        } else if (scheme.equals("content")) {
            String[] proj = {MediaStore.Video.Media.TITLE};
            Uri contentUri = null;
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            try {

                if (cursor != null && cursor.getCount() != 0) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE);
                    cursor.moveToFirst();
                    fileName = cursor.getString(columnIndex);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }

        return fileName;
    }

    private static String getMimeType(Context context, Uri uri) {
        String extension;
        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }

    public static String getContentName(ContentResolver resolver, Uri uri) {

        Cursor cursor = resolver.query(uri, null, null, null, null);
        try {
            if (cursor != null && cursor.getCount() != 0) {
                cursor.moveToFirst();
                int nameIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
                if (nameIndex >= 0) {
                    return cursor.getString(nameIndex);
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
        return null;
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    private static void getSizeOfFile(byte[] bytes) {

// Get length of file in bytes
        long fileSizeInBytes = bytes.length;
// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;
        printLog("FileSizeInKb" + fileSizeInKB + "||" + fileSizeInMB);
    }

    public static long getFileSize(Intent returnIntent, Context context) {
        Uri returnUri = returnIntent.getData();
        Cursor returnCursor =
                context.getContentResolver().query(returnUri, null, null, null, null);
    /*
     * Get the column indexes of the data in the Cursor,
     * move to the first row in the Cursor, get the data,
     * and display it.
     */
        if (returnCursor != null && returnCursor.getCount() != 0) {
            try {
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                printLog("FileName:" + returnCursor.getString(nameIndex));
                printLog("FileSize1:" + Long.toString(returnCursor.getLong(sizeIndex)));
             /*   return returnCursor.getLong(sizeIndex);*/
                return getFileInMb(returnCursor.getLong(sizeIndex));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                returnCursor.isClosed();
            }
        } else {
            printLog("CursorIsNull");
            String[] proj = {MediaStore.Video.Media.SIZE};
            Uri contentUri = null;
            Cursor cursor = context.getContentResolver().query(returnUri, proj, null, null, null);
            if (cursor != null && cursor.getCount() != 0) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
                cursor.moveToFirst();
                printLog("FileSize2:" + Long.toString(cursor.getLong(columnIndex)));
                /*return cursor.getLong(columnIndex);*/
                return getFileInMb(cursor.getLong(columnIndex));
            } else {
                try {
                    printLog("FileSize3:" + getFileSizeFromUri(returnUri));
                    return getFileSizeFromUri(returnUri);
                   /* File file = new File(returnUri.getPath());
                    if (file != null) {
                        printLog("File:" + file.length());
                        return file.length();

                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        return 0;
    }

    public static double getSizeOfFileInMb(byte[] bytes) {

// Get length of file in bytes
        long fileSizeInBytes = bytes.length;
// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
      /*  long fileSizeInMB = fileSizeInKB / 1024;*/
        double fileSizeInMB = fileSizeInKB / 1024;
        printLog("FileSizeInKb" + fileSizeInKB + "||" + fileSizeInMB);
        return fileSizeInMB;
    }

    private static long getFileSizeFromUri(Uri uri) {
        File f = new File(uri.getPath());
        long fileSizeInBytes = f.length();
        long fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
      /*  long fileSizeInMB = fileSizeInKB / 1024;*/
        long fileSizeInMB = fileSizeInKB / 1024;
        return fileSizeInMB;
    }

    private static long getFileInMb(long fileSizeInBytes) {

        long fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
      /*  long fileSizeInMB = fileSizeInKB / 1024;*/
        long fileSizeInMB = fileSizeInKB / 1024;
        return fileSizeInMB;
    }

    /************************************* PICASO IMAGE optimisation****************************************/

    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
  /*  private void getSizeOfFile(byte[] bytes) {

// Get length of file in bytes
        long fileSizeInBytes = bytes.length;
// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;
        printLog("FileSizeInKb" + fileSizeInKB + "||" + fileSizeInMB);
    }*/

    public static int convertDpToPx(int dp, Context context) {
        return Math.round(dp * (context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }

    /**
     * Returns the bitmap that represents the chart.
     *
     * @return
     */
    public static Bitmap getScreenShot(View view) {
        // Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        // Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        // Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            // has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            // does not have background drawable, then draw white background on
            // the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        // return the bitmap
        return returnedBitmap;
    }

    private void getFileInformation(Intent returnIntent, Context context) {
        Uri returnUri = returnIntent.getData();
        Cursor returnCursor =
                context.getContentResolver().query(returnUri, null, null, null, null);
    /*
     * Get the column indexes of the data in the Cursor,
     * move to the first row in the Cursor, get the data,
     * and display it.
     */
        if (returnCursor != null && returnCursor.getCount() != 0) {
            try {
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                printLog("FileName:" + returnCursor.getString(nameIndex));
                printLog("FileSize:" + Long.toString(returnCursor.getLong(sizeIndex)));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                returnCursor.isClosed();
            }
        } else printLog("CursorIsNull");

    }

    private byte[] getDataByteForCaptureImage(Intent intent) {
        Bitmap thumbnail = (Bitmap) intent.getExtras().get("data");
        if (thumbnail == null) {
            return new byte[]{};
        }
      /*  ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();*/


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        File extBaseDir = Environment.getExternalStorageDirectory();
        File file = new File(extBaseDir.getAbsolutePath() + APP_FOLDER + SUB_FOLDER_NAME_NEWS);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                printLog("CantConnect:");
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        File destination = new File(extBaseDir.getAbsolutePath() + APP_FOLDER + SUB_FOLDER_NAME_NEWS, "/photo_" + timeStamp + ".png");
      /*  File destination = new File(extBaseDir.getAbsolutePath() + APP_FOLDER + SUB_FOLDER_NAME_NEWS, timeStamp + ".jpg");*/
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int size = (int) destination.length();
        byte[] bytesArray = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(destination));
            buf.read(bytesArray, 0, bytesArray.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        printLog("ByteArray:" + bytesArray.length);
        return bytesArray;

    }

    private int convertPxToDp(int px) {
        return Math.round(px / (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private float dpFromPx(float px, Context context) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    private float pxFromDp(float dp, Context context) {
        return dp * context.getResources().getDisplayMetrics().density;
    }


}
