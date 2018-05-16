package com.ritvi.kaajneeti.Util;

import java.util.Random;

/**
 * Created by sunil on 26-05-2017.
 */

public class StringUtils {

    public static final String IS_LOGIN="is_login";
    public static final String IS_PROFILE_COMPLETED="is_profile_completed";
    public static final String IS_PROFILE_SKIPPED="is_profile_skipped";
    public static final String IS_USER_NAME="is_user_name";
    public static final String USER_TYPE="user_type";
    public static final String SELECTED_LANGUAGE="selected_language";
    public static final String CURRENT_LATITUDE="current_latitude";
    public static final String CURRENT_LONGITUDE="current_longitude";

    public static final String USER_PROFILE="user_profile";


    public static final String INTRO_COMPLETED ="intro_completed";


    public static final String C_PROFILE_DETAIL="c_profile_detail";
    public static final String L_PROFILE_DETAIL="l_profile_detail";

    public static final String FORGOT_SMS_CLASS = "forgot_sms_class";
    public static final String LOGIN_OTP_CLASS = "login_otp_class";


    public static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    public static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

}
