package com.mitroz.bloodbank.Constant;

import java.util.concurrent.TimeUnit;

public class ConstatsValue {
    public static class INTENT_KEYS {
        public static final String BARCODE_NUMBER = "barcodeNumber" ;
        public static final String IS_FROM_FORM = "isFromStockForm";
        public static String USERNAME ="username";
        public static String PASSWORD ="password";
        public static String Token ="token";
        public static String BEARER ="bearer ";
        public static String BEA ="bearer";
        public static String userRole ="userRole";
    }

    public static class ResponseCodes {

        public static final int SUCCESS = 200;

        public static final long FAILURE = 111;

        public static final int BadRequest = 400;
        public static final int Unauthorized = 401;
        public static final int NotFound = 404;
        public static final int InternalServerError = 500;
        public static final int Forbidden = 403;
        public static final int TIMEOUT_ERROR = 408;

        public static final int TYPE_WIFI = 1;
        public static final int TYPE_MOBILE = 2;
        public static final int TYPE_NOT_CONNECTED = 0;

    }
    public class PREFERENCES_KEYS {
        public static final String IS_LOGGED_IN = "isLogged";
        public static final String LOGGED_USER = "loggedUser";
        public static final String LOGGED_USERROLE = "userRole";
    }


    public class FilterKeys {
        public static final String Active_Call = "Active Call";
        public static final String Pending_Settlement = "Pending Settlement";
        public static final String Completed = "Completed";
        public static final String In_Progress = "In-Progress";
        public static final String pendingsettlement="pendingsettlement";
        public static final String pending="pending";
        public static final String PENDING="Pending";
        public static final String inprogress="inprogress";
        public static final String  complete="complete";
        public static final String  all="all";
        public static final String CASH = "cash";
        public static final String EXPENSE = "expense";
        public static final String CREDIT = "credit";
        public static final String UPI = "upi";
    }

    public class DATE_FORMATS {
        public static final String DEFAULT = "dd MMM yyyy";
        public static final String MM_DD_YYYY = "MM/dd/yyyy";
    }

    public class PhotoStrings {
        public static final String ADD_PHOTO = "Add Photo";
        public static final String SELECT_FILE = "Select File";
        public static final String SET_TYPE_IMAGE = "image/*";
        public static final String DATA = "data";
        public static final String CANCELLED = "Cancelled";

        public static final String FILE_PATH = "cropImageFilePath";


    }

    public class RequestCodes {
        public static final int NAV_MENU_ACTIVITY = 145;
        public static final int TAKE_PICTURE = 5;
        public static final int SELECT_FILE = 2;
        public static final int START_FRAGMENT = 2;
        public static final int  GALLERY = 1;
        public static final int  RECORD_VIDEO = 31;
        public static final int  RECORD_AUDIO = 10;
        public static final int  SELECT_AUDIO = 1001;
        public static final int  CROPPED_IMAGE = 7;
        public static final int  PAGE_NUMBER =1;
        public static final int  REQUEST_TAKE_PHOTO =13;
        public static final int START_BUDDYWALL_FRAGMENT = 14;
        public static final int START_POST_FRAGMENT = 15;
        public static final int TAKE_VIDEO = 11;
        public static final int START_MYBUDDY_FRAGMENT = 15;
    }

    public static class SelectImageTypes {
        public static final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Remove Photo","Cancel"};
        public static final String TAKE_PHOTO = "Take Photo";
        public static final String CHOOSE_FROM_GALLERY = "Choose from Gallery";
        public static final String CANCEL = "Cancel";
        public static final String REMOVE_PHOTO = "Remove Photo";
        public static final String IMAGE_MESSAGE_TYPE_SENT = "imageSent";
        public static final String IMAGE_MESSAGE_TYPE_RECEIVE = "imageRecieve";
        public static final String TEXT_MESSAGE_TYPE_SENT = "textSent";
        public static final String TEXT_MESSAGE_TYPE_RECEIVE = "textRecieve";

        private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
        private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

        // key to store image path in savedInstance state
        public static final String KEY_IMAGE_STORAGE_PATH = "image_path";

        public static final int MEDIA_TYPE_IMAGE = 1;
        public static final int MEDIA_TYPE_VIDEO = 2;

        // Bitmap sampling size
        public static final int BITMAP_SAMPLE_SIZE = 8;

        // Gallery directory name to store the images or videos
        public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";

        // Image and Video file extensions
        public static final String IMAGE_EXTENSION = "jpg";
        public static final String VIDEO_EXTENSION = "mp4";

        private static String imageStoragePath;


    }

    public static class ImageHelperString {
        public static final String SUBDIRECTORY = "/beer/media/";
        public static final String SDCARD = "/sdcard";
        public static final String IMAGENAME = "chat_wallpaper.jpg";
        public static final String FILE_PATH = "cropImageFilePath";
        public static final String CROPPING = "cropping";
        public static final int THUMBNAIL_WIDHT = 20;
        public static final int THUMBNAIL_HEIGHT = 20;
        public static final String DEFAULT_EVENT_IMAGE = "https://but-dev-bucket-mumbai.s3.amazonaws.com/sko-but-images/posts/34f1bf4e-1b2c-41ff-944f-6426e26e837f.jpg";
        public static final int IMAGE_COMPRESS_QUALITY = 50;
        public static final String IMAGE_FILE_NAME =  "BUT_IMAGE"+String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())) ;
        public static final String FORMAT_JPG = ".jpg";
        public static final String CURRENT_PHOTO_PATH = "mCurrentPhotoPath";
        public static final String PICTURES = "Pictures";
    }

}
