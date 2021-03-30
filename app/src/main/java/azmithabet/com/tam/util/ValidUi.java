package azmithabet.com.tam.util;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidUi {
    private static final int passwordLength=8;
   public enum errorType{
        IS_EMPTY,ERROR_FORMAT,NO_ERROR
    }
    public static errorType isValidEmail(String email) {
        if (TextUtils.isEmpty(email))
        {
            return errorType.IS_EMPTY;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return errorType.ERROR_FORMAT;
        }
        return errorType.NO_ERROR;
    }

    public static errorType isValidPassword(String password) {
        if (TextUtils.isEmpty(password))
        {
            return errorType.IS_EMPTY;
        }
        else if (password.length()<passwordLength)
        {
            return errorType.ERROR_FORMAT;
        }
        return errorType.NO_ERROR;
     }

}
