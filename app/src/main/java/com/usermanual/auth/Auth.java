package com.usermanual.auth;

import android.content.Context;

import com.usermanual.R;
import com.usermanual.dbmodels.LoginResponse;
import com.usermanual.dbmodels.Token;
import com.usermanual.helper.StorageHelper;

import static com.usermanual.helper.Consts.LOGED_IN;
import static com.usermanual.helper.Consts.TOKEN;
import static com.usermanual.helper.Consts.USER_NAME;
import static com.usermanual.helper.Consts.USER_PIC_URL;
import static com.usermanual.helper.PrefHelper.getBoolean;
import static com.usermanual.helper.PrefHelper.getString;
import static com.usermanual.helper.PrefHelper.saveBoolean;
import static com.usermanual.helper.PrefHelper.saveString;

public class Auth {
    public static void login(Context context, LoginResponse loginResponse) {
        if (!loginResponse.token.equals("")) {
            saveBoolean(context, LOGED_IN, true);
            if (loginResponse.picFileUrl != null && !loginResponse.picFileUrl.equals(""))
                saveString(context, USER_PIC_URL, loginResponse.picFileUrl);
            if (loginResponse.name != null && !loginResponse.name.equals(""))
                saveString(context, USER_NAME, loginResponse.name);
            saveString(context, TOKEN, loginResponse.token);
        }
    }

    public static void logout(Context context) {
        saveBoolean(context, LOGED_IN, false);
    }

    public static boolean isLoggedIn(Context context) {
//        return true;
        return getBoolean(context, LOGED_IN, false);
    }

    public static String getToken(Context context) {
        return getString(context, TOKEN, "");
//        return "43e013b45432622a5413811c0a503136";
    }

    public static Token getTokenModel(Context context) {
        Token token = new Token();
        token.token = getToken(context);
        return token;
    }

    public static String getName(Context context) {
        return getString(context, USER_NAME, context.getResources().getString(R.string.name));
    }

    public static String getUserPicUrl(Context context) {
        return StorageHelper.getUrl(getString(context, USER_PIC_URL, ""));
    }
}
