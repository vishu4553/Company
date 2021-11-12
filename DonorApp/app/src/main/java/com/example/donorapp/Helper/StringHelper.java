//------------------------------------------------------------------------------
//    DEVELOPER          : SKO SYSTEMS LTD. (https://www.skosystems.com)        
//------------------------------------------------------------------------------


package com.mitroz.bloodbank.Helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.icu.util.Calendar;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.Patterns;
import android.webkit.URLUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;


/**
 * Created by varshap on 23/06/18.
 */

public class StringHelper {


    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.trim().length() == 0 || str.equalsIgnoreCase("NULL"));
    }


    public static String getJSONStringFromAssets(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

    public static boolean containsNumbers(String str) {
        return (!isNullOrEmpty(str)) && str.matches(".*\\d+.*");

    }


    public static boolean containsSpecialSymbol(String str) {

        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        //handle your action here toast message/ snackbar or something else
        return (!isNullOrEmpty(str) && regex.matcher(str).find());


    }


    public static boolean containsOnlyNumbers(String str) {
        return (!isNullOrEmpty(str)) && (str.matches("[0-9]+") && str.length() > 2);
    }

    public static boolean containsCharacters(String str) {
        return (!isNullOrEmpty(str)) && str.matches(".*[a-zA-Z].*");
    }

    public static boolean isValidEmailId(String emailId) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        return (!isNullOrEmpty(emailId)) && pat.matcher(emailId).matches();
    }

    public static boolean isValidPassword(String password) {

        String passwordRegress = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

        Pattern pat = Pattern.compile(passwordRegress);

        return (!isNullOrEmpty(password)) && pat.matcher(password).matches();
    }

    //It will allow to have spaces, unicode chars, dash in name
    public static boolean isValidName(String name) {

        String regx = "^[\\p{L} .'-]+$";

        Pattern pat = Pattern.compile(regx);

        return (!isNullOrEmpty(name)) && pat.matcher(name).matches();
    }

    public static String getFormattedUrl(String formedUrl) {

        String newUrl = formedUrl.trim().replace("null", "").replace(" ", "%20");

        return newUrl;

    }

    /**
     * @param phone : will be like "(999) 999 -9999"
     */
    public static int getLengthOfOnlyNumbersInPhoneNo(String phone) {

        //Remove all symbols from phoneno
        phone = phone.replaceAll("\\p{P}", "");
        phone = phone.replaceAll("-", "");
        phone = phone.replaceAll("_", "");
        phone = phone.replaceAll(" ", "");

        return phone.length();
    }

    public static String getTrimmedString(String str) {
        return str.trim();
    }

    /**
     * It will replace all alphabets, symbols and keep only digits
     */
    public static String getOnlyDigits(String str) {

        if (isNullOrEmpty(str)) {
            return "";
        }

        String newDigitStr = str.replaceAll("[^0-9]", "");

        return newDigitStr;
    }

    /**
     * It will replace all symbols and keep only digits and alphabets
     */
    public static String getOnlyAlphanumeric(String str) {

        if (isNullOrEmpty(str)) {
            return "";
        }

        String newDigitStr = str.replaceAll("[^A-Za-z0-9]", "");

        return newDigitStr;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean isAgeValid(String birthdate, int minimumValidAge) {

        String date = birthdate;
        int yeardiff = 0;
        if (date != null) {

            String d = null;
            String m = null;
            String y = null;
            try {
                String[] items1 = date.split("-");
                d = items1[0];
                m = items1[1];
                y = items1[2];
            } catch (Exception e) {
                e.printStackTrace();
            }

            int day = Integer.parseInt(d);
            int month = Integer.parseInt(m);
            int year = Integer.parseInt(y);

            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c);

            yeardiff = c.get(Calendar.YEAR) - year;

            if (c.get(Calendar.MONTH) < month - 1) {
                yeardiff--;
            } else {
                if (c.get(Calendar.MONTH) == month - 1 && c.get(Calendar.DAY_OF_MONTH) < day) {
                    yeardiff--;
                }
            }
        }
        if (yeardiff < minimumValidAge) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidUrl(String urlString) {
        try {
            URL url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return URLUtil.isValidUrl(urlString) && Patterns.WEB_URL.matcher(urlString).matches();
    }

    public static int getPixelFromDp(Context context, int paddingDp) {
        float density = context.getResources().getDisplayMetrics().density;
        int paddingPixel = (int) (paddingDp * density);
        return paddingPixel;
    }



        public static String capitaliseName(String name) {
            String collect[] = name.split(" ");
            String returnName = "";
            for (int i = 0; i < collect.length; i++) {
                collect[i] = collect[i].trim().toLowerCase();
                if (collect[i].isEmpty() == false) {
                    returnName = returnName + collect[i].substring(0, 1).toUpperCase() + collect[i].substring(1) + " ";
                }
            }
            return returnName.trim();
        }
        public static String capitaliseOnlyFirstLetter(String data)
        {
            return data.substring(0,1).toUpperCase()+data.substring(1);
        }

    public static String getCamelCase(String profileUserName) {
        String stringInCamelCase="";
        String words[]=profileUserName.split(" ");
        String finalWord[] = new String[words.length];
        char firstChar;
        for(int i=0;i<words.length;i++){
            firstChar=words[i].charAt(0);
            finalWord[i]= Character.toString(firstChar).toUpperCase()+words[i].substring(1,words[i].length()).toLowerCase();
            stringInCamelCase = stringInCamelCase +  " " + finalWord[i];
        }

        return stringInCamelCase.trim();
    }

}
