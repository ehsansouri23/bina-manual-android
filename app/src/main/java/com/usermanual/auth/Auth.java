package com.usermanual.auth;

import android.content.Context;

import com.usermanual.helper.dbmodels.LoginResponse;
import com.usermanual.helper.dbmodels.Token;

import static com.usermanual.helper.Consts.LOGED_IN;
import static com.usermanual.helper.Consts.TOKEN;
import static com.usermanual.helper.PrefHelper.getBoolean;
import static com.usermanual.helper.PrefHelper.getString;
import static com.usermanual.helper.PrefHelper.saveBoolean;
import static com.usermanual.helper.PrefHelper.saveString;

public class Auth {
    public static void login(Context context, LoginResponse loginResponse) {
        if (!loginResponse.token.equals("")) {
            saveBoolean(context, LOGED_IN, true);
//            saveString(context, USER_PIC_URL, loginResponse.picUrl);
//            saveString(context, USER_NAME, loginResponse.name);
            saveString(context, TOKEN, loginResponse.token);
        }
    }

    public static void logout(Context context) {
        saveBoolean(context, LOGED_IN, false);
    }

    public static boolean isLoggedIn(Context context) {
        return getBoolean(context, LOGED_IN, false);
    }

    public static String getToken(Context context) {
        return getString(context, TOKEN, "");
    }

    public static Token getTokenModel(Context context) {
        Token token = new Token();
        token.token = getToken(context);
        return token;
    }
}
