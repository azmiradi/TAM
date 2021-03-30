package azmithabet.com.tam.localDB.Shared;

import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;
import static azmithabet.com.tam.util.Constants.IS_LOGIN;
import static azmithabet.com.tam.util.Constants.TAM_DB;


public class SharedPreference {


    public static void setLogin(boolean isLogin, Context context){
         SharedPreferences.Editor editor =context.
                getSharedPreferences(TAM_DB, MODE_PRIVATE).edit();

        editor.putBoolean(IS_LOGIN, isLogin );
        editor.apply();
    }



    public static boolean isLogin(Context context){
        SharedPreferences prefs =
                context.getSharedPreferences(TAM_DB, MODE_PRIVATE);
        return prefs.getBoolean(IS_LOGIN, false);
    }


}
