package com.dailytodo.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.dailytodo.model.UserEntity;
import com.google.gson.Gson;


public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;
    int new_feature = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "dailytodo";

    private static final String IS_LOGIN = "IsLogin";
    private static final String USER_DATA = "userdata";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setIsLogin(boolean b) {
        editor.putBoolean(IS_LOGIN, b);
        editor.commit();
    }

    public boolean isLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean storeUser(UserEntity userEntity) {

        try {
            Gson gson = new Gson();
            editor.putString(USER_DATA, gson.toJson(userEntity));
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public UserEntity getUser() {

        try {
            String loginResponse = pref.getString(USER_DATA, "");
            Gson gson = new Gson();
            UserEntity objloginResponse = gson.fromJson(loginResponse, UserEntity.class);
            return objloginResponse;
        } catch (Exception io) {
            io.printStackTrace();
        }
        return null;
    }

}