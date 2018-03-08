package com.ritvi.kaajneeti.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ritvi.kaajneeti.pojo.user.ProfileRolePOJO;
import com.ritvi.kaajneeti.pojo.user.UserProfilePOJO;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sunil on 26-05-2017.
 */

public class Pref {

    private static final String PrefDB = "cms";
    private static final String PerPrefDB = "percms";


    public static final String FCM_REGISTRATION_TOKEN = "fcm_registration_token";


    public static void SetStringPref(Context context, String KEY, String Value) {
        try {
            SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(KEY, Value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String GetStringPref(Context context, String KEY, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getString(KEY, defValue);
    }

    public static void SetBooleanPref(Context context, String KEY, boolean Value) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY, Value);
        editor.commit();
    }

    public static void setPermanentBoolean(Context context, String KEY, boolean Value) {
        SharedPreferences sp = context.getSharedPreferences(PerPrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY, Value);
        editor.commit();
    }

    public static boolean getPermanentBoolean(Context context, String KEY, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(PerPrefDB, MODE_PRIVATE);
        return sp.getBoolean(KEY, defValue);
    }

    public static int GetIntPref(Context context, String KEY, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getInt(KEY, defValue);
    }

    public static void SetIntPref(Context context, String KEY, int Value) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY, Value);
        editor.commit();
    }

    public static boolean GetBooleanPref(Context context, String KEY, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getBoolean(KEY, defValue);
    }

    public static void clearSharedPreference(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public static void SaveDeviceToken(Context context, String Value) {
        SharedPreferences sp = context.getSharedPreferences("momytdevicetoken.txt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("devicetoken", Value);
        editor.commit();
    }

    public static String GetDeviceToken(Context context, String defValue) {
        SharedPreferences sp = context.getSharedPreferences("momytdevicetoken.txt", MODE_PRIVATE);
        return sp.getString("devicetoken", defValue);
    }

    public static void SaveUserProfile(Context context, UserProfilePOJO userProfilePOJO) {
        if (userProfilePOJO != null) {

            SetStringPref(context, StringUtils.CITIZEN_ID, userProfilePOJO.getCitizenId());
            SetStringPref(context, StringUtils.CITIZEN_PROFILE_ID, userProfilePOJO.getProfileId());
            SetStringPref(context, StringUtils.CITIZEN_FIRST_NAME, userProfilePOJO.getFirstname());
            SetStringPref(context, StringUtils.CITIZEN_MIDDLE_NAME, userProfilePOJO.getLastname());
            SetStringPref(context, StringUtils.CITIZEN_LAST_NAME, userProfilePOJO.getLastname());
            SetStringPref(context, StringUtils.CITIZEN_FULLNAME, userProfilePOJO.getFullname());
            SetStringPref(context, StringUtils.CITIZEN_EMAIL, userProfilePOJO.getEmail());
            SetStringPref(context, StringUtils.CITIZEN_USERNAME, userProfilePOJO.getUsername());
            SetStringPref(context, StringUtils.CITIZEN_MOBILE, userProfilePOJO.getMobile());
            SetStringPref(context, StringUtils.CITIZEN_ALT_MOBILE, userProfilePOJO.getAltMobile());
            SetStringPref(context, StringUtils.CITIZEN_GENDER, userProfilePOJO.getGender());
            SetStringPref(context, StringUtils.CITIZEN_STATUS, userProfilePOJO.getStatus());
            SetStringPref(context, StringUtils.CITIZEN_CREATED_ON, userProfilePOJO.getCreatedOn());
            SetStringPref(context, StringUtils.CITIZEN_UPDATED_ON, userProfilePOJO.getUpdatedOn());
            SetStringPref(context, StringUtils.CITIZEN_ADDRESS, userProfilePOJO.getAddress());
            SetStringPref(context, StringUtils.CITIZEN_CITY, userProfilePOJO.getCity());
            SetStringPref(context, StringUtils.CITIZEN_STATE, userProfilePOJO.getState());
            SetStringPref(context, StringUtils.CITIZEN_COUNTRY, userProfilePOJO.getCountry());
            SetStringPref(context, StringUtils.CITIZEN_ZIPCODE, userProfilePOJO.getZipcode());
            SetStringPref(context, StringUtils.CITIZEN_ABOUT_ME, userProfilePOJO.getAboutMe());
            SetStringPref(context, StringUtils.CITIZEN_DEVICE_TOKEN, userProfilePOJO.getDeviceToken());
            SetStringPref(context, StringUtils.CITIZEN_DATE_OF_BIRTH, userProfilePOJO.getDateOfBirth());
            SetStringPref(context, StringUtils.CITIZEN_PROFILE_IMAGE, userProfilePOJO.getProfileImage());
            SetStringPref(context, StringUtils.CITIZEN_COVER_IMAGE, userProfilePOJO.getCoverImage());
            SetStringPref(context, StringUtils.CITIZEN_FACEBOOK_PROFILEID, userProfilePOJO.getFacebookProfileId());
            SetStringPref(context, StringUtils.CITIZEN_GOOGLE_PROFILEID, userProfilePOJO.getGoogleProfileId());
            SetStringPref(context, StringUtils.CITIZEN_TWITTER_PROFILEID, userProfilePOJO.getTwitterProfileId());

        }
    }

    public static ProfileRolePOJO getProfileRolePOJO(String profileRole) {
        try {
            Gson gson = new Gson();
            ProfileRolePOJO profileRolePOJO = gson.fromJson(profileRole, ProfileRolePOJO.class);
            return profileRolePOJO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserProfilePOJO GetUserProfile(Context context) {

        UserProfilePOJO userProfilePOJO = new UserProfilePOJO(
                GetStringPref(context, StringUtils.CITIZEN_ID, ""),
                GetStringPref(context, StringUtils.CITIZEN_PROFILE_ID,""),
                GetStringPref(context, StringUtils.CITIZEN_FIRST_NAME,""),
                GetStringPref(context, StringUtils.CITIZEN_MIDDLE_NAME,""),
                GetStringPref(context, StringUtils.CITIZEN_LAST_NAME,""),
                GetStringPref(context, StringUtils.CITIZEN_FULLNAME, ""),
                GetStringPref(context, StringUtils.CITIZEN_EMAIL, ""),
                GetStringPref(context, StringUtils.CITIZEN_USERNAME, ""),
                GetStringPref(context, StringUtils.CITIZEN_MOBILE, ""),
                GetStringPref(context, StringUtils.CITIZEN_ALT_MOBILE, ""),
                GetStringPref(context, StringUtils.CITIZEN_GENDER,""),
                GetStringPref(context, StringUtils.CITIZEN_STATUS, ""),
                GetStringPref(context, StringUtils.CITIZEN_CREATED_ON, ""),
                GetStringPref(context, StringUtils.CITIZEN_UPDATED_ON, ""),
                GetStringPref(context, StringUtils.CITIZEN_ADDRESS, ""),
                GetStringPref(context, StringUtils.CITIZEN_CITY, ""),
                GetStringPref(context, StringUtils.CITIZEN_STATE, ""),
                GetStringPref(context, StringUtils.CITIZEN_COUNTRY, ""),
                GetStringPref(context, StringUtils.CITIZEN_ZIPCODE, ""),
                GetStringPref(context, StringUtils.CITIZEN_ABOUT_ME, ""),
                GetStringPref(context, StringUtils.CITIZEN_DEVICE_TOKEN, ""),
                GetStringPref(context, StringUtils.CITIZEN_DATE_OF_BIRTH, ""),
                GetStringPref(context, StringUtils.CITIZEN_PROFILE_IMAGE, ""),
                GetStringPref(context, StringUtils.CITIZEN_COVER_IMAGE, ""),
                GetStringPref(context, StringUtils.CITIZEN_FACEBOOK_PROFILEID, ""),
                GetStringPref(context, StringUtils.CITIZEN_GOOGLE_PROFILEID, ""),
                GetStringPref(context, StringUtils.CITIZEN_TWITTER_PROFILEID, "")
        );

        return userProfilePOJO;
    }

}
