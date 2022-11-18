package com.instanceit.alhadafpos.Utility;

import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.michaelrocks.paranoid.Obfuscate;


/**
 * Created by c49 on 01/02/16.
 */
@Obfuscate

public class Validation {

    public static boolean chkRequired(Context context, EditText editText, String errorMessage, int minLen, int maxLen) {


        String text = editText.getText().toString().trim();
        //String pass=editText.getText().toString().trim();
        if (text.length() < minLen || text.length() > maxLen) {
            editText.requestFocus();
            //  editText.setError("Required..");
            //  Toast.makeText(context, errorMessage + " and length between " + minLen + " to " + maxLen + " character", Toast.LENGTH_LONG).show();

            return false;
        } else {

            return true;
        }

    }

    public static boolean chkRequired(Context context, EditText editText, View containerView, String errorMessage, int minLen, int maxLen, int errorDrawable, int defaultBackGround) {


        String text = editText.getText().toString().trim();
        if (text.length() < minLen || text.length() > maxLen) {
            editText.requestFocus();
            containerView.setBackgroundResource(errorDrawable);
            Toast.makeText(context, errorMessage + " and length between " + minLen + " to " + maxLen + " character", Toast.LENGTH_LONG).show();
            editText.requestFocus();

            return false;
        } else {
            containerView.setBackgroundResource(defaultBackGround);
            return true;
        }


    }

    public final static boolean isValidEmail(Context c, EditText edit) {
        String str = edit.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
            return true;
        }
        edit.requestFocus();
        return false;
    }

    public final static boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }

    public static final boolean isValidPhoneNumber(Context c, EditText edit) {

        String str = edit.getText().toString();
        String ex = "^[7-9][0-9]{9}$";
        Pattern pattern = Pattern.compile(ex);
        Matcher matcher = pattern.matcher(str);

        if (str.length() <= 12/*||!str.matches(ex)*/) {
            return true;
        } else {
            edit.requestFocus();
            return false;
        }

    }

    public static final boolean isGstCadeValidate(Context c, EditText edit) {

        String str = edit.getText().toString();
        String ex = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$";
        Pattern pattern = Pattern.compile(ex);
        Matcher matcher = pattern.matcher(str);

        if (!str.matches(ex)) {
            edit.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isValidGSTNo(String str) {
        // Regex to check valid
        // GST (Goods and Services Tax) number
        String regex = "^[0-9]{2}[A-Z]{5}[0-9]{4}"
                + "[A-Z]{1}[1-9A-Z]{1}"
                + "Z[0-9A-Z]{1}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // return false
        if (str == null) {
            return false;
        }

        // Pattern class contains matcher()
        // method to find the matching
        // between the given string
        // and the regular expression.
        Matcher m = p.matcher(str);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }

    public static boolean isValidVehicleNo(String str) {
        // Regex to check valid
        // GST (Goods and Services Tax) number
//        String regex = "^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$";
        String regex = "^[A-Za-z]{2}[0-9]{2}[A-Za-z]{2}[0-9]{4}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // return false
        if (str == null) {
            return false;
        }

        // Pattern class contains matcher()
        // method to find the matching
        // between the given string
        // and the regular expression.
        Matcher m = p.matcher(str);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }

}
